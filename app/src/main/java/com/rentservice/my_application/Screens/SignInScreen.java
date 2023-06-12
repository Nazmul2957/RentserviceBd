package com.rentservice.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rentservice.my_application.Network.Api;
import com.rentservice.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.my_application.Util.Constant;
import com.rentservice.my_application.Util.MySharedPreference;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInScreen extends AppCompatActivity {

    EditText Mobile_num, Password;
    Button SignIn;
    TextView Goto_Signup,Forgot;
    Api api;
    ProgressDialog progressDialog;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        Mobile_num = findViewById(R.id.mobile_number);
        Password = findViewById(R.id.password_match);
        SignIn = findViewById(R.id.sign_ip);
        Goto_Signup = findViewById(R.id.new_registar);
        Forgot=findViewById(R.id.forget_password);



        String Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");
        if (!Token.equals(new String("not found"))) {
            startActivity(new Intent(SignInScreen.this, DashBoardScreen.class));
            finish();
        }
        progressDialog = new ProgressDialog(SignInScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInScreen.this,Forgot_Password_Page_One.class));
                finish();
            }
        });

        Goto_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInScreen.this, SignUpScreen.class));
                finish();
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Mobile_num.getText().toString())) {
                    if (!TextUtils.isEmpty(Password.getText().toString())) {
                        progressDialog.show();
                        api = RetrofitClient.noInterceptor().create(Api.class);
                        Call<JsonObject> call = api.login(("88" + Mobile_num.getText().toString()),
                                Password.getText().toString());

                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful() && response.body() != null) {
                                    token = response.body().get("key").getAsString();
                                    MySharedPreference.getInstance(getApplicationContext()).edit()
                                            .putString(Constant.TOKEN, token).apply();
                                    String message = response.body().get("message").toString();
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignInScreen.this, DashBoardScreen.class));
                                    finish();

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignInScreen.this, "Something Wrong", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Password.setError("input valid password");
                        Password.requestFocus();
                    }

                } else {
                    Mobile_num.setError("Input Valid Mobile Number");
                    Mobile_num.requestFocus();
                }
            }
        });

    }
}