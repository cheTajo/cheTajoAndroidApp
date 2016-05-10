package com.teamlz.cheTajo.object;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by francesco on 05/05/16.
 */
public class HairDresser {

    private String firstName;
    private String lastName;
    private String shopName;
    private List<String> followers;
    private List<String> likes;
    private int numFollowers;
    private int numLikes;

    public HairDresser(){}

    public HairDresser(String shopName, String firstName, String lastName){
        this.shopName = shopName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.followers = new ArrayList<>();
        this.followers.add("email@example.com");
        this.likes = new ArrayList<>();
        this.likes.add("email@example.com");
        this.numFollowers = 0;
        this.numLikes = 0;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public List<String> getLikes() {
        return likes;
    }

    private void incNumFollowers() {
        this.numFollowers += 1;
    }

    private void decNumFollowers() {
        this.numFollowers -= 1;
    }

    private void incNumLikes() {
        this.numLikes += 1;
    }

    private void decNumLikes() {
        this.numLikes -= 1;
    }

    public void addFollower(String email) {
        this.followers.add(email);
        this.incNumFollowers();
    }

    public void removeFollower(String email) {
        this.followers.remove(email);
        this.decNumFollowers();
    }

    public void addLike(String email) {
        this.likes.add(email);
        this.incNumLikes();
    }

    public void removeLike(String email) {
        this.likes.remove(email);
        this.decNumLikes();
    }
}
