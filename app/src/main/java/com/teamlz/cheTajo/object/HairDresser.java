package com.teamlz.cheTajo.object;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by francesco on 05/05/16.
 */
@SuppressWarnings("unused")
public class HairDresser {
    private String id;
    private String shopName;
    private List<String> followers;
    private List<String> likes;

    public HairDresser(){}

    public HairDresser(String id, String shopName){
        this.id = id;
        this.shopName = shopName;
        this.followers = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public void initFollowers() {
        followers = new ArrayList<>();
    }

    public void initLikes() {
        likes = new ArrayList<>();
    }

    public List<String> getFollowers() {
        return followers;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void addFollower(String id) {
        this.followers.add(id);
    }

    public void removeFollower(String id) {
        this.followers.remove(id);
    }

    public void addLike(String id) {
        this.likes.add(id);
    }

    public void removeLike(String id) {
        this.likes.remove(id);
    }

    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(getClass())) return false;
        HairDresser hd = (HairDresser) o;
        return (id.equals(hd.id));
    }
}
