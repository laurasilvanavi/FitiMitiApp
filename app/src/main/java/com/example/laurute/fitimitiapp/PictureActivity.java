package com.example.laurute.fitimitiapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
    }

    public void takeaPicture(View view) {
        String state;
        state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)) { //kitoks tikrinimas ar suteiktos teises
            dispatchTakePictureIntent();
        }
         else{
              Toast.makeText(getApplicationContext(), "Jeigu norite įvykdyti funkciją, turi leisti prieiti prie jūsų duomenų.", Toast.LENGTH_LONG).show();
         }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "Atsiprašome, bet įvyko klaida kuriant failą. Suteikite teises programėlei pasiekti ir naudoti Jūsų duomenis.", Toast.LENGTH_LONG).show();

            }
            // sukurtas buvo failas, o dbr fotkinama
            if (photoFile != null) {
                Uri photoURI=Uri.fromFile(new File(mCurrentPhotoPath));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
   }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "FitiMitiApp_" + timeStamp + "_"; //Jpeg_
        File storageDir = Environment.getExternalStoragePublicDirectory("FitiMitiApp");
        if(!storageDir.exists()){ //jei nera tokio aplanko, tai sukuriam
            storageDir.mkdir();
         }

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
