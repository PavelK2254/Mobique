package com.exam.pk.mobiquitest;


import com.exam.pk.mobiquitest.Model.Category;
import com.google.gson.Gson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListPageVM extends ViewModel implements IListPageVM {

    private NetworkManager mNetworkManager;
    private MutableLiveData<Category[]> categoryData;

    public ListPageVM() {
        mNetworkManager = NetworkManager.getInstance();
        mNetworkManager.setListPagePresenter(this);
    }

    public LiveData<Category[]> getCategories(String url){
        if(categoryData == null){
            categoryData = new MutableLiveData<>();
            mNetworkManager.doGetRequest(url);
        }
        return categoryData;
    }

    @Override
    public void onNetworkResponse(String response) {
        Gson gson = new Gson();
        Category[] categoryArray = gson.fromJson(response, Category[].class);
        categoryData.postValue(categoryArray);

    }

}
