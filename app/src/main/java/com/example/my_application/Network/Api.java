package com.example.my_application.Network;

import com.example.my_application.Data_Model.Dashboard.DashboardContainer;
import com.example.my_application.Data_Model.DistrictModel.DistrictContainer;
import com.example.my_application.Data_Model.Division.DivisionContainer;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<JsonObject> login(@Field("mobile") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("otp")
    Call<JsonObject> Otp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("register")
    Call<JsonObject> register(@Field("name") String name, @Field("mobile") String mobile, @Field("password") String Password,
                              @Field("otp") String Otp);

    @GET("post")
    Call<DashboardContainer> getdashboarddata();

    @GET("division")
    Call<DivisionContainer> getDivision();

    @GET("district")
    Call<DistrictContainer> getDistrict();
}
