package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.my_application.Data_Model.Profile.ProfileContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Pic_Show_Screen extends AppCompatActivity {

    ImageView imageView;
    Api api;
    ProgressDialog progressDialog;
    String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic_show_screen);

        imageView = findViewById(R.id.pro_image_show);

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(Profile_Pic_Show_Screen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();


        api.getprofile(Token).enqueue(new Callback<ProfileContainer>() {
            @Override
            public void onResponse(Call<ProfileContainer> call, Response<ProfileContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Glide.with(getApplicationContext()).load("https://rentservicebd.com/public/api/image/" +
                            response.body().getUserInfo().getAvatar()).into(imageView);
                }


            }

            @Override
            public void onFailure(Call<ProfileContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}