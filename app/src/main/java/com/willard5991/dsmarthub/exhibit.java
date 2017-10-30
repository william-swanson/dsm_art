package com.willard5991.dsmarthub;
//import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

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
    private String year;
    private String medium;
    private String desc;
    private int clicks;
    public RealmList<Photo> photos;

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

    public String getYear() { return year; }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMedium() {
        return medium;
    }

    public void setDesc (String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setMedium (String medium) {
        this.medium = medium;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public RealmList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(RealmList<Photo> photos) {
        this.photos = photos;
    }

    public void appendPhoto(Photo photo) {
        this.photos.add(photo);
    }
}

