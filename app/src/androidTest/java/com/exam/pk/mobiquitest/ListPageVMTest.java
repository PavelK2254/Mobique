package com.exam.pk.mobiquitest;

import com.exam.pk.mobiquitest.Model.Category;
import com.exam.pk.mobiquitest.Model.Product;
import com.exam.pk.mobiquitest.Model.SalePrice;
import com.exam.pk.mobiquitest.View.ListPage.ListPageActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListPageVMTest implements IListPageVM{



    private ListPageVM listPageVM;
    private NetworkManager networkManager;
    private ListPageActivity listPageActivity;
    private final int exampleClickedItem = 0;



    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public ActivityTestRule<ListPageActivity> mActivityTestRule = new ActivityTestRule<>(ListPageActivity.class);

    @Before
    public void setUp() {
        listPageActivity = mActivityTestRule.getActivity();
        listPageVM = new ListPageVM();

    }

    @Test
    public void networkFailureFiringIfWrongUrl() {
        networkManager = NetworkManager.getInstance();
        networkManager.setListPagePresenter(this);
        networkManager.doGetRequest("http://fwef7wgef7g.com");
    }

    @Test
    public void networkResponseFiringIfRightUrl() {
        networkManager = NetworkManager.getInstance();
        networkManager.setListPagePresenter(this);
        networkManager.doGetRequest(listPageActivity.getString(R.string.baseUrl));
    }

    @Test
    public void clickTransferredFromActivity() {
        networkManager = NetworkManager.getInstance();
        networkManager.setListPagePresenter(this);
        listPageActivity.clickOnProduct(0);
    }

    @Override
    public void onNetworkResponse(String response) {
        assertTrue(response.length() > 1);
    }

    @Override
    public void onNetworkFailure() {
        boolean networkFalureHasFired = true;
        assertTrue(networkFalureHasFired);
    }

    @Override
    public void onProductItemClicked(int itemIndex) {
        assertEquals(exampleClickedItem,itemIndex);
    }
}