package com.binjure.backend.Services;

import com.binjure.backend.Models.Movie;
import com.binjure.backend.Models.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class MovieService {

    private final String userNotFoundMessage = "Movie was not found for this ID: ";

    @Autowired
    private MovieRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Movie> getMovies() {
        return repository.findAll();
    }

    public List<Movie> getMoviesFeatured(String r) {
        if(r.trim().equalsIgnoreCase("true") || r.trim().equalsIgnoreCase("yes") ||
                r.trim().equalsIgnoreCase("featured")) {

            Query query = new Query();
            query.addCriteria(Criteria.where("isFeatured").is(true));
            return mongoTemplate.find(query, Movie.class);
        } else {
            return getMovies();
        }
    }

    public List<Movie> searchByTitle(String s) {
        if(!s.isEmpty()) {
            Query query = new Query();
            Pattern pattern = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("title").regex(pattern));
            return mongoTemplate.find(query, Movie.class);
        } else {
            return Collections.<Movie> emptyList();
        }
    }

    public String getNextMovieID() {
        final String prefix = "mov";
        List<Movie> allMovies = getMovies();
        if(allMovies.isEmpty()){
            return prefix + 1;
        } else {
            ArrayList<Integer> movieIds = new ArrayList<>();
            for (Movie m : allMovies) {
                movieIds.add(Integer.parseInt(m.getId().split(prefix)[1]));
            }
            int nextID = Collections.max(movieIds) + 1;
            return prefix + nextID;
        }
    }

    public void insertMovie(Movie movie) {
        String nextID = getNextMovieID();
        movie.setId(nextID);
        repository.insert(movie);
    }

    public Movie updateMovie(String id, Movie newMovieData) throws Exception {
        Movie movie = getMovieById(id);
        if (!Objects.isNull(movie)) {
            movie.setTitle(newMovieData.getTitle());
            movie.setSynopsis(newMovieData.getSynopsis());
            movie.setRatings(newMovieData.getRatings());
            movie.setGenre(newMovieData.getGenre());
            movie.setGenres(newMovieData.getGenres());
            movie.setRuntime(newMovieData.getRuntime());
            movie.setRatings(newMovieData.getRatings());
            movie.setRentPrice(newMovieData.getRentPrice());
            movie.setBuyPrice(newMovieData.getBuyPrice());
            movie.setSmallPoster(newMovieData.getSmallPoster());
            movie.setLargePoster(newMovieData.getLargePoster());
            movie.setFeatured(newMovieData.isFeatured());
            movie.setTrending(newMovieData.isTrending());
            return repository.save(movie);
        } else {
            throw new Exception(userNotFoundMessage+id);
        }
    }

    public Movie getMovieById(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("movieId").is(id));
        return mongoTemplate.findOne(query, Movie.class);
    }

    public boolean deleteMovie(String id) {
        Movie preDeletion = getMovieById(id);
        try {
            String movieId = preDeletion.getId();
            Query query = new Query();
            query.addCriteria(Criteria.where("movieId").is(id));
            Movie deleted = mongoTemplate.findAndRemove(query, Movie.class);
            try {
                Movie verify = getMovieById(deleted.getId());
                return true;
            } catch (NullPointerException nex) {
                return false;
            }
        } catch (NullPointerException nex) {
            return false;
        }
    }

}
