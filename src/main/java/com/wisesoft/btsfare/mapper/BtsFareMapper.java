package com.wisesoft.btsfare.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.wisesoft.btsfare.dto.BtsFareDTO;
import com.wisesoft.btsfare.entity.BtsFare;

@Mapper(componentModel = "spring")
public interface BtsFareMapper {
    // @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // public void updateBtsFareFromEntity(BtsFare entity, @MappingTarget BtsFareDTO dto);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateStationFromEntity(List<BtsFare> stations, @MappingTarget List<BtsFareDTO> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public void updateStationFromDTO(List<BtsFareDTO> dtos, @MappingTarget List<BtsFare> stations);
}
