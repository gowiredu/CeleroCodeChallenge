package com.example.retrofittutorial;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private int passedCustomerId;
    private String passedCustomerName;
    private ArrayList<String> customerDetails;
    private CardViewAdapterDetailsActivity adapter;
    private ArrayList<String> problemPictureUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        problemPictureUrls = new ArrayList<>();

        ViewPager viewPager = findViewById(R.id.view_pager);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(this, problemPictureUrls);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerDetails = new ArrayList<>();

        Intent intent = getIntent();
        passedCustomerId = intent.getIntExtra("customerID", 0);
        passedCustomerName = intent.getStringExtra("customerName");
        setActionBarTitle();

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

                for(Post post : posts) {
                    if (post.getIdentifier().equals(passedCustomerId))
                    {
                        customerDetails.add(post.getPhoneNumber());
                        customerDetails.add(post.getServiceReason());
                        customerDetails.add((post.getLocation().getAddress().getStreet()
                                + ", " + post.getLocation().getAddress().getCity()
                                + ", " + post.getLocation().getAddress().getState()
                                + " " + post.getLocation().getAddress().getPostalCode()));

                        problemPictureUrls.addAll(post.getProblemPictures());

                    }
                }

                Log.i("Details: ", Arrays.toString(customerDetails.toArray()));


                adapter = new CardViewAdapterDetailsActivity(getApplicationContext(), customerDetails);
                recyclerView.setAdapter(adapter);
                viewPager.setAdapter(imageSliderAdapter);

                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(
                                getApplicationContext(),
                                recyclerView,
                                new RecyclerItemClickListener.ClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        if (position == 0)
                                        {
                                            callCustomerPhoneIntent();
                                        }
                                        else if (position == 2)
                                        {
                                            openMapsIntent();
                                        }
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

    private void setActionBarTitle()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(passedCustomerName);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void callCustomerPhoneIntent()
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customerDetails.get(0), null));
        startActivity(intent);
    }

    private void openMapsIntent()
    {
        String uri = "http://maps.google.com/maps?q=" + customerDetails.get(2);
        Intent openMapsIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(Intent.createChooser(openMapsIntent, "Select an application"));
    }
}