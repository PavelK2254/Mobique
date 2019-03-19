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
    private Category[] mFullDataSet;
    private int mCurrentSelectedCategory,mCurrentSelectedProduct;



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
        mFullDataSet = categoryArray;
        categoriesData.postValue(categoryArray);

    }

    @Override
    public void onNetworkFailure() {
        categoriesData.postValue(null);
    }

    @Override
    public void onProductItemClicked(int itemIndex) {
        mCurrentSelectedProduct = itemIndex;
    }

    public Product[] getCurrentProducts(int position){
        Category currentCategory = mFullDataSet[position];
        return currentCategory.getProducts().toArray(new Product[0]);
    }

   public Product getCurrentProduct(){

        Category currentCategory;
        if(mFullDataSet != null){
            currentCategory = mFullDataSet[mCurrentSelectedCategory];
            return currentCategory.getProducts().get(mCurrentSelectedProduct);
        }else{
            return null;
        }


   }


    public void setmCurrentSelectedCategory(int mCurrentSelectedCategory) {
        this.mCurrentSelectedCategory = mCurrentSelectedCategory;
    }

    public void setCategoriesData(MutableLiveData<Category[]> categoriesData) {
        this.categoriesData = categoriesData;
    }


}
