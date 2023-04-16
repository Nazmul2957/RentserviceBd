package com.rentservice.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.rentservice.my_application.Data_Model.SinglePost.SinglePostContainer;
import com.rentservice.my_application.Network.Api;
import com.rentservice.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.my_application.Util.Constant;
import com.rentservice.my_application.Util.MySharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Image_Preview extends AppCompatActivity {

    ImageSlider imageSlider;
    Api api;
    ProgressDialog progressDialog;
    String PostId;
    String Token;

    ArrayList<SlideModel> slideImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        imageSlider=findViewById(R.id.big_image);
        PostId = getIntent().getStringExtra(Intent.EXTRA_UID);

        api = RetrofitClient.difBaseUrle().create(Api.class);
        Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(Image_Preview.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();
        api.getsinglepost(PostId).enqueue(new Callback<SinglePostContainer>() {
            @Override
            public void onResponse(Call<SinglePostContainer> call, Response<SinglePostContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {

                    if (String.valueOf(response.body().getPost().getImage1()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage1()), ScaleTypes.CENTER_INSIDE));
                    }
                    if (String.valueOf(response.body().getPost().getImage2()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage2()), ScaleTypes.CENTER_INSIDE));

                    }
                    if (String.valueOf(response.body().getPost().getImage3()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage3()), ScaleTypes.CENTER_INSIDE));

                    }
                    if (String.valueOf(response.body().getPost().getImage4()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage4()), ScaleTypes.CENTER_INSIDE));
                    }
                    if (String.valueOf(response.body().getPost().getImage5()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage5()), ScaleTypes.CENTER_INSIDE));

                    }

                    imageSlider.setImageList(slideImage);
                    imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);
                    Log.d("slider", String.valueOf(slideImage));




                    // Toast.makeText(getApplicationContext(), "Post Successfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SinglePostContainer> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}