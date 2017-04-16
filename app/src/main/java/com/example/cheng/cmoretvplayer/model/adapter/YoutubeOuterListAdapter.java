package com.example.cheng.cmoretvplayer.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.YoutubeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cheng on 2017/3/16.
 */

public class YoutubeOuterListAdapter extends RecyclerView.Adapter<YoutubeOuterListAdapter.ViewHolder> {
    private ArrayList<ArrayList<YoutubeInfo>> youtubeList;
    private YoutubeInnerListAdapter youtubeInnerListAdapter;
    private int count;
    private int listsize=0;
    public YoutubeOuterListAdapter(ArrayList<ArrayList<YoutubeInfo>> youtubeList) {
        this.youtubeList = youtubeList;
        listsize=0;
    }

    @Override
    public YoutubeOuterListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_youtube_outer_list, parent, false);
        // Return a new holder instance
        YoutubeOuterListAdapter.ViewHolder viewHolder = new YoutubeOuterListAdapter.ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final YoutubeOuterListAdapter.ViewHolder holder, final int position) {
        if(youtubeList.get(position).size()>9){
            StaggeredGridLayoutManager linearManager = new StaggeredGridLayoutManager(9, StaggeredGridLayoutManager.VERTICAL);
            holder.youtubeInnerRecyclerView.setLayoutManager(linearManager);
        }else{
            StaggeredGridLayoutManager linearManager = new StaggeredGridLayoutManager(youtubeList.get(position).size(), StaggeredGridLayoutManager.VERTICAL);
            holder.youtubeInnerRecyclerView.setLayoutManager(linearManager);
        }
        youtubeInnerListAdapter = new YoutubeInnerListAdapter(youtubeList.get(position));
        holder.youtubeInnerRecyclerView.setAdapter(youtubeInnerListAdapter);
    }
    @Override
    public int getItemCount() {
    return youtubeList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public RecyclerView youtubeInnerRecyclerView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            youtubeInnerRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_youtube_inner_list);

        }
    }

    public void setArrayList(ArrayList arrayList) {
        youtubeList = arrayList;
    }
    public YoutubeInnerListAdapter getYoutubeInner(){
        return youtubeInnerListAdapter;
    }
}