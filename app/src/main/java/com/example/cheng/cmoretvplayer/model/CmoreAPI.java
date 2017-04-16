package com.example.cheng.cmoretvplayer.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cheng on 2017/3/27.
 */

public class CmoreAPI {
    private RequestQueue queue;
    private OnGoogleRegisterFinish onGoogleRegisterFinish;
    private OnGoogleLoginFinish onGoogleLoginFinish;
    private OnBagInfoFinish onBagInfoFinish;
    private Context context;
    private boolean checkNetWork=true;
    public CmoreAPI(Context context){
        this.context=context;
        queue = SingleRequestQueue.getQueue(context);
    }

    /**
     * google註冊
     * @param onGoogleRegisterFinish
     */
    public void setOnGoogleRegisterFinish(OnGoogleRegisterFinish onGoogleRegisterFinish) {
        this.onGoogleRegisterFinish = onGoogleRegisterFinish;
    }

    public interface OnGoogleRegisterFinish {
        public void onFinish(String response) throws JSONException;
    }

    public void getGoogleRegister(final String name, final String mail) {
        String url = "http://www.cmoremap.com.tw/cmorechat/register.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        checkNetWork=true;
                        if (onGoogleRegisterFinish != null) {
                            try {
                                onGoogleRegisterFinish.onFinish(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(checkNetWork==true){
                    Toast.makeText(context,"網路連線不穩定",Toast.LENGTH_SHORT).show();
                    checkNetWork=false;
                }
                Log.e("请求错误:", error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("mail", mail);
                params.put("type", "1");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    /**
     * Google登入
     * @param onGoogleLoginFinish
     */
    public void setOnGoogleLoginFinish(OnGoogleLoginFinish onGoogleLoginFinish) {
        this.onGoogleLoginFinish = onGoogleLoginFinish;
    }

    public interface OnGoogleLoginFinish {
        public void onFinish(String response) throws JSONException;
    }

    public void getGoogleLogin(final String plus_id) {
        String url = "http://www.cmoremap.com.tw/cmorechat/gmail_login.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        checkNetWork=true;
                        if (onGoogleLoginFinish != null) {
                            try {
                                onGoogleLoginFinish.onFinish(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(checkNetWork==true){
                    Toast.makeText(context,"網路連線不穩定",Toast.LENGTH_SHORT).show();
                    checkNetWork=false;
                }
                Log.e("请求错误:", error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("plus_id", plus_id);
                return params;
            }
        };
        queue.add(stringRequest);
    }


    /**
     * 背包顯示
     * @param onBagInfoFinish
     */
    public void setOnBagInfoFinish(OnBagInfoFinish onBagInfoFinish) {
        this.onBagInfoFinish = onBagInfoFinish;
    }

    public interface OnBagInfoFinish {
        public void onFinish(String response) throws JSONException;
    }

    public void getBagInfo(final String id) {
        String url = "http://www.cmoremap.com.tw/cmorechat/box_info.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        checkNetWork=true;
                        if (onBagInfoFinish != null) {
                            try {
                                onBagInfoFinish.onFinish(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(checkNetWork==true){
                    Toast.makeText(context,"網路連線不穩定",Toast.LENGTH_SHORT).show();
                    checkNetWork=false;
                }
                Log.e("请求错误:", error.toString());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    /**
     * 對 Volley 的 RequestQueue 做單例模式雙重檢查，避免重複產生 Queue，造成 OutOfMemory 錯誤
     */
    private static final class SingleRequestQueue {
        private volatile static RequestQueue queue;

        private SingleRequestQueue() {
        }

        private static RequestQueue getQueue(Context context) {
            if (queue == null) {
                synchronized (SingleRequestQueue.class) {
                    if (queue == null) {
                        queue = Volley.newRequestQueue(context);
                    }
                }
            }
            return queue;
        }
    }
}
