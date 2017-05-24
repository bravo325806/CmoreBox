package com.example.cheng.cmoretvplayer.view.activity;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.Developer;
import com.example.cheng.cmoretvplayer.model.datastructure.YoutubeInfo;
import com.example.cheng.cmoretvplayer.view.adapter.ListFragmentInnerAdapter;
import com.example.cheng.cmoretvplayer.view.fragment.YoutubeListFragment;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.services.youtube.model.PlaylistListResponse;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cheng on 2017/3/16.
 */

public class YoutubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    private static final int RECOVERY_REQUEST = 1;
    YouTubePlayerView youTubeView;
    @Bind(R.id.youtube_view_title)
    TextView youtube_view_title;
    private static ArrayList<YoutubeInfo> youtubeList;
    private YouTubePlayer player;
    private boolean fullScreen = false;
    private YouTubePlayerFragment youTubePlayerFragment;
    private YoutubeListFragment youtubeListFragment;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        ButterKnife.bind(this);
        init();
    }
    private void init(){
        youtubeList = (ArrayList<YoutubeInfo>) getIntent().getSerializableExtra("data");
        youtubeListFragment=(YoutubeListFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment_list);
        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment_player);
        youTubePlayerFragment.initialize(Developer.Key, this);
        handler=new Handler();
        handler.post(runnable);
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            player = youTubePlayer;
            youtube_view_title.setText(youtubeList.get(0).getVideoTitle());
            youTubePlayer.loadVideo(youtubeList.get(0).getVideoUrl());
            youTubePlayer.setOnFullscreenListener(FullScreenListener);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format("Error initializing YouTube player", errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (fullScreen) {
            player.setFullscreen(false);
        } else {
           super.onBackPressed();
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (!fullScreen) {
                player.setFullscreen(true);
            }
        }else if(keyCode==KeyEvent.KEYCODE_DPAD_DOWN){
            if (youTubePlayerFragment.getView().getVisibility()==View.GONE){
                youTubePlayerFragment.getView().setVisibility(View.VISIBLE);
            }else {
                youTubePlayerFragment.getView().setVisibility(View.GONE);
            }
        }else if(keyCode==KeyEvent.KEYCODE_ENTER){
            if(player.isPlaying()){
                player.pause();
            }else{
                player.play();
            }
        }
        return super.dispatchKeyEvent(event);

    }

    YouTubePlayer.OnFullscreenListener FullScreenListener = new YouTubePlayer.OnFullscreenListener() {
        @Override
        public void onFullscreen(boolean b) {
            fullScreen = b;
        }
    };
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (youtubeListFragment.getListFragmentOuterAdapter().getYoutubeInner() != null) {
                youtubeListFragment.getListFragmentOuterAdapter().getYoutubeInner().setItemClick(innerItemClick);
            }
            handler.postDelayed(runnable, 20);
        }
    };
    ListFragmentInnerAdapter.OnItemClick innerItemClick=new ListFragmentInnerAdapter.OnItemClick() {
        @Override
        public void ItemOnClick(View view) {
            youTubePlayerFragment.getView().setVisibility(View.VISIBLE);
            youtube_view_title.setText(youtubeList.get(0).getVideoTitle());
            player.loadVideo(((YoutubeInfo)view.getTag()).getVideoUrl());
            player.setOnFullscreenListener(FullScreenListener);
        }
    };
    public ArrayList<YoutubeInfo> getYoutubeList(){
        return youtubeList;
    }
}
