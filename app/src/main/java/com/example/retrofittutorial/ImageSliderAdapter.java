package com.example.retrofittutorial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.viewpager.widget.PagerAdapter;

public class ImageSliderAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> imageUrls;

    ImageSliderAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }
    @Override
    public int getCount() {
        return imageUrls.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        /*
        Picasso.get()
                .load(imageUrls.get(position))
                .fit()
                .centerCrop()
                .error(R.drawable.ic_no_image)
                .into(imageView);

         */

        Picasso.get()
                .load(imageUrls.get(position))
                .fit()
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(imageUrls.get(position))
                                .fit()
                                .centerCrop()
                                .error(R.drawable.ic_no_image)
                                .into(imageView, new Callback() {
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

        container.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent fullScreenImageActivityIntent = new Intent(context, FullScreenImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("ImageUrls", imageUrls);
                    bundle.putInt("currentPosition", position);
                    fullScreenImageActivityIntent.putExtras(bundle);

                    context.startActivity(fullScreenImageActivityIntent);
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
