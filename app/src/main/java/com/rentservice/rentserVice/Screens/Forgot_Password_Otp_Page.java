package com.rentservice.rentserVice.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chaos.view.PinView;
import com.example.my_application.R;
import com.rentservice.rentserVice.Network.Api;

public class Forgot_Password_Otp_Page extends AppCompatActivity {

    Button Verification;
    Api api;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_otp_page);

        PinView pinView = findViewById(R.id.pin_view);
        Verification = findViewById(R.id.verify_otp);

        String ReceiveMobile = getIntent().getStringExtra("mbl");
        Log.d("mobile_print",ReceiveMobile);
        progressDialog = new ProgressDialog(Forgot_Password_Otp_Page.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        Verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String GetOtp=pinView.getText().toString();
                Intent intent=new Intent(getApplicationContext(),Change_Password.class);
                intent.putExtra("mobile_number",ReceiveMobile);
                intent.putExtra("send_otp",GetOtp);
                startActivity(intent);
                finish();

            }
        });

    }
}