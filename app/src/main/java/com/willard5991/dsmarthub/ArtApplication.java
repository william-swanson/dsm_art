package com.willard5991.dsmarthub;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by clayton on 10/29/17.
 */

public class ArtApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
