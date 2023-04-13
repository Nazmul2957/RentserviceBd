package com.example.my_application.Network;

import com.example.my_application.Data_Model.Category.CategoryContainer;
import com.example.my_application.Data_Model.CommentList.CommentContainer;
import com.example.my_application.Data_Model.Dashboard.DashboardContainer;
import com.example.my_application.Data_Model.DistrictModel.DistrictContainer;
import com.example.my_application.Data_Model.DistrictshowbyDivision.DistrictshowContainer;
import com.example.my_application.Data_Model.Division.DivisionContainer;
import com.example.my_application.Data_Model.Favourite.FavouriteContainer;
import com.example.my_application.Data_Model.InsertPost.InsertPostResponseContainer;
import com.example.my_application.Data_Model.Mypost.MypostListContainer;
import com.example.my_application.Data_Model.PoliceStation.PoliceStationContainer;
import com.example.my_application.Data_Model.PoliceStationByDistrict.PolicestationByDistrictContainer;
import com.example.my_application.Data_Model.Profile.ProfileContainer;
import com.example.my_application.Data_Model.Profile.ProfilePicContainer;
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
                                     @Field("postId") String PostId);

    @FormUrlEncoded
    @POST("favourite/delete")
    Call<JsonObject> deletefavourite(@Field("key") String Key,
                                     @Field("postId") String PostId);

    @FormUrlEncoded
    @POST("favourite/get")
    Call<FavouriteContainer> favouritelist(@Field("key") String Key);

    @FormUrlEncoded
    @POST("register")
    Call<JsonObject> register(@Field("name") String name,
                              @Field("mobile") String mobile,
                              @Field("password") String Password,
                              @Field("otp") String Otp,
                              @Field("address") String Address);


    @POST("post")
    Call<InsertPostResponseContainer> InsertPost(@Body RequestBody params);

    @POST("user/image")
    Call<ProfilePicContainer> ProfilePicUpload(@Body RequestBody params);

    @POST("profile/update")
    Call<ProfilePicContainer> ProfileEdit(@Body RequestBody params);


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

    @GET("district/{id}")
    Call<DistrictshowContainer> getDistrictbydivision(@Path("id") String id);

    @GET("policeStation")
    Call<PoliceStationContainer> getpolice();

    @GET("policeStation/{id}")
    Call<PolicestationByDistrictContainer> getpolicebydistrict(@Path("id") String id);

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

    @FormUrlEncoded
    @POST("policeStation")
    Call<JsonObject> insertpolicestation(@Field("name") String Name, @Field("divisionId") String DivisionId,
                                         @Field("districtId") String DistrictId);

    @GET("post/{id}")
    Call<SinglePostContainer> getsinglepost(@Path("id") String ID);

    @GET("comment/{id}")
    Call<CommentContainer> getallComments(@Path("id") String Postid);

    @GET("post/delete/{id}")
    Call<JsonObject> deletemypost(@Path("id") String Postid);

    @FormUrlEncoded
    @POST("comment")
    Call<JsonObject> insert_comments(@Field("postId") String PId, @Field("text") String Text, @Field("key") String Key);

}
