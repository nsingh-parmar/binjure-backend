package com.binjure.backend.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;


@Document("movies")
public class Movie {

    @Id
    private String id;
    private String movieId;
    private String title;
    private String synopsis;
    private String genre;
    private HashMap<String, Integer> ratings;
    private ArrayList<String> genres;
    private int runtime;
    private String smallPoster;
    private String largePoster;
    private double rentPrice;
    private double buyPrice;
    private boolean isFeatured;
    private boolean isTrending;

    public Movie() {
    }

    public Movie(String id, String title, String synopsis, String genre, HashMap<String, Integer> ratings,
                 ArrayList<String> genres, int runtime, String smallPoster, String largePoster, double rentPrice,
                 double buyPrice, boolean isFeatured, boolean isTrending) {
        this.movieId = id;
        this.title = title;
        this.synopsis = synopsis;
        this.genre = genre;
        this.ratings = ratings;
        this.genres = genres;
        this.runtime = runtime;
        this.smallPoster = smallPoster;
        this.largePoster = largePoster;
        this.rentPrice = rentPrice;
        this.buyPrice = buyPrice;
        this.isFeatured = isFeatured;
        this.isTrending = isTrending;
    }

    public String getId() {
        return movieId;
    }

    public void setId(String id) {
        this.movieId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public HashMap<String, Integer> getRatings() {
        return ratings;
    }

    public void setRatings(HashMap<String, Integer> ratings) {
        this.ratings = ratings;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getSmallPoster() {
        return smallPoster;
    }

    public void setSmallPoster(String smallPoster) {
        this.smallPoster = smallPoster;
    }

    public String getLargePoster() {
        return largePoster;
    }

    public void setLargePoster(String largePoster) {
        this.largePoster = largePoster;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public boolean isTrending() {
        return isTrending;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", movieId='" + movieId + '\'' +
                ", title='" + title + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", genre='" + genre + '\'' +
                ", ratings=" + ratings.toString() +
                ", genres=" + genres.toString() +
                ", runtime=" + runtime +
                ", smallPoster='" + smallPoster + '\'' +
                ", largePoster='" + largePoster + '\'' +
                ", rentPrice=" + rentPrice +
                ", buyPrice=" + buyPrice +
                ", isFeatured=" + isFeatured +
                ", isTrending=" + isTrending +
                '}';
    }
}