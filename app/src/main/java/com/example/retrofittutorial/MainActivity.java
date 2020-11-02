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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> names;
    private ArrayList<Uri> profileURLS;
    private CardViewAdapter adapter;
    private LinkedHashMap<Integer, Integer> idNamePairs;
    private SQLiteDBHelper sqLiteDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        names = new ArrayList<>();
        profileURLS = new ArrayList<>();
        idNamePairs = new LinkedHashMap<>();
        sqLiteDBHelper = new SQLiteDBHelper(this);

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

                List<Post> posts = response.body();

                Collections.sort(posts, new Comparator<Post>() {
                    @Override
                    public int compare(Post g1, Post g2) {
                        return g1.getVisitOrder().compareTo(g2.getVisitOrder());
                    }
                });

                // get visit order and name and sort according to just the visit order.
                int count = 0;
                for(Post post : posts) {
                    names.add(post.getName());
                    profileURLS.add(Uri.parse(post.getProfilePicture().getThumbnail()));
                    idNamePairs.put(count, post.getIdentifier());
                    count++;
                }

                adapter = new CardViewAdapter(getApplicationContext(), names, profileURLS);
                recyclerView.setAdapter(adapter);

                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(
                                getApplicationContext(),
                                recyclerView,
                                new RecyclerItemClickListener.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        String customerNameToPass = ((TextView) view.findViewById(R.id.customerName)).getText().toString();
                                        startDetailsActivity(idNamePairs.get(position), customerNameToPass);

                                    }
                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                    }
                                }
                        )
                );
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.i("Failure", t.getMessage());
            }
        });
    }


    private void startDetailsActivity(Integer customerId, String customerName)
    {
        Intent detailsActivityIntent = new Intent(MainActivity.this, ReportDetailsActivity.class);
        detailsActivityIntent.putExtra("customerID", customerId);
        detailsActivityIntent.putExtra("customerName", customerName);
        startActivity(detailsActivityIntent);
    }
}