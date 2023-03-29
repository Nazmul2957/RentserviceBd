package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpScreen extends AppCompatActivity {

    Button GotoOtpPage;
    EditText Name, Phone_Number, Pasword, Confrim_Password;
    ProgressDialog progressDialog;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        GotoOtpPage = findViewById(R.id.sign_up);
        Name = findViewById(R.id.took_name);
        Phone_Number = findViewById(R.id.phn_no);
        Pasword = findViewById(R.id.took_password);
        Confrim_Password = findViewById(R.id.took_confirm_password);

        progressDialog = new ProgressDialog(SignUpScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);


        GotoOtpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Phone_Number.getText().toString())) {
                    progressDialog.show();
                    api = RetrofitClient.noInterceptor().create(Api.class);
                    Call<JsonObject> call = api.Otp(("88" + Phone_Number.getText().toString())
                    );
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful() && response.body() != null) {
                                String passname = Name.getText().toString();
                                String passingPassword = Pasword.getText().toString();
                                String passConfrimPassword = Confrim_Password.getText().toString();
                                String mobile = "88" + Phone_Number.getText().toString();

                                Intent intent = new Intent(SignUpScreen.this, OtpScreen.class);
                                intent.putExtra("fullname", passname);
                                intent.putExtra("pasPassword", passingPassword);
                                intent.putExtra("mbl", mobile);
                                // startActivity(new Intent(SignUpScreen.this, OtpScreen.class));
                                startActivity(intent);
                                finish();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(SignUpScreen.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Network or Server Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Phone_Number.setError("Input Valid Mobile Number");
                    Phone_Number.requestFocus();
                }


            }
        });
    }
}