package com.example.cheng.cmoretvplayer.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.YoutubeInfo;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cheng on 2017/3/14.
 */

public class YoutubeInnerListAdapter extends RecyclerView.Adapter<YoutubeInnerListAdapter.ViewHolder> {
    private ArrayList<YoutubeInfo> youtubeList;
    private OnItemClick itemOnClick;
    private Context context;

    public interface OnItemClick {
        void ItemOnClick(View view, HashMap data);
    }

    public YoutubeInnerListAdapter(ArrayList<YoutubeInfo> youtubeList) {
        this.youtubeList = youtubeList;
    }

    @Override
    public YoutubeInnerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_youtube_inner_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(YoutubeInnerListAdapter.ViewHolder holder, int position) {
        HashMap map = new HashMap();
        map.put("title", youtubeList.get(position).getVideoTitle().toString());
        map.put("videoId", youtubeList.get(position).getVideoUrl().toString());
        map.put("position", position);
        map.put("id", youtubeList.get(position).getVideoId().toString());
        Ion.with(holder.imageView).load(youtubeList.get(position).getThumbnails().toString());
        holder.textView.setText(youtubeList.get(position).getVideoTitle().toString());
        holder.imageView.setTag(position);
        holder.layout.setOnClickListener(imageClick);
        holder.layout.setTag(map);
    }

    @Override
    public int getItemCount() {
        return youtubeList.size();
    }

    View.OnClickListener imageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            itemOnClick.ItemOnClick(v, (HashMap) v.getTag());
        }
    };

    public void setItemClick(OnItemClick itemOnClick) {
        this.itemOnClick = itemOnClick;
    }

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
            imageView = (ImageView) itemView.findViewById(R.id.item_inner_image);
            textView = (TextView) itemView.findViewById(R.id.item_inner_text);
            layout = (LinearLayout) itemView.findViewById(R.id.item_inner_layout);
        }
    }
}
