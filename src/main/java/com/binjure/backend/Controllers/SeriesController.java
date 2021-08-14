package com.binjure.backend.Controllers;

import com.binjure.backend.CustomizedResponse;
import com.binjure.backend.Models.Series;
import com.binjure.backend.Services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins="https://binjure.herokuapp.com")
@RestController
public class SeriesController {


    @Autowired
    private SeriesService seriesService;

    @GetMapping("/api/series")
    public ResponseEntity getSeries() {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse(
                    "A list of series",
                    seriesService.getSeries());

            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch(Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/series/where")
    public ResponseEntity getSeriesByParams(@RequestParam(value="featured")String r) {
        var customizedResponse = new CustomizedResponse(
                "A list of featured series: ",
                seriesService.getSeriesFeatured(r));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/api/series/{id}")
    public ResponseEntity getSeriesById(@PathVariable("id")String id) {
        CustomizedResponse customizedResponse = null;
        try {
            var fetchResponse = seriesService.getSeriesById(id);
            String fetchId = fetchResponse.getId();
            customizedResponse = new CustomizedResponse(
                    "Series with id " + id,
                    Collections.singletonList(fetchResponse));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @DeleteMapping("/api/series/{id}")
    public ResponseEntity deleteSeries(@PathVariable("id")String id) {

        try {
            boolean deletion = seriesService.deleteSeries(id);
            if(deletion) {
                var customizedResponse = new CustomizedResponse("Deletion successful", null);
                return new ResponseEntity(customizedResponse, HttpStatus.OK);
            } else {
                var customizedResponse = new CustomizedResponse("Deletion failed", null);
                return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            var customizedResponse = new CustomizedResponse(ex.getMessage(), null);
            System.out.println("Error occurred while deleting from series: "+ex.getMessage());
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/api/series", consumes={
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity addNewSeries(@RequestBody Series series) {
        try {
            seriesService.insertSeries(series);
            return new ResponseEntity(series, HttpStatus.CREATED);
        } catch (Exception ex) {
            var customizedResponse = new CustomizedResponse(ex.getMessage(), null);
            System.out.println("Error occurred while adding series: "+ex.getMessage());
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/api/series/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity editSeries(@PathVariable("id")String id, @RequestBody Series updatedSeries) {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse(" Series with ID:  " + id + "was updated successfully ",
                    Collections.singletonList(seriesService.updateSeries(id, updatedSeries)));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch(Exception ex) {
            customizedResponse = new CustomizedResponse(ex.getMessage(), null);
            System.out.println("Error occurred while editing series: "+ex.getMessage());
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

