package com.example.my_application.Screens;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.my_application.R;
import com.example.my_application.Util.MySharedPreference;

public class LoginScreen extends AppCompatActivity {
    Button signup, signin;

    boolean doubleBackToExitPressedOnce;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        signup = findViewById(R.id.sign_up);
        signin = findViewById(R.id.sign_in);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, SignUpScreen.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, SignInScreen.class));
            }
        });

    }

    public void onshowquitedailog() {
        builder.setMessage("Do you want to exit").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // doubleBackToExitPressedOnce = true;
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog alert = builder.create();
        alert.setTitle("Close App");
        alert.show();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        // .... other stuff in my onResume ....
//        this.doubleBackToExitPressedOnce = false;
//    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
          //  onshowquitedailog();
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();



    }


}