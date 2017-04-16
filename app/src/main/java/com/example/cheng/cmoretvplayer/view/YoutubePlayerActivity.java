package com.example.cheng.cmoretvplayer.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.Developer;
import com.example.cheng.cmoretvplayer.model.YoutubeInfo;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cheng on 2017/3/16.
 */

public class YoutubePlayerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_REQUEST = 1;
    YouTubePlayerView youTubeView;
    @Bind(R.id.youtube_view_title)
    TextView youtube_view_title;
    ArrayList<ArrayList<YoutubeInfo>> youtubeList;
    HashMap data;
    private YouTubePlayer player;
    private boolean fullScreen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        ButterKnife.bind(this);
        youtubeList = (ArrayList<ArrayList<YoutubeInfo>>) getIntent().getSerializableExtra("list");
        data = (HashMap) getIntent().getSerializableExtra("data");
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Developer.Key, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            player = youTubePlayer;
            youtube_view_title.setText(data.get("title").toString());
            youTubePlayer.loadVideo(data.get("videoId").toString());
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
        }
        return super.dispatchKeyEvent(event);

    }

    YouTubePlayer.OnFullscreenListener FullScreenListener = new YouTubePlayer.OnFullscreenListener() {
        @Override
        public void onFullscreen(boolean b) {
            fullScreen = b;
        }
    };
}