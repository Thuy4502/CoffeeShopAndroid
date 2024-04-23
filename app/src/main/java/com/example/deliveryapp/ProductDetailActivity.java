package com.example.deliveryapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.colormoon.readmoretextview.ReadMoreTextView;
import com.example.deliveryapp.Api.ApiService;
import com.example.deliveryapp.adapter.ProductCustomerAdapter;
import com.example.deliveryapp.model.CartRequest;
import com.example.deliveryapp.model.Category;
import com.example.deliveryapp.model.CommonResponse;
import com.example.deliveryapp.model.OrderRequest;
import com.example.deliveryapp.model.ProAPI;
import com.example.deliveryapp.model.Size;
import com.example.deliveryapp.model.SizeList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    private TextView tvNameDetail, tvPriceDetail;
    private ImageView ivProductImg, ivBack;
    private ProductCustomerAdapter adapter;
    private Button btnBuyNow, btnAddItem, btnSizeS, btnSizeM, btnSizeL;
    private Toolbar appBar;
    public static ProAPI sp;
    public static ProAPI roduct = new ProAPI();
    OrderRequest orderRequest;
    SizeList<Size> allSize;
    public static String size="";
    String categoryName="";
    public static float percent= 0;
    public String category = "";

    float priceBySize;
    String token="eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTM4NDU0OTgsImV4cCI6MTcxNDQ1MDI5OCwidXNlcm5hbWUiOiIwOTI3MDE0MDUxIiwiYXV0aG9yaXRpZXMiOiJDVVNUT01FUiJ9.nzaEjAP5q0ZUyiSIRNJCQtJegz8wS_UgnG8lo89TgJDydUx5zJsAiz-Gnsx7oqUm";

    private ReadMoreTextView tvReadMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_customer_product);
        setControl();
        btnBuyNow.setEnabled(false);
        btnAddItem.setEnabled(false);
        getProductDetail();
        setEvent();
        getAllSize();

    }


    public void setEvent() {
        tvReadMore.setCollapsedText("Read more");
        tvReadMore.setTrimLines(2);


        int cornerRadiusPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                12, // giá trị ban đầu của bán kính ở đơn vị dp
                getResources().getDisplayMetrics()
        );
        GradientDrawable drawableEnable = new GradientDrawable();
        drawableEnable.setShape(GradientDrawable.RECTANGLE);
        drawableEnable.setCornerRadius(cornerRadiusPixels);
        drawableEnable.setColor(Color.parseColor("#b42329"));

        GradientDrawable drawableDisable = new GradientDrawable();
        drawableDisable.setShape(GradientDrawable.RECTANGLE);
        drawableDisable.setCornerRadius(cornerRadiusPixels);
        drawableDisable.setStroke(4, Color.parseColor("#b42329"));
        drawableDisable.setColor(Color.parseColor("#FFFFFF"));


        btnSizeS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBuyNow.setEnabled(true);
                btnAddItem.setEnabled(true);
                size = "S";
                getAllSize();
                btnSizeS.setBackground(drawableEnable);
                btnSizeM.setBackground(drawableDisable);
                btnSizeL.setBackground(drawableDisable);

            }

        });

        btnSizeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBuyNow.setEnabled(true);
                btnAddItem.setEnabled(true);
                size ="M";
                getAllSize();
                btnSizeM.setBackground(drawableEnable);
                btnSizeL.setBackground(drawableDisable);
                btnSizeS.setBackground(drawableDisable);

            }
        });

        btnSizeL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBuyNow.setEnabled(true);
                btnAddItem.setEnabled(true);
                size="L";
                getAllSize();
                btnSizeL.setBackground(drawableEnable);
                btnSizeM.setBackground(drawableDisable);
                btnSizeS.setBackground(drawableDisable);

            }
        });


        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("DAY LA SIZEEE" + size);
                ProAPI sendProduct;
                sendProduct = getProductDetail();
                Intent intent = new Intent(ProductDetailActivity.this, BuyNowActivity.class);
                if (orderRequest != null) {
                    intent.putExtra("buyNow", orderRequest);
                }
                if (priceBySize != 0) {
                    intent.putExtra("priceBySize", priceBySize);
                }

                if (sendProduct != null) {
                    intent.putExtra("product", sendProduct);
                } else {
                    System.out.println("Không có thông tin sản phẩm");
                }
                startActivity(intent);

            }
        });


        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!categoryName.equals("Bánh")) {
                    if(size.equals("S") || size.equals("M") || size.equals("L")) {
                        CartRequest cartRequest = new CartRequest();
                        cartRequest.setProduct_name(sp.getProductName());
                        cartRequest.setSize(size);
                        cartRequest.setTopping("");
                        ProductCustomerAPI.callApiAddCart(cartRequest);
                        Toast.makeText(ProductDetailActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(ProductDetailActivity.this, "Bạn chưa chọn size", Toast.LENGTH_LONG).show();

                    }
                }


            }
        });

        appBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public ProAPI getProductDetail() {
        String id = adapter.productID;
        ApiService.apiService.getProductDetail("Bearer "+token, id).enqueue(new Callback<ProAPI>() {
            @Override
            public void onResponse(@NonNull Call<ProAPI> call, @NonNull Response<ProAPI> response) {
                sp = response.body();
                categoryName = String.valueOf(sp.getCategory().getCategory_name());
                System.out.println("Loại nèeeee" + categoryName);
                tvNameDetail.setText(sp.getProductName());
                tvPriceDetail.setText(UserOrderActivity.formatNumber(sp.getPrice_update_detail().get(0).getPriceNew()));
                tvReadMore.setText(sp.getDescription());
                Glide.with(ProductDetailActivity.this)
                        .load(sp.getImage()) // Lấy URL hình ảnh từ đối tượng ProAPI
                        .into(ivProductImg);

                if (orderRequest == null) {
                    orderRequest = new OrderRequest();
                }
                orderRequest.setProduct_id(sp.getProductId());
                orderRequest.setSize(size);
                orderRequest.setQuantity(1);
                orderRequest.setTopping("");

            }

            @Override
            public void onFailure(Call<ProAPI> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Lỗi lấy thông tin chi tiết sản phẩm", Toast.LENGTH_LONG).show();
            }
        });
        return sp;
    }


    public void getAllSize() {
        ApiService.apiService.getPriceBySize("Bearer " + token).enqueue(new Callback<SizeList<Size>>() {
            @Override
            public void onResponse(Call<SizeList<Size>> call, Response<SizeList<Size>> response) {
                allSize = response.body();
                List<Size> listSize= allSize.getData();
                for(Size i: listSize) {
                    if(i.getCategory().getCategory_name().equals(categoryName) && i.getSize().getSize_name().equals(size)) {
                        percent = i.getPercent();
                        priceBySize = sp.getPrice_update_detail().get(0).getPriceNew() + (percent/100)*sp.getPrice_update_detail().get(0).getPriceNew();
                        tvPriceDetail.setText(String.valueOf(UserOrderActivity.formatNumber(priceBySize)));
                        orderRequest.setPrice(priceBySize);
                    }
                }
            }

            @Override
            public void onFailure(Call<SizeList<Size>> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Lấy tất cả size thất bại", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void setControl() {
        tvReadMore = findViewById(R.id.tvReadMore);
        tvNameDetail = findViewById(R.id.tv_productName);
        tvPriceDetail = findViewById(R.id.tv_productPrice);
        ivProductImg = findViewById(R.id.img_product);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnAddItem = findViewById(R.id.btn_addItem);
        btnSizeS = findViewById(R.id.btn_sizeS);
        btnSizeM = findViewById(R.id.btn_sizeM);
        btnSizeL = findViewById(R.id.btn_sizeL);
        appBar = findViewById(R.id.app_bar);
    }
}
