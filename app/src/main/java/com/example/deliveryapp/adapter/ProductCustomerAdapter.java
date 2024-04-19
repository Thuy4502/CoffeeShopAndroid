package com.example.deliveryapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliveryapp.ProductCustomerAPI;
import com.example.deliveryapp.ProductDetailActivity;
import com.example.deliveryapp.R;
import com.example.deliveryapp.UserOrderActivity;
import com.example.deliveryapp.model.CartRequest;
import com.example.deliveryapp.model.PriceUpdateDetail;
import com.example.deliveryapp.model.ProAPI;

import java.util.List;


public class ProductCustomerAdapter extends RecyclerView.Adapter<ProductCustomerAdapter.ProductCustomerHolder> {

    private List<ProAPI> listProduct;
    private static Context context;
    public static String productID;
    public static CartRequest cartRequest = new CartRequest();
    public static ProAPI sanPham;



    public void setFilteredList(List<ProAPI> filteredList) {
        this.listProduct = filteredList;
        notifyDataSetChanged();
    }

    public ProductCustomerAdapter(List<ProAPI> listProduct) {

        this.listProduct = listProduct;
    }


    @NonNull
    @Override
    public ProductCustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_customer, parent, false);
        context = parent.getContext();
        ProductCustomerHolder productCustomerHolder = new ProductCustomerHolder(view);
        return productCustomerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCustomerHolder holder, int position) {
        ProAPI sp = listProduct.get(position);
        if(sp == null) {
            return;
        }
        holder.txtProductName.setText(sp.getProductName());
//        holder.position = position;
        holder.product = sp;
        List<PriceUpdateDetail> priceUpdateDetailList = sp.getPrice_update_detail();


        if (priceUpdateDetailList != null && !priceUpdateDetailList.isEmpty()) {
            StringBuilder priceText = new StringBuilder();
            for (PriceUpdateDetail priceUpdateDetail : priceUpdateDetailList) {
                priceText.append(String.valueOf(priceUpdateDetail.getPriceNew())).append(", ");
            }
            // Loại bỏ dấu phẩy cuối cùng
            if (priceText.length() > 0) {
                priceText.deleteCharAt(priceText.length() - 2);
            }
            holder.txtProductPrice.setText(UserOrderActivity.formatNumber(Float.parseFloat(String.valueOf(priceText))));
        } else {
            holder.txtProductPrice.setText("N/A");
        }


        if(sp.getImage() != null && !sp.getImage().isEmpty()) {
            Glide.with(context)
                    .load(sp.getImage())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.icon_caphe)
                    .into(holder.ivAnhSp);
        }
        else {
            holder.ivAnhSp.setImageResource(R.drawable.placeholder_image);
        }


    }

    @Override
    public int getItemCount() {
        if(listProduct != null) {
            return listProduct.size();
        }
        return 0;
    }


    public static class ProductCustomerHolder extends RecyclerView.ViewHolder{
        private final TextView txtProductName;
        public TextView btnAddItem;
        private TextView txtProductPrice;
        private ImageView ivAnhSp;
        int position;
        View rootView;
        ProAPI product;
        public ProductCustomerHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            ivAnhSp = itemView.findViewById(R.id.ivAnhSp);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            btnAddItem = itemView.findViewById(R.id.btnAddItem);
            rootView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    productID = product.getProductId();
                    context.startActivity(intent);
                }
            });

            btnAddItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!product.isAddToCart()) {
                        cartRequest.setProduct_name(product.getProductName());
                        cartRequest.setSize("M");
                        cartRequest.setTopping("");
                        ProductCustomerAPI.callApiAddCart(cartRequest);
                        product.setAddToCart(true);
                        sanPham = product;
                    }


                }
            });



        }

    }
}
