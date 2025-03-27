package com.wisesoft.btsfare.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wisesoft.btsfare.dto.StationDTO;
import com.wisesoft.btsfare.entity.BtsFare;
import com.wisesoft.btsfare.entity.CustomerType;
import com.wisesoft.btsfare.entity.Discount;
import com.wisesoft.btsfare.entity.ExtensionFare;
import com.wisesoft.btsfare.entity.Station;
import com.wisesoft.btsfare.modal.PathResult;
import com.wisesoft.btsfare.modal.PriceResultBTS;
import com.wisesoft.btsfare.repository.CustomerTypeRepository;
import com.wisesoft.btsfare.repository.DiscountRepository;
import com.wisesoft.btsfare.repository.ExtensionFareRepository;
import com.wisesoft.btsfare.repository.FareRepository;

@Service
public class CalculateFareService {

    private final FareRepository fareRepository;
    private final ExtensionFareRepository extensionFareRepository;
    private final FindPathService findPathService;
    private final DiscountRepository discountRepository;
    private final StationService stationService;
    private final CustomerTypeRepository customerTypeRepository;

    public CalculateFareService(FareRepository fareRepository, ExtensionFareRepository extensionFareRepository,
            FindPathService findPathService, StationService stationService,
            DiscountRepository discountRepository,CustomerTypeRepository customerTypeRepository) {
        this.fareRepository = fareRepository;
        this.extensionFareRepository = extensionFareRepository;
        this.findPathService = findPathService;
        this.discountRepository = discountRepository;
        this.stationService = stationService;
        this.customerTypeRepository = customerTypeRepository;
    }

    public PriceResultBTS calculateFare(String startName, String endName) {
        PathResult pathResult = findPathService.calculateRoute(startName, endName);
        StationDTO start = stationService.getStationDetail(startName);
        StationDTO end = stationService.getStationDetail(endName);
        List<Station> path = pathResult.getPath();

        Map<String, Integer> discountMap = discountRepository.findAll().stream()
                .collect(Collectors.toMap(d -> d.getType().getCustomerType(), Discount::getDiscount));

        Map<String, Double> extensionFareMap = extensionFareRepository.findAll().stream()
                .collect(Collectors.toMap(f -> f.getType().getCustomerType(), ExtensionFare::getPrice));

        // int stationCount = countStationService.countFareStations(path);
        int stationCount = pathResult.getDistance() - 1;
        int maxStations = fareRepository.findMaxStationsNumber()
                .orElseThrow(() -> new IllegalArgumentException("Invalid MaxStation"));
        BtsFare fare = fareRepository.findByStationNumber(Math.min(stationCount, maxStations))
                .orElseGet(() -> {
                    BtsFare defaultFare = new BtsFare();
                    defaultFare.setFare(0);
                    return defaultFare;
                });

        boolean passesExtension = path.stream().filter(Station::isExtension).count() > 1;
        boolean hasNonExtension = path.stream().anyMatch(station -> !station.isExtension());

        PriceResultBTS priceResult = new PriceResultBTS();
        priceResult.setPath(path.stream()
                .map(station -> station.getStationName() + " | " + station.getStationNameThai())
                .collect(Collectors.toList()));

        List<CustomerType> customerTypes = customerTypeRepository.findAll();

        for (CustomerType customerType : customerTypes) {
            int discount = discountMap.getOrDefault(customerType.getCustomerType(), 0);   
            double extensionFare = extensionFareMap.getOrDefault(customerType.getCustomerType(), 0.0);

            double price;
            if (!hasNonExtension) {
                price = extensionFare;
            } else if (passesExtension) {
                price = calculatePriceWithExtension(fare.getFare(), discount, extensionFare);
            } else {
                price = calculatePrice(fare.getFare(), discount);
            }

            switch (customerType.getCustomerType()) {
                case "single" -> priceResult.setSingle(price);
                case "adult" -> priceResult.setAdult(price);
                case "student" -> priceResult.setStudent(price);
                case "senior" -> priceResult.setSenior(price);
            }
        }

        priceResult.setDistance(pathResult.getPath().size());
        priceResult.setTime(pathResult.getTime());
        priceResult.setStart(start.getStationName());
        priceResult.setEnd(end.getStationName());
        priceResult.setStartNameThai(start.getStationNameThai());
        priceResult.setEndNameThai(end.getStationNameThai());

        return priceResult;
    }

    private double calculatePrice(double baseFare, int discountPercent) {
        return Math.round(baseFare - ((baseFare * discountPercent) / 100));
    }

    private double calculatePriceWithExtension(double baseFare, int discountPercent, double extensionPrice) {
        return calculatePrice(baseFare, discountPercent) + extensionPrice;
    }
}
