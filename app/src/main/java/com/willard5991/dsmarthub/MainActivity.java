package com.willard5991.dsmarthub;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import io.realm.exceptions.RealmMigrationNeededException;

public class MainActivity extends AppCompatActivity implements LocationListener{

    public Realm realm;
    private double latitude;
    private double longitude;

//    private void login(final String email, final String password, final String username){

//        if(SyncUser.currentUser() != null){
//            SyncUser.currentUser().logout();
//
//            Realm realm = Realm.getDefaultInstance();
//            if(realm != null){
//                realm.close();
//                Realm.deleteRealm(realm.getConfiguration());
//            }
//        }

//        SyncCredentials myCredentials = SyncCredentials.usernamePassword(email,password,false);
//        SyncUser.loginAsync(myCredentials, "http://52.205.194.154:9080",new SyncUser.Callback(){
//            @Override
//            public void onSuccess(SyncUser user){
//                SyncConfiguration configuration = new SyncConfiguration.Builder(user,
//                        "http://52.205.194.154:9080/~/dsmarthub").disableSSLVerification().waitForInitialRemoteData().schemaVersion((long)12.0).build();
//                Realm.setDefaultConfiguration(configuration);

//                Realm.getInstanceAsync(configuration, new Realm.Callback(){
//                    @Override
//                    public void onSuccess(Realm realm){

//                        realm.executeTransaction(new Realm.Transaction(){
//                            @Override
//                            public void execute(Realm realm){
//                                realm.copyToRealmOrUpdate(user);
//                            }
//                        });
//                        realm.close();
//                    }
//                });
//            }

//            @Override
//            public void onError(ObjectServerError error){
//                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//                System.out.println(error.toString());
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        login("cs188","MyBeautifulBulldogApp","email");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Discover"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //it looks like you have the tabs but no viewpager,
        // this could have been in a different branch but I implemented this
        // so I could test the fragment
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final ArtPagerAdapter adapter = new ArtPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddActivity.class); //TODO: will need to update the name of the class
                startActivity(intent);
            }
        });
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
      
    }

    private void login(final String email, final String password, final String username) {
        if(SyncUser.currentUser() != null) {

            SyncUser.currentUser().logout();
            Realm realm = Realm.getDefaultInstance();
            if(realm != null) {
                realm.close();
                Realm.deleteRealm(realm.getConfiguration());
            }
        }
        Log.v("login", "logged");

        SyncCredentials myCredentials = SyncCredentials.usernamePassword(email, password, false);
        SyncUser.loginAsync(myCredentials, "http://52.205.194.154:9080", new SyncUser.Callback() {
            @Override
            public void onSuccess(SyncUser user) {
                SyncConfiguration configuration = new SyncConfiguration.Builder(user, "realm://52.205.194.154:9080/~/dsm_art_v3").disableSSLVerification().waitForInitialRemoteData().schemaVersion((long) 16.0).build();
                configuration.shouldDeleteRealmIfMigrationNeeded();
                Realm.setDefaultConfiguration(configuration);

                Realm.getInstanceAsync(configuration, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {

                        //successfully created a user

                        final exhibit exh = new exhibit();

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

//                                exh.setArtist("test");
//                                exh.setDesc("test");
//                                exh.setMedium("test");
//                                exh.setYear("test");
//                                exh.setName("test");
//
//                                realm.copyToRealmOrUpdate(exh);
                            }
                        });

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                final exhibit art = realm.where(exhibit.class).equalTo("name","test").findFirst();

                                Photo p1 = new Photo();
                                p1.setName("test2");
                                BitmapDrawable image = (BitmapDrawable) getResources().getDrawable(R.drawable.b);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] imageInByte = baos.toByteArray();
                                p1.setImage(imageInByte);

//                                Photo p2 = new Photo();
//                                p2.setName("testA");
//                                p2.setImage(imageInByte);

//                                realm.copyToRealm(p1);
//                                realm.copyToRealm(p2);

                                art.appendPhoto(p1);
//                                art.appendPhoto(p2);
                            }
                        });

                        realm.close();

                        Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                        intent.putExtra("exhibit", "test");
                        startActivity(intent);
                    }
                });

                Log.v("login", "logged");
            }

            @Override
            public void onError(ObjectServerError error) {

                Log.v("error here", error.toString());
            }
        });
    }

}
