package com.example.my_application.Network;

import com.example.my_application.Data_Model.Category.CategoryContainer;
import com.example.my_application.Data_Model.Dashboard.DashboardContainer;
import com.example.my_application.Data_Model.DistrictModel.DistrictContainer;
import com.example.my_application.Data_Model.Division.DivisionContainer;
import com.example.my_application.Data_Model.Favourite.FavouriteContainer;
import com.example.my_application.Data_Model.InsertPost.InsertPostResponseContainer;
import com.example.my_application.Data_Model.Mypost.MypostListContainer;
import com.example.my_application.Data_Model.PoliceStation.PoliceStationContainer;
import com.example.my_application.Data_Model.Profile.ProfileContainer;
import com.example.my_application.Data_Model.SinglePost.SinglePostContainer;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<JsonObject> login(@Field("mobile") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("otp")
    Call<JsonObject> Otp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("favourite")
    Call<JsonObject> insertfavourite(@Field("key") String Key,
                                     @Field("postId") String PostId, @Field("userId") String UserId);@FormUrlEncoded
    @POST("favourite")
    Call<FavouriteContainer> favouritelist(@Field("key") String Key);

    @FormUrlEncoded
    @POST("register")
    Call<JsonObject> register(@Field("name") String name,
                              @Field("mobile") String mobile,
                              @Field("password") String Password,
                              @Field("otp") String Otp);

//    @FormUrlEncoded
//    @POST("post")
//    Call<JsonObject> insertPost(@Field("title") String Title,
//                                @Field("description") String Description,
//                                @Field("price") String Price,
//                                @Field("address") String Address,
//                                @Field("divisionId") String Division,
//                                @Field("districtId") String District,
//                                @Field("policeStationId") String Police,
//                                @Field("key") String Token);

//    @Multipart
//    @POST("post")
//    Call<InsertPostResponseContainer> InsertPost(@Part("title") RequestBody Title,
//                                                 @Part("description") RequestBody Description,
//                                                 @Part("price") RequestBody Price,
//                                                 @Part("address") RequestBody Address,
//                                                 @Part("divisionId") RequestBody Division,
//                                                 @Part("districtId") RequestBody District,
//                                                 @Part("policeStationId") RequestBody Police,
//                                                 @Part("key") RequestBody Token,
//                                                 @Part MultipartBody.Part image1
//    );

    @POST("post")
    Call<InsertPostResponseContainer> InsertPost(@Body RequestBody params);

//    @Part MultipartBody.Part image3

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
