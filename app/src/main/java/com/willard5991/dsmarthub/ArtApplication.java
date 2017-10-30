package com.willard5991.dsmarthub;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by clayton on 10/29/17.
 */

//I'm pretty sure I helped Rachel create an application like this as well
//make sure to merge your code so you don't have 2 of these

public class ArtApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
