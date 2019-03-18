package com.exam.pk.mobiquitest;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager {

    private OkHttpClient mClient = new OkHttpClient();
    private static final NetworkManager ourInstance = new NetworkManager();
    private IListPageVM iListPageVM;


    public static NetworkManager getInstance() {
        return ourInstance;
    }

    private NetworkManager() {
        }

        public void setListPagePresenter(IListPageVM listPagePresenter){
            iListPageVM = listPagePresenter;
        }


        public void doGetRequest(String url){
            Request request = new Request.Builder().url(url).build();
            mClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    iListPageVM.onNetworkFailure();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                   String body = response.body().string();
                    iListPageVM.onNetworkResponse(body);
                }
            });
        }
    }


