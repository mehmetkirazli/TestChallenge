package com.mehmetkirazli.testchallenge.util;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application { // volley kütüphanesini kullanabilmek için eklendi

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    // kuyruya istek eklemek için kullanılır (istek adıyla beraber)
    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    // kuyruya istek eklemek için kullanılır (istek adı olmadan)
    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    // volley isteğini iptal etmek için çağrılır
    public void cancelPendingRequests(Object obj) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(obj);
        }
    }
}