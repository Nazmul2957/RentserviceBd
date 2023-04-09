package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.my_application.R;

public class Check_Terms_Condition_Screen extends AppCompatActivity {

    CheckBox checkBox;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_terms_condition_screen);

        checkBox=findViewById(R.id.checked);
        button=findViewById(R.id.Next_Page);


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked()){
                    button.setVisibility(View.VISIBLE);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignInScreen.class);
                startActivity(intent);
            }
        });
    }
}