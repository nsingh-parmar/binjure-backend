package com.binjure.backend.Models;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends MongoRepository<Series, String> {

}