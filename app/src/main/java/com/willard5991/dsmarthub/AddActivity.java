package com.willard5991.dsmarthub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import io.realm.Realm;

public class AddActivity extends AppCompatActivity implements LocationListener {
    private ImageButton imageButton;
    private EditText nameView;
    private EditText artistView;
    private EditText yearView;
    private EditText mediumView;
    private TextView latitudeView;
    private TextView longitudeView;
    private Button saveButton;
    private Realm realm;
    private double longitude;
    private double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        imageButton = (ImageButton) findViewById(R.id.add_image);
        nameView = (EditText) findViewById(R.id.add_name);
        artistView = (EditText) findViewById(R.id.add_artist);
        yearView = (EditText) findViewById(R.id.add_year);
        mediumView = (EditText) findViewById(R.id.add_medium);
        saveButton = (Button) findViewById(R.id.add_button);
        latitudeView = (TextView) findViewById(R.id.latitudeView);
        longitudeView = (TextView) findViewById(R.id.longitudeView);
        realm = Realm.getDefaultInstance();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) !=null) {
                    startActivityForResult(takePictureIntent, 1);
                }
            }


        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nameView.getText().toString().matches("")
                    &&!artistView.getText().toString().matches("")
                    &&!yearView.getText().toString().matches("")
                    &&!mediumView.getText().toString().matches("")
                    &&imageButton.getDrawable()!=null){
                    realm.executeTransaction(new Realm.Transaction(){
                        @Override
                        public void execute(Realm realm) {
                            exhibit exhibit = new exhibit();
                            exhibit.setName(nameView.getText().toString());
                            exhibit.setArtist(artistView.getText().toString());
                            exhibit.setYear(yearView.getText().toString());
                            exhibit.setMedium(mediumView.getText().toString());
                            exhibit.setLongitude(longitude);
                            exhibit.setLatitude(latitude);

                            exhibit.setId(realm.where(exhibit.class).findAllSorted("id").last().getId()+1);

                            BitmapDrawable image = (BitmapDrawable) imageButton.getDrawable();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageInByte = baos.toByteArray();
                            exhibit.setImage(imageInByte);

                            realm.copyToRealm(exhibit);
                            finish();
                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        latitudeView.setText(Double.toString(latitude));
        longitudeView.setText(Double.toString(longitude));

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
}
