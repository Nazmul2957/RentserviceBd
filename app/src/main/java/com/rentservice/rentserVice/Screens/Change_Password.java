package com.rentservice.rentserVice.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class Change_Password extends AppCompatActivity {

    EditText Password, ConfrimPaswword;
    Button Save;
    ProgressDialog progressDialog;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Password = findViewById(R.id.took_passwords);
        ConfrimPaswword = findViewById(R.id.took_confirm_passwords);
        Save = findViewById(R.id.Confirms);


        progressDialog = new ProgressDialog(Change_Password.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);


        String ReceiveMobiles = getIntent().getStringExtra("mobile_number");
        String ReceiveOtps = getIntent().getStringExtra("send_otp");

        Log.d("mobil", ReceiveMobiles);
        Log.d("gear", ReceiveOtps);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Password.getText().toString())) {
                    if (!TextUtils.isEmpty(ConfrimPaswword.getText().toString())) {
                        if (!Password.getText().toString().equals(ConfrimPaswword.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Confirm password is not correct", Toast.LENGTH_LONG).show();
                        } else {
                            progressDialog.show();
                            api = RetrofitClient.noInterceptor().create(Api.class);
                            api.forgotpassword(ReceiveMobiles, ConfrimPaswword.getText().toString(), ReceiveOtps).enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    progressDialog.dismiss();
                                    if (response.isSuccessful() && response.body() != null) {
                                        startActivity(new Intent(getApplicationContext(), SignInScreen.class));
                                        Toast.makeText(getApplicationContext(), "Password Change Successful", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Network or Server Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        ConfrimPaswword.setError("Password did't match");
                        ConfrimPaswword.requestFocus();
                    }

                } else {
                    Password.setError("Enter Your New Password");
                    Password.requestFocus();
                }

            }
        });
    }
}