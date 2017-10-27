package com.willard5991.dsmarthub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.Menu;
import android.app.Activity;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.ByteArrayOutputStream;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ProfileActivity extends AppCompatActivity
{
    private TextView art_name;
    private TextView art_artist;
    private TextView art_year;
    private TextView art_desc;
    private TextView art_medium;
    private Realm realm;
    private ViewFlipper flip;
    private Button add_photo_button;
    private RealmList<Photo> list;
    private int art_clicks;
    private ImageView pic;

    private float initialX;
    private exhibit art;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pic = new ImageView(this);

        art_name= (TextView) findViewById(R.id.exhibit_name);
        art_artist= (TextView) findViewById(R.id.exhibit_artist);
        art_year= (TextView) findViewById(R.id.exhibit_year);
        art_desc= (TextView) findViewById(R.id.exhibit_desc);
        art_medium=(TextView) findViewById(R.id.exhibit_medium);
        flip = (ViewFlipper) findViewById(R.id.profile_pics);
        add_photo_button=(Button) findViewById(R.id.add_photo_button);

        realm = Realm.getDefaultInstance();

        String name = (String) getIntent().getStringExtra("exhibit");
        art = realm.where(exhibit.class).equalTo("name",name).findFirst();
        art_name.setText(art.getName());
        art_artist.setText(art.getArtist());
        art_year.setText(art.getYear());
        art_desc.setText(art.getDesc());
        art_medium.setText(art.getMedium());

        reload();

//        flip.setAutoStart(true);

        flip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float finalX = event.getX();
                        if (initialX > finalX) {
//                            if (flip.getDisplayedChild() == 1)
//                                break;

 /*TruitonFlipper.setInAnimation(this, R.anim.in_right);
 TruitonFlipper.setOutAnimation(this, R.anim.out_left);*/

                            flip.showNext();
                        } else {
//                            if (flip.getDisplayedCh ild() == 0)
//                                break;

 /*TruitonFlipper.setInAnimation(this, R.anim.in_left);
 TruitonFlipper.setOutAnimation(this, R.anim.out_right);*/

                            flip.showPrevious();
                        }
                        return true;
                }
                return false;
            }
        });

        realm.executeTransaction(new Realm.Transaction()
        {
            @Override
            public void execute(Realm realm) {
                art_clicks = art.getClicks() + 1;
            }
        });

        add_photo_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicture.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(takePicture, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        Bundle extras = data.getExtras();
        Bitmap imageBitmap=(Bitmap)extras.get("data");
        pic.setImageBitmap(imageBitmap);

        realm.executeTransaction(new Realm.Transaction()
        {
            @Override
            public void execute(Realm realm)
            {
                BitmapDrawable image = (BitmapDrawable)pic.getDrawable();
                ByteArrayOutputStream boas=new ByteArrayOutputStream();
                image.getBitmap().compress(Bitmap.CompressFormat.JPEG,100,boas);
                final byte[] imageInByte = boas.toByteArray();

                Photo p1 = new Photo();
                p1.setImage(imageInByte);

                art.appendPhoto(p1);
                reload();
            }
        });
    }

    private void setFlipperImage(byte[] res)
    {
        Bitmap bitmap = BitmapFactory.decodeByteArray(res, 0, res.length);
        ImageView image = new ImageView(getApplicationContext());
        image.setImageBitmap(bitmap);

        flip.addView(image);
    }

    private void reload() {
        list = art.getPhotos();
        flip.removeAllViews();

        for (Photo photo : list) {
            setFlipperImage(photo.getImage());
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        realm.close();
    }
}
