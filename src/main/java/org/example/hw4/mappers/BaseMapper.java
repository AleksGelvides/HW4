package org.example.hw4.mappers;

import org.mapstruct.*;

@MapperConfig(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BaseMapper<ENTITY, RESPONSE_DTO, REQUEST_DTO> {

    RESPONSE_DTO toDto(ENTITY entity);

    ENTITY toEntity(RESPONSE_DTO dto);

    ENTITY requestToEntity(REQUEST_DTO dto);

    ENTITY merge(@MappingTarget ENTITY oldEntity, ENTITY newEntity);
}
