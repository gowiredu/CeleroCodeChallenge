package com.example.retrofittutorial;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReportDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private int passedCustomerId;
    private ArrayList<String> customerDetails;
    private CardViewAdapterDetailsActivity adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerDetails = new ArrayList<>();

        Intent intent = getIntent();
        passedCustomerId = intent.getIntExtra("customerID", 0);

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
                    if (post.getIdentifier().equals(passedCustomerId))
                    {
                        //Toast.makeText(getApplicationContext(), post.getName(), Toast.LENGTH_SHORT).show();
                        customerDetails.add(post.getPhoneNumber());
                        customerDetails.add(post.getServiceReason());
                        customerDetails.add(String.valueOf(post.getLocation()));

                    }
                }

                Log.i("Details: ", Arrays.toString(customerDetails.toArray()));

                // phone number, service reason, problem pictures, location
                // store all in an arrayList, pass to CardViewAdapter's second constructor
                //

                adapter = new CardViewAdapterDetailsActivity(getApplicationContext(), customerDetails);
                recyclerView.setAdapter(adapter);

                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(
                                getApplicationContext(),
                                recyclerView,
                                new RecyclerItemClickListener.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        //Toast.makeText(getApplicationContext(), String.valueOf(idNamePairs.get(position)), Toast.LENGTH_SHORT).show();
                                        //startDetailsActivity(idNamePairs.get(position));
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
}