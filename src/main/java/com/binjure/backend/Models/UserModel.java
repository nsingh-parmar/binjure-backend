package com.binjure.backend.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document("users")
public class UserModel {

    @Id
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<String> mediaBought;
    private ArrayList<String> mediaRented;


    public UserModel() {    }

    public UserModel(String id, String firstName, String lastName, String email, String password,
                     ArrayList<String> mediaBought, ArrayList<String> mediaRented) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.mediaBought = mediaBought;
        this.mediaRented = mediaRented;
    }

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getMediaBought() {
        return mediaBought;
    }

    public void setMediaBought(ArrayList<String> mediaBought) {
        this.mediaBought = mediaBought;
    }

    public ArrayList<String> getMediaRented() {
        return mediaRented;
    }

    public void setMediaRented(ArrayList<String> mediaRented) {
        this.mediaRented = mediaRented;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mediaBought=" + mediaBought.toString() +
                ", mediaRented=" + mediaRented.toString() +
                '}';
    }
}