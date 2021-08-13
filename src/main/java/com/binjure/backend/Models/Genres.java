package com.binjure.backend.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("genres")
public class Genres {
    @Id
    private String id;
    private String genreId;
    private String genre;

    public Genres() {
    }

    public Genres(String id, String genre) {
        this.genreId = id;
        this.genre = genre;

    }

    public String getId() {
        return genreId;
    }

    public void setId(String id) {
        this.genreId = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Genres{" +
                "id='" + id + '\'' +
                ", genreId='" + genreId + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}