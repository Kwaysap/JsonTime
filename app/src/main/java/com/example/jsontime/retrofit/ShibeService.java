package com.example.jsontime.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShibeService {
    //The path or query we are looking to communicate with
    @GET("shibes")
   //this is a method just does not have public in front of it
    Call<List<String>> loadShibe(@Query("count") int count);

}
