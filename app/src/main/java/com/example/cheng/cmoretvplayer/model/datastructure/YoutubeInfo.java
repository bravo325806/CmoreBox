package com.example.cheng.cmoretvplayer.model.datastructure;

import java.io.Serializable;

/**
 * Created by cheng on 2017/3/28.
 */

public class YoutubeInfo implements Serializable {
    private String t2id;
    private String t2name;
    private String t2Thumbnails;
    private String videoTitle;
    private String videoUrl;
    private String videoId;
    private String videoThumbnails;

    public void setT2Thumbnails(String t2Thumbnails) {
        this.t2Thumbnails = t2Thumbnails;
    }
    public void setT2id(String t2id) {
        this.t2id = t2id;
    }
    public void setT2name(String t2name) {
        this.t2name = t2name;
    }
    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public void setVideoThumbnails(String videoThumbnails) {
        this.videoThumbnails = videoThumbnails;
    }

    public String getT2Thumbnails() {
        return t2Thumbnails;
    }
    public String getT2id() {
        return t2id;
    }
    public String getT2name() {
        return t2name;
    }
    public String getVideoTitle() {
        return videoTitle;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public String getVideoId() {
        return videoId;
    }
    public String getVideoThumbnails() {
        return videoThumbnails;
    }
}
