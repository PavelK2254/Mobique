package com.exam.pk.mobiquitest.View.DetailPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.exam.pk.mobiquitest.ListPageVM;
import com.exam.pk.mobiquitest.Model.Product;
import com.exam.pk.mobiquitest.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment {

    @BindView(R.id.detail_page_image)
    ImageView mDetailImageView;

    @BindView(R.id.detail_product_name)
    TextView mProductName;

    @BindView(R.id.detail_product_price)
    TextView mProductPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListPageVM listPageVM =  ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ListPageVM.class);
        Product mCurrentProduct = listPageVM.getCurrentProduct();
        FrameLayout root = (FrameLayout) inflater.inflate(R.layout.detail_page,container,false);
        String imageUrl;
        ButterKnife.bind(this,root);
        if(mCurrentProduct != null){
            mProductName.setText(mCurrentProduct.getName());
            mProductPrice.setText(String.format("%s %s", mCurrentProduct.getSalePrice().getAmount(), mCurrentProduct.getSalePrice().getCurrency()));
            imageUrl = mCurrentProduct.getUrl().substring(1);
        }else{
            imageUrl = "unAvailable";
        }


        Picasso.get().load(Objects.requireNonNull(getContext()).getString(R.string.baseUrl) + imageUrl).error(R.drawable.missing_image).into(mDetailImageView);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
