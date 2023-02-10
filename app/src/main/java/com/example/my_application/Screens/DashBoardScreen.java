package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.my_application.R;
import com.example.my_application.Util.MySharedPreference;

public class DashBoardScreen extends AppCompatActivity {

    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_screen);
        Logout=findViewById(R.id.logout);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreference.editor(getApplicationContext()).clear().commit();
                startActivity(new Intent(DashBoardScreen.this, LoginScreen.class));
                finish();

            }
        });
    }
}