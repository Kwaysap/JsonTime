package com.example.jsontime.RecycleView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jsontime.R;

import java.util.List;

public class ShibeRecyclerAdapter extends RecyclerView.Adapter<ShibeRecyclerAdapter.ShibeViewHolder> {

    //Declare variables
    private Context context;
    private List<String> urls;
    //created constructor
    public ShibeRecyclerAdapter(Context context, List<String> urls) {
    //initialize
        this.context = context;
        this.urls = urls;
    }

    @NonNull
    @Override
    public ShibeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
       View itemView = LayoutInflater.from(context).inflate(R.layout.shibe_content_view, viewGroup, false);
        return new ShibeViewHolder(itemView);
    }
    //(where it says position(name of the int) was initially (i)index and we changed it to position)
    @Override
    public void onBindViewHolder(@NonNull ShibeViewHolder holder, int position) {
        String url = urls.get(position);
        holder.tvUrl.setText(url);
        Glide.with(context).load(url).into(holder.ivShibe);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public void setData(List<String>urls){
        this.urls = urls;
        notifyDataSetChanged();
    }

    public class ShibeViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivShibe;
        private AppCompatTextView tvUrl;



    ShibeViewHolder(@NonNull View itemView) {
        super(itemView);
        ivShibe = itemView.findViewById(R.id.iv_shibe);
        tvUrl = itemView.findViewById(R.id.tv_url);
    }
}
}
