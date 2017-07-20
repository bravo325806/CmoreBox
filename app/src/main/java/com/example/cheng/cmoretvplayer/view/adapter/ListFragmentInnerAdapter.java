package com.example.cheng.cmoretvplayer.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.datastructure.YoutubeInfo;
import com.example.cheng.cmoretvplayer.view.activity.YoutubePlayerActivity;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2017/5/22.
 */

public class ListFragmentInnerAdapter extends RecyclerView.Adapter<ListFragmentInnerAdapter.ViewHolder> {
    private ArrayList<YoutubeInfo> youtubeList;
    private Context context;
    private YoutubePlayerActivity.ItemClick itemClick;

    public ListFragmentInnerAdapter(List youtubeList,YoutubePlayerActivity.ItemClick itemClick) {
        this.youtubeList = new ArrayList<>(youtubeList);
        this.itemClick=itemClick;
    }

    @Override
    public ListFragmentInnerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_list_fragment_inner_list, parent, false);

        // Return a new holder instance
        ListFragmentInnerAdapter.ViewHolder viewHolder = new ListFragmentInnerAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListFragmentInnerAdapter.ViewHolder holder, int position) {
        Ion.with(holder.imageView).load(youtubeList.get(position).getVideoThumbnails());
        holder.textView.setText(youtubeList.get(position).getVideoTitle());
        holder.imageView.setTag(position);
        holder.layout.setOnClickListener(imageClick);
        holder.layout.setTag(youtubeList.get(position));
    }

    @Override
    public int getItemCount() {
        return youtubeList.size();
    }

    View.OnClickListener imageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            itemClick.itemClicK(v);
        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView imageView;
        public TextView textView;
        public LinearLayout layout;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_list_fragment_inner_image);
            textView = (TextView) itemView.findViewById(R.id.item_list_fragment_inner_text);
            layout = (LinearLayout) itemView.findViewById(R.id.item_list_fragment_inner_layout);
        }
    }
}
