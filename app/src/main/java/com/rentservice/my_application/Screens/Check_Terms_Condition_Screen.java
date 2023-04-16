package com.rentservice.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.rentservice.my_application.Network.Api;
import com.example.my_application.R;

public class Check_Terms_Condition_Screen extends AppCompatActivity {

    CheckBox checkBox;
    Button button;
    ProgressDialog progressDialog;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_terms_condition_screen);

        checkBox=findViewById(R.id.checked);
        button=findViewById(R.id.Next_Page);

        String ReceiveName = getIntent().getStringExtra("fullname");
        String ReceivePassword = getIntent().getStringExtra("pasPassword");
        String ReceiveMobile = getIntent().getStringExtra("mbl");
        String ReceiveAddress=getIntent().getStringExtra("addres");



        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    button.setVisibility(View.VISIBLE);
                }
                else{
                    button.setVisibility(View.GONE);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),OtpScreen.class);
                intent.putExtra("fullname", ReceiveName);
                intent.putExtra("pasPassword", ReceivePassword);
                intent.putExtra("mbl", ReceiveMobile);
                intent.putExtra("addres", ReceiveAddress);
                startActivity(intent);
                finish();
            }
        });







    }
}