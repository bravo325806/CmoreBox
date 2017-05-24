package com.example.cheng.cmoretvplayer.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.datastructure.YoutubeInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cheng on 2017/5/22.
 */

public class ListFragmentOuterAdapter extends RecyclerView.Adapter<ListFragmentOuterAdapter.ViewHolder> {
    private ArrayList<YoutubeInfo> youtubeList;
    private ListFragmentInnerAdapter listFragmentInnerAdapter;

    public ListFragmentOuterAdapter(ArrayList<YoutubeInfo> youtubeList) {
        this.youtubeList = youtubeList;
    }

    @Override
    public ListFragmentOuterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_list_fragment_outer_list, parent, false);
        // Return a new holder instance
        ListFragmentOuterAdapter.ViewHolder viewHolder = new ListFragmentOuterAdapter.ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListFragmentOuterAdapter.ViewHolder holder, final int position) {
        if (youtubeList.size() > 10) {
            StaggeredGridLayoutManager linearManager = new StaggeredGridLayoutManager(10, StaggeredGridLayoutManager.VERTICAL);
            holder.youtubeInnerRecyclerView.setLayoutManager(linearManager);
        } else {
            StaggeredGridLayoutManager linearManager = new StaggeredGridLayoutManager(youtubeList.size(), StaggeredGridLayoutManager.VERTICAL);
            holder.youtubeInnerRecyclerView.setLayoutManager(linearManager);
        }
        if((position*30)+30>youtubeList.size()){
            listFragmentInnerAdapter = new ListFragmentInnerAdapter(youtubeList.subList(position*30,youtubeList.size()));
        }else{
            listFragmentInnerAdapter = new ListFragmentInnerAdapter(youtubeList.subList(position*30,(position*30)+30));
        }

        holder.youtubeInnerRecyclerView.setAdapter(listFragmentInnerAdapter);
    }

    @Override
    public int getItemCount() {
        if (youtubeList.size() > 0) {
            return (youtubeList.size() / 30) + 1;
        } else {
            return 0;
        }
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

            youtubeInnerRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_list_fragment_inner_list);

        }
    }
    public ListFragmentInnerAdapter getYoutubeInner(){
        return listFragmentInnerAdapter;
    }
}