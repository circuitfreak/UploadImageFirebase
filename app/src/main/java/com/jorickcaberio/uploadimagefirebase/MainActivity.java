package com.jorickcaberio.uploadimagefirebase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final  String TAG = "MainActivity";
    private Firebase ref;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.my_image);

        ref = new Firebase("https://insertyourfirebaseapp.firebaseio.com/").child("image");

        Bitmap bmp =  BitmapFactory.decodeResource(getResources(),
                R.drawable.kiner_play_store); // image to be stored in firebase
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        bmp.recycle();
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);

        ref.setValue(imageFile);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String imageFile = (String) snapshot.getValue();
                Log.d(TAG, imageFile);
                byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                imageView.setImageBitmap(bmp);

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e(TAG,"The read failed: " + firebaseError.getMessage());
            }
        });

    }
}
