package com.exam.pk.mobiquitest;


import com.exam.pk.mobiquitest.Model.Category;
import com.exam.pk.mobiquitest.Model.Product;
import com.google.gson.Gson;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListPageVM extends ViewModel implements IListPageVM {

    private NetworkManager mNetworkManager;
    private MutableLiveData<Category[]> categoriesData;
    private MutableLiveData<Product[]> productsData;

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

    public LiveData<Product> getProduct(int positionInCategory){
       //TODO : Finish this
    }

    @Override
    public void onNetworkResponse(String response) {
        Gson gson = new Gson();
        Category[] categoryArray = gson.fromJson(response, Category[].class);
        categoriesData.postValue(categoryArray);

    }

}
