package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpScreen extends AppCompatActivity {

    Button Verification;
    Api api;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);

        PinView pinView = findViewById(R.id.pin_view);
        Verification = findViewById(R.id.verify_otp);

        String ReceiveName = getIntent().getStringExtra("fullname");
        String ReceivePassword = getIntent().getStringExtra("pasPassword");
        String ReceiveMobile = getIntent().getStringExtra("mbl");
        //String SendOtp = MatchOtp.getText().toString();
        Log.e("test", ReceiveName);
        Log.e("test1", ReceivePassword);
        Log.e("test2", ReceiveMobile);
        progressDialog = new ProgressDialog(OtpScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(pinView.getText().toString())) {
                    progressDialog.show();
                    api = RetrofitClient.noInterceptor().create(Api.class);
                    Call<JsonObject> call = api.register(ReceiveName, ReceiveMobile, ReceivePassword, pinView.getText().toString());
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                Log.e("test4", response.body().getAsString());
                                startActivity(new Intent(OtpScreen.this, SignInScreen.class));
                                Toast.makeText(OtpScreen.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(OtpScreen.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Network or Server Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    pinView.setError("Input Valid OTP Number");
                    pinView.requestFocus();
                }
            }
        });


    }
}