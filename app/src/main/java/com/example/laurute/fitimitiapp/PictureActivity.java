package com.example.laurute.fitimitiapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    Uri photoURI;
    ImageView imageView;
    TextView textView;
    int peopleCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        imageView =(ImageView) findViewById(R.id.imageViewPhoto);
        textView=(TextView) findViewById(R.id.textViewRepeatPhotoTask);
        Intent intent = getIntent();
        String message = intent.getStringExtra(GameActivity.EXTRA_MESSAGE);
        try
        {
            int number = Integer.parseInt(message);
            peopleCount = number;
        }
        catch (NumberFormatException e)
        {
            peopleCount = 1;
        }
        try{
        dispatchTakePictureIntent();}
        catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), "Jeigu norite tęsti žaidimą, turite leisti naudoti Jūsų kamerą.", Toast.LENGTH_LONG).show();
        }
    }

    public void takeaPicture(View view) {
        String state;
        state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)) { //kitoks tikrinimas ar suteiktos teises
            dispatchTakePictureIntent();
        }
         else{

              Toast.makeText(getApplicationContext(), "Jeigu norite tęsti žaidimą, leiskite programėlei prieiti prie Jūsų duomenų.", Toast.LENGTH_LONG).show();
         }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "Jeigu norite tęsti žaidimą, leiskite programėlei prieiti prie Jūsų duomenų.", Toast.LENGTH_LONG).show();

            }
            // sukurtas buvo failas, o dbr fotkinama
            if (photoFile != null) {
                photoURI=Uri.fromFile(new File(mCurrentPhotoPath));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
   }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                int facesCount = detectFaces();
                if (facesCount>=peopleCount){
                    Intent intent = new Intent(PictureActivity.this, GameActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    textView.setText("Net pirmokas suskaičiuotų, jog per mažai žmonių nuotraukoje!");
                }
            }
            else{
                textView.setText("Kur bėgate? Dar nenusifotografavote!");
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

    private int detectFaces(){
        Bitmap picture = rotatePicture();
        FaceDetector detector = new FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .build();

        Frame frame = new Frame.Builder().setBitmap(picture).build();
        SparseArray<Face> faces = detector.detect(frame); //dedami rasti veidai

        int count = faces.size();
        Toast.makeText(getApplicationContext(), "Aptikta veidų: "+count, Toast.LENGTH_LONG).show();
        detector.release();
        imageView.setImageBitmap(picture);
        return count;
    }

    private Bitmap rotatePicture(){
        BitmapFactory.Options bounds = new BitmapFactory.Options(); //nustatomas ilgis ir plotis
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bounds);


        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath, opts);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(mCurrentPhotoPath);        //info apie foto padeti
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Sugadinta nuotrauka", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;

        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        return rotatedBitmap;
    }
    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
    }
}
