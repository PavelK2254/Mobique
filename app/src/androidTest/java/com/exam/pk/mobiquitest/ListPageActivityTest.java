package com.exam.pk.mobiquitest;

import com.exam.pk.mobiquitest.View.ListPage.ListPageActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.rule.ActivityTestRule;

import static junit.framework.TestCase.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
public class ListPageActivityTest {

    @Rule
    public ActivityTestRule<ListPageActivity> mActivityTestRule = new ActivityTestRule<>(ListPageActivity.class);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    private ListPageActivity mActivity;


    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
    }



    @Test
    public void viewPagerNotNull(){
        assertNotNull(mActivity.getViewPager());
    }

    @Test
    public void tabsNotNull(){
        assertNotNull(mActivity.getPageListTabs());
    }

    @Test
    public void showNoConnectionIfInternetNotConnected() {
        mActivity.refreshData(false,mActivity.getString(R.string.baseUrl), categories -> assertNotNull(mActivity.getSupportFragmentManager().findFragmentByTag("noInternetDialog")));
    }

    @Test
    public void errorMessageWhenServerNotAvailable() {
        mActivity.refreshData(true,mActivity.getString(R.string.baseUrl), categories -> assertNotNull(mActivity.getSupportFragmentManager().findFragmentByTag("serverErrorDialog")));
    }

    @After
    public void tearDown() {
        mActivity = null;
    }
}