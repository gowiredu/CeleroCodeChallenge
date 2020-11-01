package com.example.retrofittutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private RecyclerView recyclerView;
    private CardView cardView;
    private ArrayList<String> names;
    private ArrayList<Uri> profileURLS;
    private CardViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        names = new ArrayList<>();
        profileURLS = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hulet.tech/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful())
                {
                    Log.i("Error", "Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body(); // data we're getting from web service

                Collections.sort(posts, new Comparator<Post>() {
                    @Override
                    public int compare(Post g1, Post g2) {
                        return g1.getVisitOrder().compareTo(g2.getVisitOrder());
                    }
                });

                // get visit order and name and sort according to just the visit order.
                for(Post post : posts) {
                    names.add(post.getName());
                    profileURLS.add(Uri.parse(post.getProfilePicture().getThumbnail()));
                }

                adapter = new CardViewAdapter(getApplicationContext(), names, profileURLS);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}