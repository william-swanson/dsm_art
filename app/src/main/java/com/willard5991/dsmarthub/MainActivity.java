package com.willard5991.dsmarthub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("Discover"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        this.login("cs188", "MyBeautifulBulldogApp", "hi");
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
                SyncConfiguration configuration = new SyncConfiguration.Builder(user, "realm://52.205.194.154:9080/~/dsm_art_v1").disableSSLVerification().waitForInitialRemoteData().schemaVersion((long) 14.0).build();
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
