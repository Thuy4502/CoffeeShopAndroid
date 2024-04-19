package com.example.deliveryapp.Api;

import com.example.deliveryapp.model.CartListResponse;
import com.example.deliveryapp.model.CartRequest;
import com.example.deliveryapp.model.CommonResponse;
import com.example.deliveryapp.model.OrderRequest;
import com.example.deliveryapp.model.ProAPI;
import com.example.deliveryapp.model.ProductListResponse;
import com.example.deliveryapp.model.Size;
import com.example.deliveryapp.model.SizeList;
import com.example.deliveryapp.model.User;
import com.example.deliveryapp.model.UserInfor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
    public String bearToken = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTMzMjQyMzcsImV4cCI6MTcxMzkyOTAzNywidXNlcm5hbWUiOiIrODQ2MjIyMTAzNSIsImF1dGhvcml0aWVzIjoiU1RBRkYifQ.Jg2FHEYZleqPDMiasvZsaeL-HAo8W6JInddweYsihvoOfBbjFJXxYZdrBIpovf-H";

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.146:9999/").addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);


    @GET("api/products/category?")
    Call<ProductListResponse<ProAPI>> filterProductByCategory(@Header("Authorization") String token, @Query("name") String nameParam);

    @GET("api/product/all")
    Call<ProductListResponse<ProAPI>> getProduct(@Header("Authorization") String token);

    @GET("api/cart/")
    Call<CartListResponse> getAllCart(@Header("Authorization") String token);

    @GET("api/admin/product/find/{productID}")
    Call<ProAPI> getProductDetail(@Header("Authorization") String token, @Path("productID") String productID);

    @PUT("api/cart/add")
    Call<CommonResponse> addToCart(@Header("Authorization") String token, @Body CartRequest cart);

    @PUT("api/cart/reduce/quantity")
    Call<CommonResponse> reduceCart(@Header("Authorization") String token, @Body CartRequest cart);

    @PUT("api/cart/increment/quantity")
    Call<CommonResponse> incrementCart(@Header("Authorization") String token, @Body CartRequest cart);

    @POST("api/cart/delete/item")
    Call<CommonResponse> deleteCart(@Header("Authorization") String token, @Body CartRequest cart);

    @POST("api/order/create")
    Call<Void> addOrder(@Header("Authorization") String token);

    @POST("api/order/buynow")
    Call<CommonResponse> buyNow(@Header("Authorization") String token, @Body OrderRequest orderRequest);

    @POST("api/order/create")
    Call<Void> orderInCart(@Header("Authorization") String token);

    @GET("api/order/size/all")
    Call<SizeList<Size>> getPriceBySize(@Header("Authorization") String token);

    @PUT("api/customer/3/update")
    Call<Void> changeAddress(@Header("Authorization") String token, @Body User user);

    @GET("api/users/profile")
    Call<UserInfor> getUserInfor(@Header("Authorization") String token);





}
