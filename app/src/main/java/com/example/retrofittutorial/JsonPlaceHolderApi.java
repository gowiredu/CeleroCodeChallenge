package com.example.retrofittutorial;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface JsonPlaceHolderApi {

    @GET("celerocustomers.json") // relative URL
    Call<List<Post>> getPosts(); // getting back a list of "posts"
}
