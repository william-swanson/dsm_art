package com.willard5991.dsmarthub;
//import android.location.Location;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.RealmList;

/**
 * Created by $ on 10/4/2017.
 */

public class exhibit extends RealmObject
{
    @PrimaryKey
    private String name;
    private String artist;
    private int year;
    private String medium;
    private int clicks;

    private double latitude;
    private double longitude;

    public byte[] image;

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYear() { return year; }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium (String medium) {
        this.medium = medium;
    }

//    public Location getLoc() { return loc; }
//
    public void setLoc(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public byte[] getImage(){
        return image;
    }

    public void setImage(byte[] image){
        this.image=image;
    }

    public double calcCrowDistance(double lat2, double lon2){
        double earthRad = 6371 * 1000; //km to m conversion
        double lat1Rad = Math.toRadians(this.latitude);
        double lat2Rad = Math.toRadians(lat2);

        //Calculate change in radians
        double deltaLatRad = Math.toRadians(lat2 - this.latitude);
        double deltaLonRad = Math.toRadians(lon2 - this.longitude);

        //calculate haversine function
        double a = Math.sin(deltaLatRad/2)*Math.sin(deltaLatRad/2) +
                    Math.cos(lat1Rad)*Math.cos(lat2Rad) *
                    Math.sin(deltaLonRad/2)*Math.sin(deltaLonRad/2);
        double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        double d = earthRad * c;

        return d;
    }

}

