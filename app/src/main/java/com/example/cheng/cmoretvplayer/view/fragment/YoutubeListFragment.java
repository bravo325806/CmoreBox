package com.example.cheng.cmoretvplayer.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.datastructure.YoutubeInfo;
import com.example.cheng.cmoretvplayer.view.activity.YoutubePlayerActivity;
import com.example.cheng.cmoretvplayer.view.adapter.ListFragmentOuterAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cheng on 2017/5/17.
 */

public class YoutubeListFragment extends Fragment{
    @Bind(R.id.youtube_fragment_outer_list)
    RecyclerView youtubeOuterList;
    private ListFragmentOuterAdapter listFragmentOuterAdapter;
    private ArrayList<YoutubeInfo> youtubeList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_youtube_list, container, false);
        ButterKnife.bind(this,rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    private void init() {
        youtubeList=((YoutubePlayerActivity)getActivity()).getYoutubeList();
        listFragmentOuterAdapter=new ListFragmentOuterAdapter(youtubeList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        youtubeOuterList.setLayoutManager(linearLayoutManager);
        youtubeOuterList.setAdapter(listFragmentOuterAdapter);
    }
    public ListFragmentOuterAdapter getListFragmentOuterAdapter(){
        return listFragmentOuterAdapter;
    }
}
