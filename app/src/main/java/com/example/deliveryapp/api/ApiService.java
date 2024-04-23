package com.example.deliveryapp.api;

import com.example.deliveryapp.model.FullCart;
import com.example.deliveryapp.model.response.SingleResponse;
import com.example.deliveryapp.model.request.CartRequest;
import com.example.deliveryapp.model.response.RequestResponse;
import com.example.deliveryapp.model.request.OrderRequest;
import com.example.deliveryapp.model.Product;
import com.example.deliveryapp.model.response.CommonResponse;
import com.example.deliveryapp.model.Size;
import com.example.deliveryapp.model.User;
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
    public String bearToken = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTM4NDU0OTgsImV4cCI6MTcxNDQ1MDI5OCwidXNlcm5hbWUiOiIwOTI3MDE0MDUxIiwiYXV0aG9yaXRpZXMiOiJDVVNUT01FUiJ9.nzaEjAP5q0ZUyiSIRNJCQtJegz8wS_UgnG8lo89TgJDydUx5zJsAiz-Gnsx7oqUm";

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.10.1.36:9999/").addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);


    @GET("api/products/category?")
    Call<CommonResponse<Product>> filterProductByCategory(@Header("Authorization") String token, @Query("name") String nameParam);

    @GET("api/product/all")
    Call<CommonResponse<Product>> getProduct(@Header("Authorization") String token);

    @GET("api/cart/")
    Call<SingleResponse<FullCart>> getAllCart(@Header("Authorization") String token);

    @GET("api/admin/product/find/{productID}")
    Call<Product> getProductDetail(@Header("Authorization") String token, @Path("productID") String productID);

    @PUT("api/cart/add")
    Call<RequestResponse> addToCart(@Header("Authorization") String token, @Body CartRequest cart);

    @PUT("api/cart/reduce/quantity")
    Call<RequestResponse> reduceCart(@Header("Authorization") String token, @Body CartRequest cart);

    @PUT("api/cart/increment/quantity")
    Call<RequestResponse> incrementCart(@Header("Authorization") String token, @Body CartRequest cart);

    @POST("api/cart/delete/item")
    Call<RequestResponse> deleteCart(@Header("Authorization") String token, @Body CartRequest cart);

    @POST("api/order/create")
    Call<Void> addOrder(@Header("Authorization") String token);

    @POST("api/order/buynow")
    Call<RequestResponse> buyNow(@Header("Authorization") String token, @Body OrderRequest orderRequest);

    @POST("api/order/create")
    Call<Void> orderInCart(@Header("Authorization") String token);

    @GET("api/order/size/all")
    Call<CommonResponse<Size>> getPriceBySize(@Header("Authorization") String token);

    @PUT("api/customer/3/update")
    Call<Void> changeAddress(@Header("Authorization") String token, @Body User user);

    @GET("api/users/profile")
    Call<SingleResponse<User>> getUserInfor(@Header("Authorization") String token);





}
