package com.example.cheng.cmoretvplayer.model;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.view.YoutubeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cheng on 2017/3/14.
 */

public class YoutubeAsync extends AsyncTask {
    private YouTube youTube;
    private ProgressDialog progressDialog;
    private GoogleAccountCredential credential;
    private YoutubeActivity activity;
    private ArrayList<HashMap<String, Object>> youtubePlayList;
    private String response;

    public YoutubeAsync(YoutubeActivity activity, GoogleAccountCredential credential) {
        this.activity = activity;
        this.credential = credential;
        youtubePlayList = new ArrayList<HashMap<String, Object>>();
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
            YouTube.PlaylistItems.List playlistItemRequest = youTube.playlistItems().list("contentDetails,snippet").setPlaylistId("PLbrSzfJVfoG7a7-pmjxQqMv2j8hlD08Jr").setMaxResults(50L).setFields(
                    "items(contentDetails/videoId,snippet/title,snippet/thumbnails),nextPageToken");
            String nextToken = "";
            List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();
            do {
                playlistItemRequest.setPageToken(nextToken);
                PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                playlistItemList.addAll(playlistItemResult.getItems());

                nextToken = playlistItemResult.getNextPageToken();
            } while (nextToken != null);
            Iterator<PlaylistItem> playlistEntries = playlistItemList.iterator();
            while (playlistEntries.hasNext()) {
                PlaylistItem playlistItem = playlistEntries.next();
                response = playlistItem.getSnippet().getTitle() + "\t";
                response = response + playlistItem.getContentDetails().getVideoId() + "\n";
                Log.e("test", response);
                response = "";
                HashMap map = new HashMap();
                map.put("title", playlistItem.getSnippet().getTitle());
                map.put("videoId", playlistItem.getContentDetails().getVideoId());
//                if (playlistItem.getSnippet().getThumbnails().getMaxres() != null) {
//                    map.put("thumbnails", playlistItem.getSnippet().getThumbnails().getMaxres().getUrl());
//                } else if (playlistItem.getSnippet().getThumbnails().getStandard() != null) {
//                    map.put("thumbnails", playlistItem.getSnippet().getThumbnails().getStandard().getUrl());
//                } else if (playlistItem.getSnippet().getThumbnails().getHigh() != null) {
                    map.put("thumbnails", playlistItem.getSnippet().getThumbnails().getHigh().getUrl());
//                } else if (playlistItem.getSnippet().getThumbnails().getMedium() != null) {
//                    map.put("thumbnails", playlistItem.getSnippet().getThumbnails().getMedium().getUrl());
//                } else if (playlistItem.getSnippet().getThumbnails().getDefault() != null) {
//                    map.put("thumbnails", playlistItem.getSnippet().getThumbnails().getDefault().getUrl());
//                }
                youtubePlayList.add(map);
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
        progressDialog.hide();
//        activity.setAdapter(youtubePlayList);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("讀取中 請等待...");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
