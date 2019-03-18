package com.exam.pk.mobiquitest;

public interface IListPageVM {
    void onNetworkResponse(String response);
    void onNetworkFailure();
    void onProductItemClicked(int itemIndex);
}
