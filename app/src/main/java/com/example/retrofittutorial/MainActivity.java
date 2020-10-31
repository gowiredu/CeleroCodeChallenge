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
    private Multimap<Integer, Integer> visitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textViewResult = findViewById(R.id.text_view_result);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        visitOrder = ArrayListMultimap.create();
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

                // get visit order and name and sort according to just the visit order.
                for(Post post : posts) {
                    //String content = "";
                    //content += "ID: " + post.getId() + "\n";
                    //content += "User ID: " + post.getUserId() + "\n";
                    //content += "Title: " + post.getTitle() + "\n";
                    //content += "Text: " + post.getName() + "\n";
                    visitOrder.put(post.getVisitOrder(), post.getIdentifier());
                    //customerInitData.put(post.getVisitOrder(), post.getName(), post.getProfilePicture().getThumbnail());
                    names.add(post.getName());
                    profileURLS.add(Uri.parse(post.getProfilePicture().getThumbnail()));
                    //customerInitData.put(post.getName(), Uri.parse(post.getProfilePicture().getThumbnail()));
                    //customerNames.put(post.getVisitOrder(), post.getName());
                    //content += "Text: " + post.getServiceReason() + "\n";

                    //textViewResult.append(content);
                }

                adapter = new CardViewAdapter(getApplicationContext(), names, profileURLS);
                recyclerView.setAdapter(adapter);
                //System.out.println("URLS: " + profileURLS);

                System.out.println("Visit 1: " + visitOrder.get(1));
                System.out.println("Visit 2: " + visitOrder.get(2));
                System.out.println("Visit 3: " + visitOrder.get(3));
                System.out.println("Visit 4: " + visitOrder.get(4));
                System.out.println("Visit 5: " + visitOrder.get(5));
                System.out.println("All Visits: " + visitOrder.values());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}