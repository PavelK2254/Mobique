package com.exam.pk.mobiquitest;


import com.exam.pk.mobiquitest.Model.Category;
import com.exam.pk.mobiquitest.Model.Product;
import com.google.gson.Gson;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListPageVM extends ViewModel implements IListPageVM {

    private NetworkManager mNetworkManager;
    private MutableLiveData<Category[]> categoriesData;


    public ListPageVM() {
        mNetworkManager = NetworkManager.getInstance();
        mNetworkManager.setListPagePresenter(this);
    }

    public LiveData<Category[]> getCategories(String url){
        if(categoriesData == null){
            categoriesData = new MutableLiveData<>();
            mNetworkManager.doGetRequest(url);
        }
        return categoriesData;
    }



    @Override
    public void onNetworkResponse(String response) {
        Gson gson = new Gson();
        Category[] categoryArray = gson.fromJson(response, Category[].class);
        categoriesData.postValue(categoryArray);

    }

    @Override
    public void onProductItemClicked() {

    }

}
