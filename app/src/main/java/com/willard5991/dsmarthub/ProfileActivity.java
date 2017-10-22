package com.willard5991.dsmarthub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;

public class ProfileActivity extends AppCompatActivity {
    private TextView art_name;
    private TextView art_artist;
    private TextView art_year;
    private TextView art_medium;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        art_name = (TextView) findViewById(R.id.exhibit_name);
        realm = Realm.getDefaultInstance();
        String name = (String) getIntent().getStringExtra("exhibit");
        exhibit exhibit = realm.where(exhibit.class).equalTo("name",name).findFirst();
        art_name.setText(exhibit.getName());

        art_artist = (TextView) findViewById(R.id.exhibit_artist);
        realm = Realm.getDefaultInstance();
        String artist = (String) getIntent().getStringExtra("exhibit");
        exhibit exhibit1 = realm.where(exhibit.class).equalTo("artist",artist).findFirst();
        art_artist.setText(exhibit1.getArtist());

        art_year = (TextView) findViewById(R.id.exhibit_year);
        realm = Realm.getDefaultInstance();
        String year = (String) getIntent().getStringExtra("exhibit");
        exhibit exhibit2 = realm.where(exhibit.class).equalTo("year",year).findFirst();
        art_year.setText(exhibit2.getYear());

        art_medium = (TextView) findViewById(R.id.exhibit_medium);
        realm = Realm.getDefaultInstance();
        String medium = (String) getIntent().getStringExtra("exhibit");
        exhibit exhibit3 = realm.where(exhibit.class).equalTo("medium",medium).findFirst();
        art_medium.setText(exhibit3.getMedium());

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        realm.close();
    }
}
