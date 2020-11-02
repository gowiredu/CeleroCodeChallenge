package com.example.retrofittutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullScreenImageActivity extends AppCompatActivity {

    private String passedImageUrl;
    private ImageView fullImageView;
    private ArrayList<String> imageUrls;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        fullImageView = findViewById(R.id.fullImageView);


        //Intent intent = getIntent();
        //passedImageUrl = intent.getStringExtra("imageUrl");

        imageUrls = this.getIntent().getExtras().getStringArrayList("ImageUrls");
        currentPosition = this.getIntent().getExtras().getInt("currentPosition");


        ViewPager viewPager = findViewById(R.id.view_pager);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, imageUrls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition);

        /*
        Picasso.get()
                .load(imageUrls.get(currentPosition))
                .fit()
                .centerCrop()
                .into(fullImageView);
         */

        Picasso.get()
                .load(imageUrls.get(currentPosition))
                .fit()
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(fullImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(imageUrls.get(currentPosition))
                                .fit()
                                .centerCrop()
                                .error(R.drawable.ic_no_image)
                                .into(fullImageView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.v("Picasso","Could not fetch image");
                                    }
                                });
                    }
                });
    }
}