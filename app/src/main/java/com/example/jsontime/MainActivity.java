package com.example.jsontime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jsontime.retrofit.RetrofitClientInstance;
import com.example.jsontime.retrofit.ShibeService;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    ImageFragment imageFragment;
    Button button;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageFragment = new ImageFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame1, imageFragment).commit();

        button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new ImageDownloaderAsyncTask(MainActivity.this).execute();
                Toast.makeText(MainActivity.this, "buttonnnn", Toast.LENGTH_SHORT).show();
                //volleyRequest();
                retrofitRequest();
            }
        });
    }

    private void retrofitRequest() {
        //create a instance of our service using the Retrofit singleton instance
        ShibeService shibeService = RetrofitClientInstance
                .getRetrofit()
                .create(ShibeService.class);

        //**we are using retrofit's default class called (Call) to wrap around our expected return data**
        //create a new instance of our return type from the service
        //calling the method and passing in whatever parameters we need
        Call<List<String>> call = shibeService.loadShibe(50);
        //then we use the new instance of our return type and enqueue it
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {
                if (response.isSuccessful()){
                  Log.d(TAG, "onResponse: " + response.body());
                    EventBus.getDefault().post(new ImageEvent(response.body().get(0), response.body()));
                }else if (response.errorBody() != null) {
                       Log.d(TAG, "onResponse: " + response.errorBody().toString());
                   }else{
                    Log.d(TAG, "onResponse: shits all fucked up");

                }


            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }


    private void volleyRequest () {

        String baseURL = "http://shibe.online/api/shibes?";
        String query = "count1";
        //create url
        final String url = baseURL + query;
        //create a RequestQueue object instance, and init with the volley class's static method
        //called newRequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        //create a JsonArray  or JsonObject Request based on the response you will be receiving
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse" + response. toString().replace("\\", ""));
                        /*try {
                            //EventBus.getDefault().post(new ImageEvent(response.get(0).toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.toString());
                    }
                });
        //pass in the request to the requestQueue object
        requestQueue.add(jsonArrayRequest);


    }

    private static class ImageDownloaderAsyncTask extends AsyncTask<Void, Void, Void> {

            WeakReference<MainActivity> activityWeakReference;
            HttpURLConnection httpURLConnection;

        public ImageDownloaderAsyncTask(MainActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Declare variables and Initialize
            String baseURL = "http://shibe.online/api/shibes?";
            String query = "count1";
            //allows to add strings
            StringBuilder result = new StringBuilder();

            try {
                //create a URL object, passing the url string in the constructor
                URL url = new URL(baseURL + query);
                //We use the url object to create a new openConnection(), then cast it as a HttpURLConnection
                httpURLConnection = (HttpURLConnection) url.openConnection();
                //Create an inputStream instance and initialize it with a BufferedInputStream
                //passing the Stream from the httpURLConnection object
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                //we create a BufferReader object instance and intialize it with our inputStreamReader
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


                String line;
                //using the bufferReader we read each line of the response and append it into our
                //StringBuilder object instance
                while ((line = reader.readLine()) !=null) {
                    result.append(line);

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                //Important to disconnect our httpURLConnection when we are done
                httpURLConnection.disconnect();
            }
           // EventBus.getDefault().post(new ImageEvent(result.toString()));
            return null;
        }
    }
}
