package com.exam.pk.mobiquitest.View.ListPage;

import android.os.Bundle;
import android.widget.FrameLayout;


import com.exam.pk.mobiquitest.ListPageVM;
import com.exam.pk.mobiquitest.Model.Category;
import com.exam.pk.mobiquitest.R;
import com.exam.pk.mobiquitest.View.DetailPage.DetailsFragment;
import com.google.android.material.tabs.TabLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ListPageActivity extends AppCompatActivity  {



    @BindView(R.id.list_tabs)
    TabLayout pageListTabs;

    @BindView(R.id.pager)
    ViewPager viewPager;

    ListPageVM listPageVM;
    PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        pageListTabs.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        listPageVM = ViewModelProviders.of(this).get(ListPageVM.class);
        listPageVM.getCategories(getString(R.string.baseUrl)).observe(this, categories -> {
            applyCategories(categories);

        });
    }

    public void applyCategories(Category[] categories){
            runOnUiThread(() -> {
                initViewPager(categories);
            });
    }

    private void initViewPager(Category[] categories){
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),categories);
        viewPager.setAdapter(mPagerAdapter);
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
}
