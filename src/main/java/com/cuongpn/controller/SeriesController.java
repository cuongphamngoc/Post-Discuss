package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateSeriesDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
@RequiredArgsConstructor
public class SeriesController {
    private final SeriesService seriesService;

    @GetMapping
    public ResponseData<?> handleGetAllSeries(Pageable pageable){
        return new ResponseData<>(HttpStatus.OK.value(), "Get series successful",seriesService.getAll(pageable));
    }
    @GetMapping("/{slug}")
    public ResponseData<?> handleGetSeriesBySlug(@PathVariable("slug") String slug){
        return new ResponseData<>(HttpStatus.OK.value(),"Get series successful", seriesService.getSeriesBySlug(slug));
    }
    @PostMapping
    public ResponseData<?> handleCreateNewSeries(@RequestBody  CreateSeriesDTO createSeriesDTO){
        System.out.println("Process...series");
        return new ResponseData<>(HttpStatus.CREATED.value(), "Create series successful", seriesService.saveNewSeries(createSeriesDTO));
    }
    @DeleteMapping("/{seriesId}")
    public ResponseData<?> deleteSeries(@PathVariable("seriesId") Long id){
        seriesService.deleteSeries(id);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete series successful");
    }
    @GetMapping("/current-user")
    public ResponseData<?> getSeriesOfCurrentUser(@CurrentUser UserPrincipal userPrincipal){
        return new ResponseData<>(HttpStatus.OK.value(),"Success", seriesService.getSeriesOfCurrentUser(userPrincipal));
    }


}
