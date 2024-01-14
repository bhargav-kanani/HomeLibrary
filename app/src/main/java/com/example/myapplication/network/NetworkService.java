package com.example.myapplication.network;

import com.example.myapplication.models.BooksResponseModel;
import com.example.myapplication.models.CategoryResponseModel;
import com.example.myapplication.models.LoginResponseModel;
import com.example.myapplication.models.OrderResponseModel;
import com.example.myapplication.models.PlaceOrderResponse;
import com.example.myapplication.models.RegistrationResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetworkService {

    @POST("categories.php")
    Call<CategoryResponseModel> getCategoriesFromServer();

    @FormUrlEncoded
    @POST("books.php")
    Call<BooksResponseModel> getBooksByCategories(@Field("category") String category);

    @FormUrlEncoded
    @POST("registration.php")
    Call<RegistrationResponseModel> register(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponseModel> login (@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("place_order.php")
    Call<PlaceOrderResponse> placeOrder(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("order_history.php")
    Call<OrderResponseModel> getOrders(@Field("email") String email);
}
