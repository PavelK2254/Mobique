package com.exam.pk.mobiquitest.View.ListPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.exam.pk.mobiquitest.ListPageVM;
import com.exam.pk.mobiquitest.Model.Product;
import com.exam.pk.mobiquitest.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PageFragment extends Fragment {

    @BindView(R.id.productList)
    RecyclerView mProductList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        Product[] currentProducts = (Product[]) args.getSerializable("products");
        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.page,container,false);
        ButterKnife.bind(this,rootView);
        setupProductList(mProductList,currentProducts);
        return rootView;
    }

    private void setupProductList(RecyclerView productList, Product[] products){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        RecyclerView.Adapter adapter = new ProductListAdapter(products,getContext(), v -> {
            ((ListPageActivity)getActivity()).clickOnProduct((Integer)v.getTag());
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(productList.getContext(),DividerItemDecoration.VERTICAL);
        productList.addItemDecoration(dividerItemDecoration);
        productList.setHasFixedSize(false);
        productList.setLayoutManager(layoutManager);
        productList.setAdapter(adapter);

    }
}
