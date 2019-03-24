package com.example.jsontime.retrofit;

import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    //set our bas url
    private static final String BASE_URL = "http://shibe.online/api/";
    //This is static so we can make this a Singleton object:
    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        //if this null we wil create an inace because means there
        //no other instance of retrofit in memory
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    //Singleton
        return retrofit;

    }
}
