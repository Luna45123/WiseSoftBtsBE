package com.wisesoft.btsfare.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wisesoft.btsfare.dto.StationDTO;
import com.wisesoft.btsfare.entity.BtsFare;
import com.wisesoft.btsfare.entity.Discount;
import com.wisesoft.btsfare.entity.ExtensionFare;
import com.wisesoft.btsfare.entity.Station;
import com.wisesoft.btsfare.modal.PathResult;
import com.wisesoft.btsfare.modal.PriceResultBTS;
import com.wisesoft.btsfare.repository.DiscountRepository;
import com.wisesoft.btsfare.repository.ExtensionFareRepository;
import com.wisesoft.btsfare.repository.FareRepository;

@Service
public class CalculateFareService {

    private final FareRepository fareRepository;
    private final ExtensionFareRepository extensionFareRepository;
    private final CountStationService countStationService;
    private final FindPathService findPathService;
    private final DiscountRepository discountRepository;
    private final StationService stationService;

    public CalculateFareService(FareRepository fareRepository, ExtensionFareRepository extensionFareRepository,
            CountStationService countStationService, FindPathService findPathService, StationService stationService,
            DiscountRepository discountRepository) {
        this.countStationService = countStationService;
        this.fareRepository = fareRepository;
        this.extensionFareRepository = extensionFareRepository;
        this.findPathService = findPathService;
        this.discountRepository = discountRepository;
        this.stationService = stationService;
    }

    public PriceResultBTS calculateFare(String startName, String endName) {
        // คำนวณเส้นทาง
        PathResult pathResult = findPathService.calculateRoute(startName, endName);
        StationDTO start = stationService.getStationDetail(startName);
        StationDTO end = stationService.getStationDetail(endName);

        // ดึงส่วนลดของลูกค้าตามประเภท
        int discountStudent = getDiscount("student");
        int discountAdult = getDiscount("adult");
        int discountSenior = getDiscount("senior");
        int discountSingle = getDiscount("single");

        List<Station> path = pathResult.getPath();
        int stationCount = countStationService.countFareStations(path);
        int maxStations = fareRepository.findMaxStationsNumber().orElseThrow(() -> new IllegalArgumentException("Invalid MaxStayion: "));
        BtsFare fare = fareRepository.findByStationNumber(Math.min(stationCount, maxStations))
                .orElseGet(() -> {
                    BtsFare defaultFare = new BtsFare();
                    defaultFare.setFare(0);
                    return defaultFare;
                });

        ExtensionFare extFareAdult = extensionFareRepository.findByType_CustomerType("adult").orElseThrow(() -> new IllegalArgumentException("Invalid customerType: "));
        ExtensionFare extFareStudent = extensionFareRepository.findByType_CustomerType("student").orElseThrow(() -> new IllegalArgumentException("Invalid customerType: "));
        ExtensionFare extFareSenior = extensionFareRepository.findByType_CustomerType("senior").orElseThrow(() -> new IllegalArgumentException("Invalid customerType: "));
        ExtensionFare extFareSingle= extensionFareRepository.findByType_CustomerType("single").orElseThrow(() -> new IllegalArgumentException("Invalid customerType: "));

        boolean passesExtension = path.stream().filter(Station::isExtension).count() > 1;
        boolean hasNonExtension = path.stream().anyMatch(station -> !station.isExtension());

        PriceResultBTS priceResult = new PriceResultBTS();
        priceResult.setPath(path.stream().map(station -> station.getStationName() +" | "+ station.getStationNameThai()).collect(Collectors.toList()));

        if (!hasNonExtension) {
            // กรณีที่ทุกสถานีเป็น extension
            priceResult.setSingle(extFareSingle.getPrice());
            priceResult.setAdult(extFareAdult.getPrice());
            priceResult.setStudent(extFareStudent.getPrice());
            priceResult.setSenior(extFareSenior.getPrice());
        } else if (passesExtension) {
            // กรณีที่มีการผ่านสถานี extension มากกว่า 1 ครั้ง
            priceResult.setSingle(calculatePriceWithExtension(fare.getFare(), discountSingle, extFareSingle.getPrice()));
            priceResult.setAdult(calculatePriceWithExtension(fare.getFare(), discountAdult, extFareAdult.getPrice()));
            priceResult.setStudent(calculatePriceWithExtension(fare.getFare(), discountStudent, extFareStudent.getPrice()));
            priceResult.setSenior(calculatePriceWithExtension(fare.getFare(), discountSenior, extFareSenior.getPrice()));
        } else {
            // กรณีที่ไม่มี extension หรือน้อยกว่าเงื่อนไข
            priceResult.setSingle(calculatePrice(fare.getFare(), discountSingle));
            priceResult.setAdult(calculatePrice(fare.getFare(), discountAdult));
            priceResult.setStudent(calculatePrice(fare.getFare(), discountStudent));
            priceResult.setSenior(calculatePrice(fare.getFare(), discountSenior));
        }

        priceResult.setDistance(pathResult.getDistance());
        priceResult.setTime(pathResult.getTime());
        priceResult.setStart(start.getStationName());
        priceResult.setEnd(end.getStationName());
        priceResult.setStartNameThai(start.getStationNameThai());
        priceResult.setEndNameThai(end.getStationNameThai());

        return priceResult;
    }

    private int getDiscount(String customerType) {
        return discountRepository.findByType_CustomerType(customerType)
                .map(Discount::getDiscount)
                .orElse(0);
    }

    private double calculatePrice(double baseFare, int discountPercent) {
        return Math.round(baseFare - ((baseFare * discountPercent) / 100));
    }

    private double calculatePriceWithExtension(double baseFare, int discountPercent, double extensionPrice) {
            return calculatePrice(baseFare, discountPercent) + extensionPrice;
    }
}

