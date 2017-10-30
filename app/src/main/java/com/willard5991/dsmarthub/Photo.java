package com.willard5991.dsmarthub;

import io.realm.RealmObject;

/**
 * Created by clayton on 10/27/17.
 */

public class Photo extends RealmObject {
    private String name;
    private byte[] image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
