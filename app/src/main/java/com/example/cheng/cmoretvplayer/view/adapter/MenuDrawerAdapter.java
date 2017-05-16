package com.example.cheng.cmoretvplayer.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.datastructure.YoutubeInfo;

import java.util.ArrayList;

/**
 * Created by cheng on 2017/3/20.
 */

public class MenuDrawerAdapter extends RecyclerView.Adapter<MenuDrawerAdapter.ViewHolder> {
    private ArrayList<ArrayList<YoutubeInfo>> youtubeList;
    private MenuDrawerAdapter.OnItemClick itemOnClick;
    private Context context;

    public interface OnItemClick {
        void ItemOnClick();
    }

    public MenuDrawerAdapter(ArrayList<ArrayList<YoutubeInfo>> youtubeList) {
        this.youtubeList = youtubeList;
    }

    @Override
    public MenuDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_menu_drawer_list, parent, false);

        // Return a new holder instance
        MenuDrawerAdapter.ViewHolder viewHolder = new MenuDrawerAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MenuDrawerAdapter.ViewHolder holder, int position) {
        holder.textView.setText("cmoreBox");
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
        if(youtubeList.size()>0){
            return (youtubeList.size()/30)+1;
        }else {
            return 0;
        }

    }

    public void setItemClick(MenuDrawerAdapter.OnItemClick itemOnClick) {
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
