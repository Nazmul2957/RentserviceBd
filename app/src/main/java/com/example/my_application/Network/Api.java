package com.example.my_application.Network;

import com.example.my_application.Data_Model.Category.CategoryContainer;
import com.example.my_application.Data_Model.Dashboard.DashboardContainer;
import com.example.my_application.Data_Model.DistrictModel.DistrictContainer;
import com.example.my_application.Data_Model.Division.DivisionContainer;
import com.example.my_application.Data_Model.Mypost.MypostListContainer;
import com.example.my_application.Data_Model.PoliceStation.PoliceStationContainer;
import com.example.my_application.Data_Model.Profile.ProfileContainer;
import com.example.my_application.Data_Model.SinglePost.SinglePostContainer;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @FormUrlEncoded
    @POST("post")
    Call<JsonObject> insertPost(@Field("title") String Title, @Field("description") String Description, @Field("price") String Price,
                                @Field("address") String Address, @Field("divisionId") String Division,
                                @Field("districtId") String District,
                                @Field("policeStationId") String Police, @Field("key") String Token);

    @FormUrlEncoded
    @POST("user/get")
    Call<ProfileContainer> getprofile(@Field("key") String key);

    @FormUrlEncoded
    @POST("post/user")
    Call<MypostListContainer> getuserpost(@Field("key") String key);

    @GET("post")
    Call<DashboardContainer> getdashboarddata();

    @GET("division")
    Call<DivisionContainer> getDivision();

    @GET("district")
    Call<DistrictContainer> getDistrict();

    @GET("policeStation")
    Call<PoliceStationContainer> getpolice();

    @GET("category/get_all")
    Call<CategoryContainer> getcat();

    @FormUrlEncoded
    @POST("category")
    Call<JsonObject> insertcategory(@Field("name") String Name);

    @FormUrlEncoded
    @POST("division")
    Call<JsonObject> insertdivision(@Field("name") String Name);

    @FormUrlEncoded
    @POST("district")
    Call<JsonObject> insertDistrict(@Field("name") String Name, @Field("divisionId") String DivisionId);

    @GET("post/{id}")
    Call<SinglePostContainer> getsinglepost(@Path("id") String ID);

}
