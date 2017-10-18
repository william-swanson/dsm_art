package com.willard5991.dsmarthub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class MainActivity extends AppCompatActivity {

    private void login(final String email, final String password, final String username){

        if(SyncUser.currentUser() != null){
            SyncUser.currentUser().logout();

            Realm realm = Realm.getDefaultInstance();
            if(realm != null){
                realm.close();
                Realm.deleteRealm(realm.getConfiguration());
            }
        }

        SyncCredentials myCredentials = SyncCredentials.usernamePassword(email,password,false);
        SyncUser.loginAsync(myCredentials, "http://52.205.194.154:9080",new SyncUser.Callback(){
            @Override
            public void onSuccess(SyncUser user){
                SyncConfiguration configuration = new SyncConfiguration.Builder(user,
                        "http://52.205.194.154:9080/~/dsmarthub").disableSSLVerification().waitForInitialRemoteData().schemaVersion((long)12.0).build();
                Realm.setDefaultConfiguration(configuration);

                Realm.getInstanceAsync(configuration, new Realm.Callback(){
                    @Override
                    public void onSuccess(Realm realm){

//                        realm.executeTransaction(new Realm.Transaction(){
//                            @Override
//                            public void execute(Realm realm){
//                                realm.copyToRealmOrUpdate(user);
//                            }
//                        });
//                        realm.close();
                    }
                });
            }

            @Override
            public void onError(ObjectServerError error){
//                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(error.toString());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        login("cs188","MyBeautifulBulldogApp","email");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("Discover"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddActivity.class); //TODO: will need to update the name of the class
                startActivity(intent);
            }
        });
    }

}
