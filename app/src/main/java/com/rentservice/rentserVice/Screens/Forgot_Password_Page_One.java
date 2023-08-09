package com.rentservice.rentserVice.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_application.R;
import com.google.gson.JsonObject;
import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot_Password_Page_One extends AppCompatActivity {

    EditText  Phone_Number;
    Button GetOtp;
    ProgressDialog progressDialog;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page_one);

        Phone_Number=findViewById(R.id.Phn_no);
        GetOtp=findViewById(R.id.get_otp);

        progressDialog = new ProgressDialog(Forgot_Password_Page_One.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        GetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Phone_Number.getText().toString())){
                    progressDialog.show();
                    api = RetrofitClient.noInterceptor().create(Api.class);
                    Call<JsonObject> call = api.Otp(("88" + Phone_Number.getText().toString())
                    );
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                String mobile = "88" + Phone_Number.getText().toString();
                                Intent intent = new Intent(Forgot_Password_Page_One.this, Forgot_Password_Otp_Page.class);
                                intent.putExtra("mbl", mobile);
                                // startActivity(new Intent(SignUpScreen.this, OtpScreen.class));
                                startActivity(intent);
                                finish();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Forgot_Password_Page_One.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Network or Server Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Phone_Number.setError("Enter Your Mobile Number");
                    Phone_Number.requestFocus();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),SignInScreen.class));
        finish();
    }
}