package com.willard5991.dsmarthub;

/**
 * Created by willard5991 on 10/9/2017.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import io.realm.ObjectServerError;

public class SeedRealm {

    public static void main(String[] args)throws FileNotFoundException,IOException{

        ArrayList<exhibit> exhibits = new ArrayList<exhibit>();
        ArrayList<String[]> rawExhibits = new ArrayList<String[]>();
        String csvFilename = "C:\\Users\\willard5991\\Documents\\Drake\\year2017_2018\\software_engineering\\data\\data.csv";
        int numCategories = 7;
        CSVReader reader = new CSVReader(csvFilename,numCategories);
        reader.read();

        rawExhibits = reader.getValues();

        login("cs188","MyBeautifulBulldogApp","hi");
        Realm realm = Realm.getDefaultInstance();

        for(String[] s : rawExhibits){
            //'Art Name','Artist','Year','Medium','Location','Image','Description'
            System.out.println(s);
            final exhibit e = new exhibit();
            e.setName(s[0]);
            e.setArtist(s[1]);
            e.setYear(s[2]);
            e.setMedium(s[3]);
            String[] coords = s[4].split("; ");
            e.setLoc(Double.parseDouble(coords[0]),Double.parseDouble(coords[1]));
            File imFile = new File(s[5]);
            byte[] image = Files.readAllBytes(imFile.toPath());
            Photo p = new Photo();
            p.setName(s[0]);
            p.setImage(image);
            e.addPhoto(p);
            Scanner scan = new Scanner(new File(s[6]));
            String description = "";
            while(scan.hasNext()){
                description += scan.nextLine();
            }
            e.setDesc(description);

            exhibits.add(e);
//            realm.executeTransaction(new Realm.Transaction(){
//                @Override
//                public void execute(Realm realm){
//                    realm.copyToRealmOrUpdate(e);
//                }
//            });
        }

        realm.close();


    }

    private static void login(final String email, final String password, final String username){

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
                        "realm://52.205.194.154:9080/~/dsmarthub").disableSSLVerification().waitForInitialRemoteData().schemaVersion((long)12.0).build();
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
