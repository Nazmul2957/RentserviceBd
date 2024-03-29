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

import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;
import com.example.my_application.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpScreen extends AppCompatActivity {

    Button GotoOtpPage;
    EditText Name, Phone_Number, Pasword, Confrim_Password, Reg_Address;
    ProgressDialog progressDialog;
    Api api;
    String PasswordMatch;
    String ConfrimPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        GotoOtpPage = findViewById(R.id.sign_up);
        Name = findViewById(R.id.took_name);
        Phone_Number = findViewById(R.id.phn_no);
        Pasword = findViewById(R.id.took_password);
        Confrim_Password = findViewById(R.id.took_confirm_password);
        Reg_Address = findViewById(R.id.reg_address);

        progressDialog = new ProgressDialog(SignUpScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        PasswordMatch = Pasword.getText().toString();
        ConfrimPassword = Confrim_Password.getText().toString();


        GotoOtpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Name.getText().toString())) {
                    if (!TextUtils.isEmpty(Phone_Number.getText().toString())) {
                        if (!TextUtils.isEmpty(Reg_Address.getText().toString())) {
                            if (!TextUtils.isEmpty(Pasword.getText().toString())) {
                                if (!TextUtils.isEmpty(Confrim_Password.getText().toString())) {
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
                                                String mobile = "88" + Phone_Number.getText().toString();
                                                String Address = Reg_Address.getText().toString();
                                                Intent intent = new Intent(SignUpScreen.this, Check_Terms_Condition_Screen.class);
                                                intent.putExtra("fullname", passname);
                                                intent.putExtra("pasPassword", passingPassword);
                                                intent.putExtra("mbl", mobile);
                                                intent.putExtra("addres", Address);
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
                                    Confrim_Password.setError("Password Not matching");
                                    Confrim_Password.requestFocus();

                                }

                            } else {
                                Pasword.setError("Input Password");
                                Pasword.requestFocus();
                            }

                        } else {
                            Reg_Address.setError("Input Your Address");
                            Reg_Address.requestFocus();
                        }

                    } else {
                        Phone_Number.setError("Input Valid Mobile Number");
                        Phone_Number.requestFocus();
                    }
                } else {
                    Name.setError("Input Your Name");
                    Name.requestFocus();
                }


            }
        });
    }
}