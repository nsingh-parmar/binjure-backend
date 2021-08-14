package com.binjure.backend.Controllers;

import com.binjure.backend.CustomizedResponse;
import com.binjure.backend.Models.Movie;
import com.binjure.backend.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins="https://binjure.herokuapp.com")
@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/api/movies")
    public ResponseEntity getMovies() {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse(
                    "A list of movies",
                    movieService.getMovies());

            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch(Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/movies/where")
    public ResponseEntity getMoviesByParams(@RequestParam(value="featured")String flag) {
        var customizedResponse = new CustomizedResponse(
                "A list of movies which are featured: ",
                movieService.getMoviesFeatured(flag));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }


    @GetMapping("/api/movies/{id}")
    public ResponseEntity getMovieById(@PathVariable("id")String id) {

        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse(
                    "Movie with id " + id,
                    Collections.singletonList(movieService.getMovieById(id)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity(
                new CustomizedResponse("No movie of this ID was found", null),
                HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/api/movies/{id}")
    public ResponseEntity deleteMovie(@PathVariable("id")String id) {
        try {
            boolean deletion = movieService.deleteMovie(id);
            if(deletion) {
                var customizedResponse = new CustomizedResponse("Deletion successful", null);
                return new ResponseEntity(customizedResponse, HttpStatus.OK);
            } else {
                var customizedResponse = new CustomizedResponse("Deletion failed", null);
                return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception ex) {
            var customizedResponse = new CustomizedResponse(ex.getMessage(), null);
            System.out.println("Error occurred while deleting from movies: "+ex.getMessage());
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value="/api/movies", consumes={
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity addNewMovie(@RequestBody Movie movie) {
        try {
            movieService.insertMovie(movie);
            return new ResponseEntity(movie, HttpStatus.CREATED);
        } catch (Exception ex) {
            var customizedResponse = new CustomizedResponse(ex.getMessage(), null);
            System.out.println("Error occurred while adding movies: "+ex.getMessage());
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/api/movies/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity editMovie(@PathVariable("id")String id, @RequestBody Movie updatedMovie) {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse(" Movie with ID:  " + id + "was updated successfully ",
                    Collections.singletonList(movieService.updateMovie(id, updatedMovie)));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch(Exception ex) {
            customizedResponse = new CustomizedResponse(ex.getMessage(), null);
            System.out.println("Error occurred while editing movie: "+ex.getMessage());
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
