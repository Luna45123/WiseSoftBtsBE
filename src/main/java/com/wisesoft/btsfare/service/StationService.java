package com.wisesoft.btsfare.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wisesoft.btsfare.dto.StationDTO;
import com.wisesoft.btsfare.entity.Station;
import com.wisesoft.btsfare.mapper.StationMapper;
import com.wisesoft.btsfare.repository.StationRepository;

@Service
public class StationService {

    private final StationRepository stationRepository;

    private final StationMapper stationMapper;

    

    public StationService(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }



    public List<StationDTO> getStationByLine(String lineName) {
        List<Station> stations = stationRepository.findByLineName(lineName).orElseThrow(() -> new IllegalArgumentException("Invalid station name"));
        List<StationDTO> dtos = new ArrayList<StationDTO>();

        stationMapper.updateStationFromEntity(stations, dtos);
        return dtos;
    }

    public StationDTO getStationDetail(String stationName) {
        Station station = stationRepository.findByStationName(stationName).orElseThrow(() -> new IllegalArgumentException("Invalid station name"));
        StationDTO dto = new StationDTO();
        stationMapper.updateStationFromEntity(station, dto);
        return dto;
    }
}
