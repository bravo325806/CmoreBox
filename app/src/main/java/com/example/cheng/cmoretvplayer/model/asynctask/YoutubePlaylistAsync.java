package com.example.cheng.cmoretvplayer.model.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.datastructure.YoutubeInfo;
import com.example.cheng.cmoretvplayer.view.activity.MenuActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by cheng on 2017/3/14.
 */

public class YoutubePlaylistAsync extends AsyncTask {
    private YouTube youTube;
    private ProgressDialog progressDialog;
    private GoogleAccountCredential credential;
    private MenuActivity activity;
    private ArrayList<ArrayList<YoutubeInfo>> youtubePlayList;
    private String response;

    public YoutubePlaylistAsync(MenuActivity activity, GoogleAccountCredential credential,ArrayList<ArrayList<YoutubeInfo>> youtubePlayList) {
        this.activity = activity;
        this.credential = credential;
        this.youtubePlayList=youtubePlayList;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        final HttpTransport transport = AndroidHttp.newCompatibleTransport();
        com.google.api.client.json.JsonFactory jsonFactory = new com.google.api.client.extensions.android.json.AndroidJsonFactory();
        youTube = new YouTube.Builder(transport, jsonFactory, credential)
                .setApplicationName(activity.getString(R.string.app_name))
                .build();
        Log.e("start", "start");
        try {
            for (int i = 0; i < youtubePlayList.size(); i++) {
                    YouTube.Videos.List videoRequest = youTube.videos().list("contentDetails,snippet").setId(youtubePlayList.get(i).get(0).getVideoUrl().toString())
                            .setFields("items/snippet/thumbnails");
                    VideoListResponse video = videoRequest.execute();
                    if(video.getItems().isEmpty()){
                        videoRequest = youTube.videos().list("contentDetails,snippet").setId(youtubePlayList.get(i).get(1).getVideoUrl().toString())
                                .setFields("items/snippet/thumbnails");
                        video = videoRequest.execute();
                        youtubePlayList.get(i).get(1).setT2Thumbnails(video.getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl());
                    }else{
                        youtubePlayList.get(i).get(0).setT2Thumbnails(video.getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl());
                    }


            }
        } catch (UserRecoverableAuthIOException e) {
            //跳出權限dialog
            activity.startActivityForResult(e.getIntent(), activity.RESULTCALLBACK);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        YoutubeVideoAsync youtubeVideoAsync = new YoutubeVideoAsync(activity, credential,youtubePlayList);
        youtubeVideoAsync.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
