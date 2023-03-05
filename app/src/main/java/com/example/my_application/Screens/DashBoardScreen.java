package com.example.my_application.Screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.my_application.Adaptar.Dashboard_adaptar;
import com.example.my_application.Data_Model.Dashboard.DashboardContainer;
import com.example.my_application.Data_Model.Profile.ProfileContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardScreen extends AppCompatActivity {

    Api api;
    NavigationView nav;
    RecyclerView recyclerView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView Username;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_screen);


        recyclerView = findViewById(R.id.dash_board_list);
        nav = (NavigationView) findViewById(R.id.navbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        Username = findViewById(R.id.username);

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        String Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(DashBoardScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();


        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navbar();

       // Log.d("token_print", Token);

        api.getprofile(Token).enqueue(new Callback<ProfileContainer>() {
            @Override
            public void onResponse(Call<ProfileContainer> call, Response<ProfileContainer> response) {
                Username.setText(response.body().getUserInfo().getName());
                Log.d("profile", response.body().getUserInfo().getName());
            }

            @Override
            public void onFailure(Call<ProfileContainer> call, Throwable t) {

            }
        });

        api.getdashboarddata().enqueue(new Callback<DashboardContainer>() {
            @Override
            public void onResponse(Call<DashboardContainer> call, Response<DashboardContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                   // Log.d("tesst", response.toString());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                   // Log.d("respons", String.valueOf(response.toString()));

                    Dashboard_adaptar adaptar = new Dashboard_adaptar(response.body().getData(), getApplicationContext());
                    recyclerView.setAdapter(adaptar);
                    Log.d("respons", String.valueOf(response.body().getData().toString()));

                }
            }

            @Override
            public void onFailure(Call<DashboardContainer> call, Throwable t) {
                progressDialog.dismiss();


            }
        });

    }

    public void navbar() {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_post:
                        Intent intent = new Intent(getApplicationContext(), AddPostScreen.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.log_out:
                        MySharedPreference.editor(getApplicationContext()).clear().commit();
                        startActivity(new Intent(DashBoardScreen.this, LoginScreen.class));
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Add_Category:
                        Intent intentcategory = new Intent(getApplicationContext(), InsertCategoryScreen.class);
                        startActivity(intentcategory);
                        //  finish();
                        break;
                    case R.id.Add_Division:
                        Intent intentdivision = new Intent(getApplicationContext(), InsertDivisionScreen.class);
                        startActivity(intentdivision);
                        // finish();
                        break;
                    case R.id.Add_Dstrict:
                        Intent intentdistrict = new Intent(getApplicationContext(), Add_District_Screen.class);
                        startActivity(intentdistrict);
                        // finish();
                        break;
                    case R.id.my_post_list:
                        Intent mypost = new Intent(getApplicationContext(), MyPostScreen.class);
                        startActivity(mypost);
                        // finish();
                        break;
                }

                return true;
            }
        });
    }


}