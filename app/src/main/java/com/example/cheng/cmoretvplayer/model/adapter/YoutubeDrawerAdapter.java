package com.example.cheng.cmoretvplayer.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.YoutubeInfo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cheng on 2017/3/20.
 */

public class YoutubeDrawerAdapter extends BaseAdapter {
    private ArrayList<ArrayList<YoutubeInfo>> youtubeTitle;

    public YoutubeDrawerAdapter(ArrayList<ArrayList<YoutubeInfo>> arrayLists) {
        this.youtubeTitle=arrayLists;
    }

    @Override
    public int getCount() {
        return youtubeTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_youtube_drawer_list, null);
            holder = new Holder();
            holder.text = (TextView) convertView.findViewById(R.id.item_drawer_text);
        }
        holder.text.setText(youtubeTitle.get(position).get(0).getT2name());
        return convertView;
    }
    class Holder{
        TextView text;
    }
}
