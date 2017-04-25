package com.example.cheng.cmoretvplayer.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.YoutubeInfo;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cheng on 2017/3/20.
 */

public class YoutubeDrawerAdapter extends RecyclerView.Adapter<YoutubeDrawerAdapter.ViewHolder> {
    private ArrayList<ArrayList<YoutubeInfo>> youtubeList;
    private YoutubeDrawerAdapter.OnItemClick itemOnClick;
    private Context context;

    public interface OnItemClick {
        void ItemOnClick();
    }

    public YoutubeDrawerAdapter(ArrayList<ArrayList<YoutubeInfo>> youtubeList) {
        this.youtubeList = youtubeList;
    }

    @Override
    public YoutubeDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_youtube_drawer_list, parent, false);

        // Return a new holder instance
        YoutubeDrawerAdapter.ViewHolder viewHolder = new YoutubeDrawerAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(YoutubeDrawerAdapter.ViewHolder holder, int position) {
        holder.textView.setText(youtubeList.get(position).get(0).getT2name());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClick.ItemOnClick();
            }
        });
        if(position==0){
            holder.textView.requestFocus();
        }
    }

    @Override
    public int getItemCount() {
        return youtubeList.size();
    }

    public void setItemClick(YoutubeDrawerAdapter.OnItemClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }
    public void setArrayList(ArrayList<ArrayList<YoutubeInfo>> youtubeList){
        this.youtubeList=youtubeList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView textView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_drawer_text);
        }
    }
}
