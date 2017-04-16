package com.example.cheng.cmoretvplayer.model;

/**
 * Created by cheng on 2017/3/28.
 */

public class YoutubeInfo {
    private String t2id;
    private String t2name;
    private String videoTitle;
    private String videoUrl;
    private String videoId;
    private String thumbnails;
    public void setT2id(String t2id){
        this.t2id=t2id;
    }
    public void setT2name(String t2name){
        this.t2name=t2name;
    }
    public void setVideoTitle(String videoTitle){
        this.videoTitle=videoTitle;
    }
    public void setVideoUrl(String videoUrl){
        this.videoUrl=videoUrl;
    }
    public void setVideoId(String videoId){
        this.videoId=videoId;
    }
    public void setThumbnails(String thumbnails){
        this.thumbnails=thumbnails;
    }
    public String getT2id(){
        return t2id;
    }
    public String getT2name(){
       return t2name;
    }
    public String getVideoTitle(){
        return videoTitle;
    }
    public String getVideoUrl(){
       return videoUrl;
    }
    public String getVideoId(){
        return videoId;
    }
    public String getThumbnails(){
        return thumbnails;
    }
}
