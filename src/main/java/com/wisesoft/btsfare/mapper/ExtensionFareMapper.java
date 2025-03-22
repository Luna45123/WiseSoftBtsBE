package com.wisesoft.btsfare.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.wisesoft.btsfare.dto.ExtensionFareDTO;
import com.wisesoft.btsfare.entity.ExtensionFare;

@Mapper(componentModel = "spring")
public interface ExtensionFareMapper {
    // @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // public void updateExtensionFromEntity(List<ExtensionFare> entity, @MappingTarget List<ExtensionFareDTO> dtos);

    // @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // public void updateExtensionFromDto( List<ExtensionFareDTO> dtos , @MappingTarget List<ExtensionFare> entity);
}
