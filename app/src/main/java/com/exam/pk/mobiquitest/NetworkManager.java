package com.exam.pk.mobiquitest;

import android.content.Context;
import android.util.Log;

import com.exam.pk.mobiquitest.Model.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager {
    private Context mContext;
    private String TAG = getClass().getSimpleName();
    private OkHttpClient mClient = new OkHttpClient();
    private static final NetworkManager ourInstance = new NetworkManager();
    private  IListPagePresenter iListPagePresenter;


    public static NetworkManager getInstance() {
        return ourInstance;
    }

    private NetworkManager() {
        }

        public void setListPagePresenter(IListPagePresenter listPagePresenter){
            iListPagePresenter = listPagePresenter;
        }


        public void doGetRequest(String url){
            Request request = new Request.Builder().url(url).build();

            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i(TAG, "Error: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                   String body = response.body().string();
                    iListPagePresenter.onNetworkResponse(body);
                }
            });
        }

    }


