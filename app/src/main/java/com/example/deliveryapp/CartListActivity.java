package com.example.deliveryapp;

import static com.example.deliveryapp.ProductCustomerAPI.countCart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryapp.Api.ApiService;
import com.example.deliveryapp.adapter.CartAdapter;
import com.example.deliveryapp.model.Cart;
import com.example.deliveryapp.model.CartListResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListActivity extends AppCompatActivity {

    private static RecyclerView rvCart;
    private Button btnShowCart;
    private static Button btnBuy;
    static CartAdapter cartAdapter;
    private static TextView tvTotalPrice;
    private ImageView ivBack;

    public static List<Cart> cartList;

    public static float totalPrice;

//    public static int countCart;
    static String token = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTMxNTM1MTEsImV4cCI6MTcxMzc1ODMxMSwidXNlcm5hbWUiOiIwOTI3MDE0MDUxIiwiYXV0aG9yaXRpZXMiOiJDVVNUT01FUiJ9.BuqNsRGHaEhsRv0L4-Xn9aM4hXmfNVAoN4Di4Na7lDmiaygfRFCUB0EA-S-B45oW";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layour_cart);
        setControl();
        rvCart.setHasFixedSize(true);
        rvCart.setLayoutManager(new GridLayoutManager(this, 1));
        callApiGetAllCart();
        setEvent();
        System.out.println("Thành công");

    }

    public static void setButtonBuy(int countCart) {
        if(ProductCustomerAPI.countCart == 0) {
            btnBuy.setEnabled(false);
        }
        else {
            btnBuy.setEnabled(true);
        }
    }


    public void setControl() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCart = findViewById(R.id.rvCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnBuy = findViewById(R.id.btnBuyInCart);
        ivBack = findViewById(R.id.ivBack);

    }

    public void setEvent() {
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCartList();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void getCartList() {
        Intent intent = new Intent(CartListActivity.this, UserOrderActivity.class);
        startActivity(intent);
    }


    public static void callApiGetAllCart() {
        ApiService.apiService.getAllCart("Bearer "+token).enqueue(new Callback<CartListResponse>() {
            @Override
            public void onResponse(@NonNull Call<CartListResponse> call, @NonNull Response<CartListResponse> response) {
                CartListResponse list = new CartListResponse();
                list = response.body();
                if (list != null && list.getData() != null && list.getData().getCart_detail() != null) {
                    cartList = list.getData().getCart_detail();
                    cartAdapter = new CartAdapter(cartList);
                    rvCart.setAdapter(cartAdapter);
                    totalPrice = list.getData().getTotal_price();
                    tvTotalPrice.setText(UserOrderActivity.formatNumber(totalPrice));
                    cartAdapter.updateTotalPrice(totalPrice);
                    cartAdapter.notifyDataSetChanged();
                    countCart = cartList.size();
                    setButtonBuy(countCart);

                } else {
                    System.out.println("Gio hang rong");
                    GradientDrawable drawableEnable = new GradientDrawable();
                    drawableEnable.setShape(GradientDrawable.RECTANGLE);
                    drawableEnable.setCornerRadius(54);
                }
            }

            @Override
            public void onFailure(Call<CartListResponse> call, Throwable t) {
                System.out.println("-----------------------Lỗi truy cập giỏ hàng");
            }
        });
    }

    public static void setTotalPrice(float price) {
        tvTotalPrice.setText(UserOrderActivity.formatNumber(price));
    }


    public static float getTotalPrice() {
        return totalPrice;
    }
}
