package com.example.cheng.cmoretvplayer.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.cheng.cmoretvplayer.R;
import com.example.cheng.cmoretvplayer.model.CmoreAPI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    public static final int YOUTUBEINTENT = 1;
    private static final int RC_SIGN_IN = 0;
    private static final int REQUEST_CONTACTS = 66;
    @Bind(R.id.googleButton)
    SignInButton googleButton;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount acct;
    private CmoreAPI cmoreAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        cmoreAPI=new CmoreAPI(MainActivity.this);
    }
    @OnClick(R.id.googleButton)
    void googleButtonClick(View v) {
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    REQUEST_CONTACTS);
        } else {
            //已有權限，可進行檔案存取
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CONTACTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //取得聯絡人權限，進行存取
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                } else {
                    //使用者拒絕權限，顯示對話框告知
                    new AlertDialog.Builder(this)
                            .setMessage("必須允許聯絡人存取權限才能讀取資料")
                            .setPositiveButton("OK", null)
                            .show();
                }
                return;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            acct = result.getSignInAccount();
            int statusCode = result.getStatus().getStatusCode();
            Log.e("google status code", statusCode + "");
            cmoreAPI.getGoogleRegister(acct.getDisplayName(), acct.getEmail());
            cmoreAPI.setOnGoogleRegisterFinish(onGoogleRegisterFinish);

        }
    }
    CmoreAPI.OnGoogleRegisterFinish onGoogleRegisterFinish=new CmoreAPI.OnGoogleRegisterFinish() {
        @Override
        public void onFinish(String response) throws JSONException {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            if (jsonObject.getString("result").equals("0")) {

                cmoreAPI.getGoogleLogin(acct.getEmail());
                cmoreAPI.setOnGoogleLoginFinish(onGoogleLoginFinish);
            } else if (jsonObject.getString("result").equals("2")) {
                cmoreAPI.getGoogleLogin(acct.getEmail());
                cmoreAPI.setOnGoogleLoginFinish(onGoogleLoginFinish);
            }
        }
    };
    CmoreAPI.OnGoogleLoginFinish onGoogleLoginFinish=new CmoreAPI.OnGoogleLoginFinish() {
        @Override
        public void onFinish(String response) throws JSONException {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            if (jsonObject.getString("result").equals("0")) {
                if (acct != null) {
                    Intent intent = new Intent(MainActivity.this, YoutubeActivity.class);
                    intent.putExtra("email", acct.getEmail());
                    intent.putExtra("disPlayName", acct.getDisplayName());
                    intent.putExtra("id",jsonObject.getString("id"));
                    startActivityForResult(intent, YOUTUBEINTENT);
                }
            }
        }
    };
}
