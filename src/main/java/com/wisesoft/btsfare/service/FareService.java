package com.wisesoft.btsfare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.wisesoft.btsfare.dto.BtsFareDTO;
import com.wisesoft.btsfare.dto.DiscountDTO;
import com.wisesoft.btsfare.entity.BtsFare;
import com.wisesoft.btsfare.entity.CustomerType;
import com.wisesoft.btsfare.entity.Discount;
import com.wisesoft.btsfare.mapper.BtsFareMapper;
import com.wisesoft.btsfare.repository.CustomerTypeRepository;
import com.wisesoft.btsfare.repository.DiscountRepository;
import com.wisesoft.btsfare.repository.FareRepository;

import jakarta.transaction.Transactional;

@Service
public class FareService {
    private final BtsFareMapper btsFareMapper;

    private final FareRepository fareRepository;


 

    public FareService(BtsFareMapper btsFareMapper, FareRepository fareRepository) {
        this.btsFareMapper = btsFareMapper;
        this.fareRepository = fareRepository;
    }


    public List<BtsFareDTO> getAllBtsFare() {
        List<BtsFare> btsFares = fareRepository.findAllOrderByNumberStation().get();
        List<BtsFareDTO> dtos = new ArrayList<BtsFareDTO>();
        btsFareMapper.updateStationFromEntity(btsFares, dtos);
        return dtos;
    }


    @Transactional
    public String updateFare(List<BtsFareDTO> fares) {
        List<Long> delete = new ArrayList<>();
        fares.stream().map(fareDto -> {
            Optional<BtsFare> existingFareOpt = fareRepository.findById(fareDto.getId());

            BtsFare fare;

            if (existingFareOpt.isPresent()) {
                fare = existingFareOpt.get();
            } else {
                fare = new BtsFare();
            }

            if (fareDto.getId() != 0 && fareDto.isDelete()) {
                delete.add(fare.getId());
            }
            fare.setStationNumber(fareDto.getStationNumber());
            fare.setFare(fareDto.getFare());
            return fareRepository.save(fare);
        }).collect(Collectors.toList());

        if (!delete.isEmpty()) {
            deleteBtsFare(delete);
        }
        return "";
    }

    @Transactional
    public void deleteBtsFare(List<Long> id) {
        fareRepository.deleteAllById(id);
    }
}
