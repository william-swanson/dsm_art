package com.willard5991.dsmarthub;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private EditText nameView;
    private EditText artistView;
    private EditText yearView;
    private EditText mediumView;
    private Button saveButton;
    private Realm realm;

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
        realm = Realm.getDefaultInstance();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) !=null) {
                    startActivityForResult(takePictureIntent, 1);
                }
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

                        }
                    });
                }
            }
        });

    }
}
