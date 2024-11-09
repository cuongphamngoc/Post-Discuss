package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateSeriesDTO;
import com.cuongpn.dto.responeDTO.SeriesDTO;
import com.cuongpn.dto.responeDTO.SeriesDetailDTO;
import com.cuongpn.entity.Article;
import com.cuongpn.entity.Series;
import com.cuongpn.entity.Tag;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.SeriesMapper;
import com.cuongpn.repository.ArticleRepository;
import com.cuongpn.repository.SeriesRepository;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.SeriesService;
import com.cuongpn.service.TagService;
import com.cuongpn.service.VoteService;
import com.cuongpn.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeriesServiceImpl implements SeriesService {
    private final SeriesRepository seriesRepository;
    private final SeriesMapper seriesMapper;
    private final TagService tagService;
    private final VoteService voteService;
    private final ArticleRepository articleRepository;
    @Override
    public Page<SeriesDTO> getAll(Pageable pageable) {
        return seriesRepository.findAll(pageable).map(seriesMapper::toSeriesDTO);
    }

    @Override
    public SeriesDTO saveNewSeries(CreateSeriesDTO createSeriesDTO) {
        Set<Tag> tags = Optional.ofNullable(createSeriesDTO.getTags())
                .map(tagSet -> tagSet.stream()
                        .map(tagService::getTagByTagName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        Set<Article> articles = Optional.ofNullable(createSeriesDTO.getArticles())
                .map(articleList -> articleList.stream()
                        .map(articleRepository::findById)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        Series series = Series.builder()
                .title(createSeriesDTO.getTitle())
                .slug(SlugUtil.makeSlug(createSeriesDTO.getTitle()))
                .content(createSeriesDTO.getContent())
                .tags(tags)
                .articles(articles)
                .build();
        Series savedSeries = seriesRepository.save(series);
        articles.forEach(article -> article.setSeries(savedSeries));
        articleRepository.saveAll(articles);
        return seriesMapper.toSeriesDTO(savedSeries);

    }

    @Override
    public void deleteSeries(Long id) {
        seriesRepository.deleteById(id);
    }

    @Override
    public SeriesDetailDTO getSeriesBySlug(String slug) {
        Series series =  seriesRepository.findBySlug(slug).orElseThrow(()-> new AppException(ErrorCode.POST_NOT_EXISTED));
        SeriesDetailDTO seriesDetailDTO =  seriesMapper.toSeriesDetailDTO(series);
        seriesDetailDTO.setTotalVote(voteService.getVoteCount(seriesDetailDTO.getId()));
        seriesDetailDTO.setTotalComments(series.getComments().size());
        return seriesDetailDTO;
    }

    @Override
    public List<SeriesDTO> getSeriesOfCurrentUser(UserPrincipal userPrincipal) {
        if(userPrincipal == null) return List.of();
        return seriesRepository.findByCreatedBy_Id(userPrincipal.getId()).stream().map(seriesMapper::toSeriesDTO).collect(Collectors.toList());
    }
}
