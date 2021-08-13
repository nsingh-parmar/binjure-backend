package com.binjure.backend.Services;

import com.binjure.backend.Models.Series;
import com.binjure.backend.Models.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class SeriesService {
    private final String userNotFoundMessage = "Series was not found for this ID: ";

    @Autowired
    private SeriesRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Series> getSeries() {
        return repository.findAll();
    }

    public List<Series> getSeriesFeatured(String r) {
        if(r.trim().equalsIgnoreCase("true") || r.trim().equalsIgnoreCase("yes") ||
                r.trim().equalsIgnoreCase("featured")) {

            Query query = new Query();
            query.addCriteria(Criteria.where("isFeatured").is(true));
            return mongoTemplate.find(query, Series.class);
        } else {
            return getSeries();
        }
    }

    public String getNextSeriesID() {
        final String prefix = "ser";
        List<Series> allSeries = getSeries();
        if(allSeries.isEmpty()){
            return prefix + 1;
        } else {
            ArrayList<Integer> seriesIds = new ArrayList<>();
            for (Series m : allSeries) {
                seriesIds.add(Integer.parseInt(m.getId().split(prefix)[1]));
            }
            int nextID = Collections.max(seriesIds) + 1;
            return prefix + nextID;
        }
    }

    public void insertSeries(Series series) {
        String nextID = getNextSeriesID();
        series.setId(nextID);
        repository.insert(series);
    }

    public List<Series> searchByTitle(String s) {
        if(!s.isEmpty()) {
            Query query = new Query();
            Pattern pattern = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("title").regex(pattern));
            return mongoTemplate.find(query, Series.class);
        } else {
            return Collections.<Series> emptyList();
        }
    }

    public Series updateSeries(String id, Series newSeriesData) throws Exception {
        Series series = getSeriesById(id);
        if (!Objects.isNull(series)) {
            series.setTitle(newSeriesData.getTitle());
            series.setSynopsis(newSeriesData.getSynopsis());
            series.setRatings(newSeriesData.getRatings());
            series.setGenre(newSeriesData.getGenre());
            series.setGenres(newSeriesData.getGenres());
            series.setRuntime(newSeriesData.getRuntime());
            series.setRatings(newSeriesData.getRatings());
            series.setRentPrice(newSeriesData.getRentPrice());
            series.setBuyPrice(newSeriesData.getBuyPrice());
            series.setSmallPoster(newSeriesData.getSmallPoster());
            series.setLargePoster(newSeriesData.getLargePoster());
            series.setFeatured(newSeriesData.isFeatured());
            series.setTrending(newSeriesData.isTrending());
            return repository.save(series);
        } else {
            throw new Exception(userNotFoundMessage+id);
        }
    }

    public Series getSeriesById(String id) {

        Query query = new Query();
        query.addCriteria(Criteria.where("seriesId").is(id));
        return mongoTemplate.findOne(query, Series.class);
    }

    public boolean deleteSeries(String id) {
        Series preDeletion = getSeriesById(id);
        try {
            String seriesId = preDeletion.getId();
            Query query = new Query();
            query.addCriteria(Criteria.where("seriesId").is(id));
            Series deleted = mongoTemplate.findAndRemove(query, Series.class);
            try {
                Series verify = getSeriesById(deleted.getId());
                return true;
            } catch (NullPointerException nex) {
                return false;
            }
        } catch (NullPointerException nex) {
            return false;
        }
    }

}
