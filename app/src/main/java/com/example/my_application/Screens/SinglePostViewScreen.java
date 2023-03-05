package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.my_application.Data_Model.Profile.ProfileContainer;
import com.example.my_application.Data_Model.SinglePost.SinglePostContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePostViewScreen extends AppCompatActivity {
    Api api;
    ProgressDialog progressDialog;
    TextView textView, Price, Post_Description;
    ImageView imageView;
    Button CallButton;
    String PhoneNumber;
    //String REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post_view_screen);

        textView = findViewById(R.id.text_show);
        imageView = findViewById(R.id.post_image);
        Price = findViewById(R.id.price_post);
        Post_Description = findViewById(R.id.post_description);
        CallButton = findViewById(R.id.call_button);
        String PostId = getIntent().getStringExtra(Intent.EXTRA_UID);
        Log.e("pppp", PostId);

        api = RetrofitClient.difBaseUrle().create(Api.class);
        String Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(SinglePostViewScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();


        api.getsinglepost(PostId).enqueue(new Callback<SinglePostContainer>() {
            @Override
            public void onResponse(Call<SinglePostContainer> call, Response<SinglePostContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Glide.with(getApplicationContext()).load("https://rentservicebd.com/public/api/image/" +
                            response.body().getPost().getImage1()).into(imageView);
                    textView.setText(response.body().getPost().getTitle());
                    Price.setText(response.body().getPost().getPrice());
                    Post_Description.setText(response.body().getPost().getDescription());
                    // Toast.makeText(getApplicationContext(), "Post Successfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SinglePostContainer> call, Throwable t) {

            }
        });

        api.getprofile(Token).enqueue(new Callback<ProfileContainer>() {
            @Override
            public void onResponse(Call<ProfileContainer> call, Response<ProfileContainer> response) {
                PhoneNumber = String.valueOf(response.body().getUserInfo().getMobile());
                // CallButton.setText(PhoneNumber);

                CallButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("phone_number", PhoneNumber);
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + PhoneNumber));
                        startActivity(intent);
                    }
                });
                // Log.d("phone_number",PhoneNumber);

            }

            @Override
            public void onFailure(Call<ProfileContainer> call, Throwable t) {

            }
        });

    }
}