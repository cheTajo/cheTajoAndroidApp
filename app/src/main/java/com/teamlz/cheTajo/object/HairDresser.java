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
    private double longitude;
    private double latitude;

    public HairDresser(){}

    public HairDresser(String id, String shopName){
        this.id = id;
        this.shopName = shopName;
        this.followers = new ArrayList<>();
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

    public List<String> getFollowers() {
        return followers;
    }

    public void setLongitude(double longitude){ this.longitude=longitude; }

    public void setLatitude(double latitude){ this.latitude=latitude; }

    public double getLongitude(){ return longitude; }

    public double getLatitude(){ return latitude; }

    public void addFollower(String id) {
        this.followers.add(id);
    }

    public void removeFollower(String id) {
        this.followers.remove(id);
    }

    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(getClass())) return false;
        HairDresser hd = (HairDresser) o;
        return (id.equals(hd.id));
    }
}
