package com.binjure.backend.Services;

import com.binjure.backend.Models.Genres;
import com.binjure.backend.Models.GenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class GenresService {


    private final String userNotFoundMessage = "Genres was not found for this ID: ";

    @Autowired
    private GenresRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Genres> getGenres() {
        return repository.findAll();
    }

    public String getNextGenresID() {
        final String prefix = "gen";
        List<Genres> allGenres = getGenres();
        ArrayList<Integer> genreIds = new ArrayList<>();
        for(Genres m: allGenres) {
            genreIds.add(Integer.parseInt(m.getId().split(prefix)[1]));
        }
        int nextID = Collections.max(genreIds);
        return prefix + nextID;
    }

    public void insertGenres(Genres genre) {
        genre.setId(getNextGenresID());
        repository.insert(genre);
    }

    public Genres updateGenres(String id, Genres newGenresData) throws Exception {
        Genres genre = getGenresById(id);
        if (!Objects.isNull(genre)) {
            genre.setGenre(newGenresData.getGenre());
            return repository.save(genre);
        } else {
            throw new Exception(userNotFoundMessage+id);
        }
    }

    public Genres getGenresById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("genreId").is(id));
        return mongoTemplate.findOne(query, Genres.class);
    }

    public void deleteGenres(String id)
    {
        repository.deleteById(id);
    }

}
