package com.example.cheng.cmoretvplayer.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.CmoreAPI;
import com.example.cheng.cmoretvplayer.model.YoutubeAsync;
import com.example.cheng.cmoretvplayer.model.YoutubeInfo;
import com.example.cheng.cmoretvplayer.model.YoutubeVideoAsync;
import com.example.cheng.cmoretvplayer.model.adapter.YoutubeDrawerAdapter;
import com.example.cheng.cmoretvplayer.model.adapter.YoutubeInnerListAdapter;
import com.example.cheng.cmoretvplayer.model.adapter.YoutubeOuterListAdapter;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.oauth2.Oauth2Scopes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class YoutubeActivity extends AppCompatActivity {
    @Bind(R.id.youtube_outer_list)
    RecyclerView youtubeOuterList;
    @Bind(R.id.youtube_drawer_list)
    ListView youtubeDrawerList;
    @Bind(R.id.activity_drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.activity_outer_main)
    RelativeLayout mainLayout;
    private YoutubeOuterListAdapter youtubeOuterListAdapter;
    private YoutubeDrawerAdapter youtubeDrawerAdapter;
    public static final int RESULTCALLBACK = 666;
    private String email, disPlayName, id;
    private GoogleAccountCredential credential;
    private Handler handler = new Handler();

    private Toast toast;
    private int position = 0;
    private CmoreAPI cmoreAPI;
    private ArrayList<ArrayList<YoutubeInfo>> youtubeList;
    private ArrayList<ArrayList<YoutubeInfo>> arrayLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_youtube_list);
//        Fabric.with(this, new Crashlytics());
        ButterKnife.bind(this);
        email = getIntent().getStringExtra("email");
        disPlayName = getIntent().getStringExtra("disPlayName");
        id = getIntent().getStringExtra("id");
        cmoreAPI = new CmoreAPI(YoutubeActivity.this);
        init();
//        logUser();
    }

    private void init() {
        arrayLists=new ArrayList<>();
        for (int i=0;i<6;i++){
            ArrayList arrayList2=new ArrayList();
            YoutubeInfo youtubeInfo=new YoutubeInfo();
            youtubeInfo.setT2name("test");
            arrayList2.add(youtubeInfo);
            arrayLists.add(arrayList2);
        }

        youtubeList = new ArrayList<>();
        setRecycleView();
//        setAPI();
//        setDrawerLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setDrawerLayout();
            }
        },3000);
    }


    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");
    }
    private void setRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        youtubeOuterList.setLayoutManager(linearLayoutManager);

    }

    private void setAPI() {

        cmoreAPI.getBagInfo(id);
        cmoreAPI.setOnBagInfoFinish(onBagDownLoadFinish);
    }

    private void setDrawerLayout() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float moveFactor = (youtubeDrawerList.getWidth() * slideOffset);
                mainLayout.setTranslationX(moveFactor);
            }
        };
        drawerLayout.addDrawerListener(mDrawerToggle);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.openDrawer(Gravity.LEFT);

        youtubeDrawerAdapter = new YoutubeDrawerAdapter(arrayLists);
        youtubeDrawerList.setAdapter(youtubeDrawerAdapter);
    }
    private void setCredential() {
        credential = GoogleAccountCredential.usingOAuth2(
                YoutubeActivity.this,
                Oauth2Scopes.all()
        );
        credential.setSelectedAccountName(email);

        YoutubeVideoAsync youtubeVideoAsync = new YoutubeVideoAsync(YoutubeActivity.this, credential,youtubeList);
        youtubeVideoAsync.execute();
    }

    public void setAdapter(ArrayList<ArrayList<YoutubeInfo>> arrayList) {
        if (youtubeOuterList.getAdapter() == null) {
            youtubeOuterListAdapter = new YoutubeOuterListAdapter(arrayList);
            youtubeOuterList.setAdapter(youtubeOuterListAdapter);
        } else {
            youtubeOuterListAdapter.setArrayList(arrayList);
            youtubeOuterListAdapter.notifyDataSetChanged();
        }
        if (youtubeDrawerList.getAdapter() == null) {
            youtubeDrawerAdapter = new YoutubeDrawerAdapter(arrayList);
            youtubeDrawerList.setAdapter(youtubeDrawerAdapter);
//            youtubeDrawerAdapter.setItemClick(drawerItemClick);
        } else {
//            youtubeDrawerAdapter.setArrayList(arrayList);
            youtubeDrawerAdapter.notifyDataSetChanged();
        }
        handler.post(runnable);
    }
    //    YoutubeDrawerAdapter.OnItemClick drawerItemClick = new YoutubeDrawerAdapter.OnItemClick() {
//        @Override
//        public void ItemOnClick() {
//
//            drawerLayout.closeDrawers();
//        }
//    };
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (youtubeOuterListAdapter.getYoutubeInner() != null) {
                youtubeOuterListAdapter.getYoutubeInner().setItemClick(innerItemClick);
            }
            handler.postDelayed(runnable, 20);
        }
    };

    YoutubeInnerListAdapter.OnItemClick innerItemClick = new YoutubeInnerListAdapter.OnItemClick() {
        @Override
        public void ItemOnClick(View view, HashMap data) {
            Intent intent = new Intent(YoutubeActivity.this, YoutubePlayerActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (youtubeDrawerList.hasFocus() && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            drawerLayout.closeDrawers();
        } else if (!youtubeOuterList.hasFocus() && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            View v = youtubeDrawerList.getFocusedChild();
//            position = youtubeDrawerList.getChildAdapterPosition(v);
            if (position >= 0) {
                youtubeOuterList.smoothScrollToPosition(position);
            }
            showToast(position + "");
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            View v = youtubeDrawerList.getFocusedChild();
//            position = youtubeDrawerList.getChildAdapterPosition(v);
            if (position >= 0) {
                youtubeOuterList.smoothScrollToPosition(position);
            }
            showToast(position + "");
        }
        return super.dispatchKeyEvent(event);
    }
    CmoreAPI.OnBagInfoFinish onBagDownLoadFinish = new CmoreAPI.OnBagInfoFinish() {
        @Override
        public void onFinish(String response) throws JSONException {
            youtubeList.clear();
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(1);
            JSONArray data = jsonObject.getJSONArray("value");
            for (int i = 0; i < data.length(); i++) {
                JSONObject layer1 = data.getJSONObject(i);
                //group
                try {
                    JSONArray layer2 = layer1.getJSONArray("layer1_value");

                    for (int j = 0; j < layer2.length(); j++) {
                        int count=0;
                        JSONObject jsonObject2 = layer2.getJSONObject(j);
                        JSONArray urlArray = jsonObject2.getJSONArray("layer2_value");
                        ArrayList arrayList=new ArrayList<YoutubeInfo>();
                        for (int k = 0; k < urlArray.length(); k++) {

                            String url[]=urlArray.getJSONObject(k).get("url").toString().split("v=");
                            YoutubeInfo youtubeInfo=new YoutubeInfo();
                            youtubeInfo.setT2id(jsonObject2.get("layer2_id").toString());
                            youtubeInfo.setT2name(jsonObject2.get("layer2_name").toString());
                            youtubeInfo.setVideoId(urlArray.getJSONObject(k).get("id").toString());
                            youtubeInfo.setVideoTitle(urlArray.getJSONObject(k).get("name").toString());
                            youtubeInfo.setVideoUrl(url[1]);

                            arrayList.add(youtubeInfo);

                            count++;
                            if(count>26){
                                youtubeList.add(arrayList);
                                arrayList=new ArrayList<YoutubeInfo>();
                                count=0;
                            }
                        }
                        youtubeList.add(arrayList);
                    }
                } catch (Exception ex) {
                    Log.e("jsonparse error", ex.toString());
                }
            }
            setCredential();
        }
    };

    private void showToast(String str) {
        if (toast == null) {
            //如果還沒有用過makeText方法，才使用
            toast = android.widget.Toast.makeText(YoutubeActivity.this, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
