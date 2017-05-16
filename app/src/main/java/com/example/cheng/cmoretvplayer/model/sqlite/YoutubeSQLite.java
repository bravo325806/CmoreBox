package com.example.cheng.cmoretvplayer.model.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cheng.cmoretvplayer.model.datastructure.YoutubeInfo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cheng on 2017/5/15.
 */

public class YoutubeSQLite extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;  //資料庫版本
    private static final String DATABASE_NAME = "cmoreBox.db";  //資料庫名稱
    public static final String youtubeTable="youtubeTable";  //資料表名稱
    private String myMaid;
    public YoutubeSQLite(Context context,String myMaid) {
        super(context, DATABASE_NAME+myMaid, null, DATABASE_VERSION);
        this.myMaid=myMaid;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + youtubeTable + myMaid + " (t2id varchar(100),t2name varchar(200),t2Thumbnails varchar(4000),videoTitle varchar(200),videoUrl varchar(100),videoId varchar(100),videoThumbnails varchar(4000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + youtubeTable + myMaid);
        onCreate(db);
    }
    //取得資料
    public ArrayList<ArrayList<YoutubeInfo>> getDataBase() {
        Cursor cursor = this.getReadableDatabase().rawQuery("select t2id from " + youtubeTable + myMaid+" group by t2id", null);
        ArrayList t2idList=new ArrayList();
        int t2id_num=cursor.getCount();
        if(t2id_num>0){
            try {
                while (cursor.moveToNext()) {
                    t2idList.add(cursor.getString(0));
                }

                ArrayList<ArrayList<YoutubeInfo>> arrayList = new ArrayList<>();
                for(int i=0;i<t2idList.size();i++){
                    ArrayList<YoutubeInfo> arrayList2 = new ArrayList<>();
                    cursor = this.getReadableDatabase().rawQuery("select * from " + youtubeTable + myMaid+" where t2id='"+t2idList.get(i)+"'", null);
                    int rows_num = cursor.getCount();//取得資料表列數
                    if (rows_num > 0) {
                        while (cursor.moveToNext()){
                            arrayList2.add(getRecord(cursor));
                        }
                        arrayList.add(arrayList2);
                    }
                }
                return arrayList;
            } catch (Exception e) {
                Log.e("searchDuplicateValue", e.toString());
            }
        }
        cursor.close(); //關閉Cursor
        return null;
    }
    // 把Cursor目前的資料包裝為物件
    public YoutubeInfo getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        YoutubeInfo youtubeInfo=new YoutubeInfo();
        youtubeInfo.setT2id(cursor.getString(0));
        youtubeInfo.setT2name(cursor.getString(1));
        youtubeInfo.setT2Thumbnails(cursor.getString(2));
        youtubeInfo.setVideoTitle(cursor.getString(3));
        youtubeInfo.setVideoUrl(cursor.getString(4));
        youtubeInfo.setVideoId(cursor.getString(5));
        youtubeInfo.setVideoThumbnails(cursor.getString(6));
        // 回傳結果
        return youtubeInfo;
    }
    public void addDb(ArrayList<ArrayList<YoutubeInfo>> arrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<arrayList.size();i++){
            for(int j=0;j<arrayList.get(i).size();j++) {
                ContentValues values = new ContentValues();
                values.put("t2id", arrayList.get(i).get(j).getT2id());
                values.put("t2name", arrayList.get(i).get(j).getT2name());
                values.put("t2Thumbnails", arrayList.get(i).get(j).getT2Thumbnails());
                values.put("videoTitle", arrayList.get(i).get(j).getVideoTitle());
                values.put("videoUrl", arrayList.get(i).get(j).getVideoUrl());
                values.put("videoId", arrayList.get(i).get(j).getVideoId());
                values.put("videoThumbnails", arrayList.get(i).get(j).getVideoThumbnails());
                //第一個值為table名稱,第二個值為當insert值為空時,會由null值取代,因為不允許值為空白,第三個值為新增的值
                long k = db.insert(youtubeTable + myMaid, null, values);
                Log.e("k", String.valueOf(k));
            }
        }
    }
    //刪除資料
    public void delDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        //第一個值為資料表名稱,第二個值為條件,如果設為?號,可由第三個值裡的陣列值來套用
        db.delete(youtubeTable + myMaid,null, null);
    }
}
