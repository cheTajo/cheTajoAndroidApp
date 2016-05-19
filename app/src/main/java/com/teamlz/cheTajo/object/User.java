package com.teamlz.cheTajo.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by francesco on 05/05/16.
 */
@SuppressWarnings("unused")
public class User implements Serializable{
    private String firstName;
    private String lastName;
    private String email;

    private List<String> followed;

    public User(){}

    public User(String email, String firstName, String lastName){
        this.email = email.trim();
        this.firstName = "";
        this.lastName = "";
        followed = new ArrayList<>();

        String[] parts = firstName.split(" ");
        for (String part : parts) {
            this.firstName += part.substring(0, 1).toUpperCase();
            this.firstName += part.substring(1).toLowerCase() + " ";
        }
        this.firstName = this.firstName.trim();

        parts = lastName.split(" ");
        for (String part : parts) {
            this.lastName += part.substring(0, 1).toUpperCase();
            this.lastName += part.substring(1).toLowerCase() + " ";
        }
        this.lastName = this.lastName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public void initFollowed() {
        followed = new ArrayList<>();
    }

    public List<String> getFollowed() {
        return followed;
    }

    public void addFollowed (String id) {
        followed.add(id);
    }

    public void removeFollowed (String id) {
        followed.remove(id);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
