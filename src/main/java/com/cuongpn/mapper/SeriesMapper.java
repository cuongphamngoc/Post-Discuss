package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.SeriesDTO;
import com.cuongpn.dto.responeDTO.SeriesDetailDTO;
import com.cuongpn.entity.Series;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeriesMapper {
    @Mapping(source = "createdBy", target = "author")
    SeriesDTO toSeriesDTO(Series series);
    @Mapping(source = "createdBy", target = "author")

    SeriesDetailDTO toSeriesDetailDTO(Series series);
}
