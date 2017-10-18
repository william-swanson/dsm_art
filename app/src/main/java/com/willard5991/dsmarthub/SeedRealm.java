package com.willard5991.dsmarthub;

/**
 * Created by willard5991 on 10/9/2017.
 */

import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import io.realm.ObjectServerError;


public class SeedRealm {

    public static void main(String[] args){

    }

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

                        realm.executeTransaction(new Realm.Transaction(){
                            @Override
                            public void execute(Realm realm){
                                //realm.copyToRealmOrUpdate(user);
                            }
                        });

                        realm.close();
                    }
                });
            }

            @Override
            public void onError(ObjectServerError error){
                //Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                System.out.println(error.toString());
            }
        });
    }

}
