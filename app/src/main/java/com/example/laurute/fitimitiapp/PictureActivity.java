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
        dispatchTakePictureIntent();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                Toast.makeText(getApplicationContext(), "first", Toast.LENGTH_LONG).show();
//                photoFile = createImageFile();
//                Toast.makeText(getApplicationContext(), "good", Toast.LENGTH_LONG).show();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Toast.makeText(getApplicationContext(), "Negerai, kol kūrė failą", Toast.LENGTH_LONG).show();
//
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//                Toast.makeText(getApplicationContext(), "good2", Toast.LENGTH_LONG).show();
//            }
//        }
   }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
//    public void writeExternalStorage(View view){
//        //tikrinam
//        String state;
//        state = Environment.getExternalStorageState();
//
//        if(Environment.MEDIA_MOUNTED.equals(state)){ //kitoks tikrinimas ar suteiktos teises
//            //File root = new File(this.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "manoAplankas");// gaunam direktorija(nuotrauku aplankale), kuri bus prieinama tik mūsų appsui
//            File root = Environment.getExternalStoragePublicDirectory("manoAplankas"); // gaunam direktorija, kuri bus prieinama visiems
//            if(!root.exists()){ //ar dar neegzistuoja folderis
//                root.mkdir(); //kuriam
//                Toast.makeText(getApplicationContext(), "Nebuvo, tad sukuriam", Toast.LENGTH_LONG).show();
//            }
//            File file = new File(root, "myMessage.txt");
//            String message = ed1.getText().toString();
//            try {
//                FileOutputStream fileOutputStream= new FileOutputStream(file);
//                fileOutputStream.write(message.getBytes());
//                fileOutputStream.close();
//                ed1.setText("");
//                textView.setText("Jeigu norite, įveskite dar kartą:");
//                Toast.makeText(getApplicationContext(), "zinute issaugota, liux", Toast.LENGTH_LONG).show();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext(), "Nerado failiuko", Toast.LENGTH_LONG).show();
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext(), "IoException", Toast.LENGTH_LONG).show();
//            }
//
//        }
//        else{
//            Toast.makeText(getApplicationContext(), "Neleidzia naudori saugyklos", Toast.LENGTH_LONG).show();
//        }
//
//    }

}
