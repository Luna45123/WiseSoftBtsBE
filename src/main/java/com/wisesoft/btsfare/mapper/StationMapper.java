package com.wisesoft.btsfare.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.wisesoft.btsfare.dto.StationDTO;
import com.wisesoft.btsfare.entity.Station;

@Mapper(componentModel = "spring")
public interface StationMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateStationFromEntity(List<Station> stations, @MappingTarget List<StationDTO> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateStationFromEntity(Station stations, @MappingTarget StationDTO dtos);
}
