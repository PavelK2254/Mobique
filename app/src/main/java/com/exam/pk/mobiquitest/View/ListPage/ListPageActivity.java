package com.exam.pk.mobiquitest.View.ListPage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.exam.pk.mobiquitest.IRefreshCallback;
import com.exam.pk.mobiquitest.ListPageVM;
import com.exam.pk.mobiquitest.Model.Category;
import com.exam.pk.mobiquitest.R;
import com.exam.pk.mobiquitest.View.DetailPage.DetailsFragment;
import com.exam.pk.mobiquitest.View.Dialogs.NoInternetDialog;
import com.exam.pk.mobiquitest.View.Dialogs.ServerErrorDialog;
import com.exam.pk.mobiquitest.View.ListPageSwipeRefreshLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ListPageActivity extends AppCompatActivity implements IRefreshCallback{



    @BindView(R.id.list_tabs)
    TabLayout pageListTabs;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.swipe_resfresh)
    ListPageSwipeRefreshLayout mSwipeRefreshLayout;


    ListPageVM listPageVM;
    PagerAdapter mPagerAdapter;
    ConnectivityManager cm;
    NetworkInfo activeNetwork;
    boolean isConnected;

    public TabLayout getPageListTabs() {
        return pageListTabs;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);

        Objects.requireNonNull(getSupportActionBar()).hide();
        ButterKnife.bind(this);
        pageListTabs.setupWithViewPager(viewPager);
        listPageVM = ViewModelProviders.of(this).get(ListPageVM.class);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            listPageVM.setCategoriesData(null);
            refreshData(isInternetConnected(),getString(R.string.baseUrl),this);
        });
        cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);


    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshData(isInternetConnected(),getString(R.string.baseUrl),this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listPageVM = null;
    }


    private boolean isInternetConnected(){
        activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public void refreshData(boolean isInternetConnected,String url,IRefreshCallback iRefreshCallback){
        if(isInternetConnected){
            listPageVM.getCategories(url).observe(this, categories -> {
                if(categories != null){
                    iRefreshCallback.dataRefreshed(categories);
                }else{
                    listPageVM.setCategoriesData(null);
                    openServerErrorDialog();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            });
        }else{
            displayNoInternetDialog();
        }

    }

    private void displayNoInternetDialog() {
        NoInternetDialog noInternetDialog = new NoInternetDialog();
        noInternetDialog.show(getSupportFragmentManager(),"noInternetDialog");
    }

    private void openServerErrorDialog() {
        ServerErrorDialog serverErrorDialog = new ServerErrorDialog();
        serverErrorDialog.show(getSupportFragmentManager(),"serverErrorDialog");
    }

    public void applyCategories(Category[] categories){
            runOnUiThread(() -> initViewPager(categories));
    }


    private void initViewPager(Category[] categories){
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),categories);
        if(viewPager.getAdapter() == null){
            viewPager.setAdapter(mPagerAdapter);
        }else{
            viewPager.getAdapter().notifyDataSetChanged();
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                listPageVM.setmCurrentSelectedCategory(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    public void clickOnProduct(int position){
        listPageVM.onProductItemClicked(position);
        showDetailFragment();
    }

    public void showDetailFragment(){
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("currentCategory",pageListTabs.getSelectedTabPosition());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_right);
        fragmentTransaction.add(R.id.root_view,detailsFragment).addToBackStack("detailStack").commit();
    }

    public void closeDetail(View v){
        getSupportFragmentManager().popBackStack();
    }

    public void retryDataRefresh(){
        refreshData(isInternetConnected(),getString(R.string.baseUrl),this);
    }

    @Override
    public void dataRefreshed(Category[] categories) {
        applyCategories(categories);
    }
}
