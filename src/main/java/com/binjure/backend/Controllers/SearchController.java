package com.binjure.backend.Controllers;

import com.binjure.backend.CustomizedResponse;
import com.binjure.backend.Services.MovieService;
import com.binjure.backend.Services.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@CrossOrigin(origins="https://binjure.herokuapp.com")
@RestController
public class SearchController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private SeriesService seriesService;

	@GetMapping("/api/search")
	public ResponseEntity searchByTitle(@RequestParam(value="title")String subString) {
		var movieSearchResults = movieService.searchByTitle(subString).toArray();
		var seriesSearchResults = seriesService.searchByTitle(subString).toArray();
		ArrayList<Object> finalResults = new ArrayList<>(Arrays.asList(movieSearchResults));
		finalResults.addAll(Arrays.asList(seriesSearchResults));
		var customizedResponse = new CustomizedResponse(
				"A list of movies and shows which contain "+subString+" in title.",
				finalResults);
		return new ResponseEntity(customizedResponse, HttpStatus.OK);
	}
}
