package com.example.jsontime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jsontime.ListView.ShibeAdapter;
import com.example.jsontime.RecycleView.ShibeRecyclerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class ImageFragment extends Fragment {
    private static final String TAG = "ImageFragment";
    ListView listView;
    RecyclerView recyclerView;
    ShibeRecyclerAdapter recyclerAdapter;
    public ImageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        listView = view.findViewById(R.id.list_view);
        recyclerView = view.findViewById(R.id.rv_shibidawg);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerAdapter = new ShibeRecyclerAdapter(getContext(), new ArrayList<String>());

        // Setup recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)

    public void imageEvent(ImageEvent imageEventmage){
        if (imageEventmage.imageUrl != null){
            //String url = imageEventmage.imageUrl.substring(2, imageEventmage.imageUrl.length()-2);
            Toast.makeText(getContext(), imageEventmage.imageUrl, Toast.LENGTH_SHORT).show();

           // listView.setAdapter(new ShibeAdapter(getContext(), imageEventmage.imageUrls));
            recyclerAdapter.setData(imageEventmage.imageUrls);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(ImageFragment.this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().register(ImageFragment.this);
        super.onStop();
    }
}
