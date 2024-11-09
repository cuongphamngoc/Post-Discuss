package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.CreateSeriesDTO;
import com.cuongpn.dto.responeDTO.SeriesDTO;
import com.cuongpn.dto.responeDTO.SeriesDetailDTO;
import com.cuongpn.security.services.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeriesService {
    Page<SeriesDTO> getAll(Pageable pageable);

    SeriesDTO saveNewSeries(CreateSeriesDTO createSeriesDTO);

    void deleteSeries(Long id);

    SeriesDetailDTO getSeriesBySlug(String slug);

    List<SeriesDTO> getSeriesOfCurrentUser(UserPrincipal userPrincipal);
}
