package com.exam.pk.mobiquitest.View.ListPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.exam.pk.mobiquitest.Model.Product;
import com.exam.pk.mobiquitest.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter {

    private Product[] mDataset;
    private Context mContext;




    public class ProductItem extends RecyclerView.ViewHolder{

        @BindView(R.id.product_list_item_image)
        public ImageView vhImage;

        @BindView(R.id.product_list_item_text)
        public TextView vhText;



        public ProductItem(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    public ProductListAdapter(Product[] products, Context context) {
        mDataset = products;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout productListItem = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
        ProductItem vh = new ProductItem(productListItem);

        vh.itemView.setOnClickListener(v -> {

        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       ProductItem productItem = (ProductItem) holder;
       String imageUrl = (mDataset[position].getUrl()).substring(1);
       productItem.vhText.setText(mDataset[position].getName());
       Picasso.get().load(mContext.getString(R.string.baseUrl) + imageUrl).fit().error(R.drawable.missing_image).into(productItem.vhImage);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
