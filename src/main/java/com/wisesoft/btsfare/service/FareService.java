package com.wisesoft.btsfare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.wisesoft.btsfare.dto.BtsFareDTO;
import com.wisesoft.btsfare.entity.BtsFare;
import com.wisesoft.btsfare.mapper.BtsFareMapper;
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
    public void updateFare(List<BtsFareDTO> fares) {
        List<Long> delete = new ArrayList<>();

        for (BtsFareDTO fareDto : fares) {
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

            fareRepository.save(fare);
        }

        if (!delete.isEmpty()) {
            deleteBtsFare(delete);
        }
    }

    @Transactional
    public void deleteBtsFare(List<Long> id) {
        fareRepository.deleteAllById(id);
    }
}
