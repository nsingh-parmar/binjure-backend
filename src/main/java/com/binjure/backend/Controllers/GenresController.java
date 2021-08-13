package com.binjure.backend.Controllers;

import com.binjure.backend.CustomizedResponse;
import com.binjure.backend.Models.Genres;
import com.binjure.backend.Services.GenresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins="https://binjure.herokuapp.com")
@RestController
public class GenresController {


    @Autowired
    private GenresService genresService;

    @GetMapping("/api/genres")
    public ResponseEntity getGenres() {
        CustomizedResponse customizedResponse = null;
        try {
//            System.out.println(genresService.getGenres());
            customizedResponse = new CustomizedResponse(
                    "A list of genres",
                    genresService.getGenres());

            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch(Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/genres/{id}")
    public ResponseEntity getGenresById(@PathVariable("id")String id) {

        CustomizedResponse customizedResponse = null;
        try {
            System.out.println("id: ["+id+"]");
            System.out.println(genresService.getGenresById(id));
            customizedResponse = new CustomizedResponse(
                    "Genres with id " + id,
                    Collections.singletonList(genresService.getGenresById(id)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }


    @DeleteMapping("/api/genres/{id}")
    public ResponseEntity deleteGenres(@PathVariable("id")String id) {
        genresService.deleteGenres(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value="/api/genres", consumes={
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity addNewGenres(@RequestBody Genres genres) {
        try {
            genresService.insertGenres(genres);
            return new ResponseEntity(genres, HttpStatus.CREATED);
        } catch(Exception ex) {
            var customizedResponse = new CustomizedResponse(ex.getMessage(), null);
            System.out.println("Error occurred while adding genres: "+ex.getMessage());
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/api/genres/{id}", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity editGenres(@PathVariable("id")String id, @RequestBody Genres updatedGenres) {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse(" Genres with ID:  " + id + "was updated successfully ",
                    Collections.singletonList(genresService.updateGenres(id, updatedGenres)));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        } catch(Exception ex) {
            customizedResponse = new CustomizedResponse(ex.getMessage(), null);
            System.out.println("Error occurred while editing genres: "+ex.getMessage());
            return new ResponseEntity(customizedResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
