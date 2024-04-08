package com.nataliia.koval.movieland.mapper;

import com.nataliia.koval.movieland.dto.CountryDto;
import com.nataliia.koval.movieland.entity.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDto toCountryDto(Country country);
}
