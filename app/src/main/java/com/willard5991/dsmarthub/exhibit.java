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
    //private Location loc;
    private int clicks;
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

    //public Location getLoc() { return loc; }

    //public void setLoc(Location loc) {
    //    this.loc = loc;
    //}

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

}

