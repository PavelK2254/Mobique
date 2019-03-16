package com.exam.pk.mobiquitest;


import android.content.Context;

import com.exam.pk.mobiquitest.Model.Category;
import com.exam.pk.mobiquitest.View.ListPage.ListPageActivity;
import com.google.gson.Gson;

public class ListPagePresenter implements IListPagePresenter{

    private NetworkManager mNetworkManager;
    private Category[] categoryArray;
    private ListPageActivity mListPageActivity;

    public ListPagePresenter(ListPageActivity listPage) {
        mListPageActivity = listPage;
        mNetworkManager = NetworkManager.getInstance();
        mNetworkManager.setListPagePresenter(this);
        mNetworkManager.doGetRequest(listPage.getString(R.string.baseUrl));
    }



    @Override
    public void onNetworkResponse(String response) {
        Gson gson = new Gson();
        categoryArray = gson.fromJson(response,Category[].class);
        mListPageActivity.applyCategories();

    }

    public Category[] getCategoryArray() {
        return categoryArray;
    }
}
