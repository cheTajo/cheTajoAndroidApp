package com.teamlz.cheTajo.object;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by francesco on 05/05/16.
 */

public class HairDresser {
    private String id;
    private String shopName;
    private List<String> followers;
    private List<String> likes;
    private int numFollowers;
    private int numLikes;

    public HairDresser(){}

    public HairDresser(String id, String shopName){
        this.id = id;
        this.shopName = shopName;
        this.followers = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.numFollowers = 0;
        this.numLikes = 0;
    }

    public String getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void addFollower(String id) {
        this.followers.add(id);
        this.numFollowers += 1;
    }

    public void removeFollower(String id) {
        this.followers.remove(id);
        this.numFollowers -= 1;
    }

    public void addLike(String id) {
        this.likes.add(id);
        this.numLikes += 1;
    }

    public void removeLike(String id) {
        this.likes.remove(id);
        this.numLikes -= 1;
    }

    @Override
    public String toString() {
        String ret = shopName;
        //ret += ": LIKES=" + likes.toString() + " FOLLOWERS=" + followers.toString();
        //ret += " NUMLIKES=" + numLikes + " NUMFOLLOWERS=" + numFollowers;
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(getClass())) return false;
        HairDresser hd = (HairDresser) o;
        if (id.equals(hd.id)) return true;
        return false;
    }
}
