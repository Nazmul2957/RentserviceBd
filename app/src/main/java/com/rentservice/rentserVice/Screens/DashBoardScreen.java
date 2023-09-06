package com.rentservice.rentserVice.Screens;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rentservice.rentserVice.Adaptar.Dashboard_adaptar;
import com.rentservice.rentserVice.Data_Model.Category_Search.CategorySearch;
import com.rentservice.rentserVice.Data_Model.Dashboard.DashboardContainer;
import com.rentservice.rentserVice.Data_Model.Profile.ProfileContainer;
import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.rentserVice.Util.Constant;
import com.rentservice.rentserVice.Util.MySharedPreference;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardScreen extends AppCompatActivity {

    Api api;
    NavigationView nav;
    RecyclerView recyclerView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView Username, HeadName, UserMobile, Add_Post,
            Favourite, Add_Category, Add_Division,
            Add_District, My_Post, About, LogOut, ADD_Police, Profile;
    ProgressDialog progressDialog;
    LinearLayout Category;
    boolean visibility = false;
    String Token;
    ImageView imageView, Search;
    String UserType = "user";
    AlertDialog.Builder builder;
    String PostId;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_screen);

        swipeRefreshLayout = findViewById(R.id.swip_refresh);

        recyclerView = findViewById(R.id.dash_board_list);
        nav = (NavigationView) findViewById(R.id.navbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        Username = findViewById(R.id.username);
        HeadName = findViewById(R.id.usernames);
        UserMobile = findViewById(R.id.usermobile);
        Add_Post = findViewById(R.id.add_post);
        Favourite = findViewById(R.id.favourite_list);
        Add_Category = findViewById(R.id.Add_Category);
        Add_Division = findViewById(R.id.Add_Division);
        ADD_Police = findViewById(R.id.Add_Police);
        Add_District = findViewById(R.id.Add_Dstrict);
        My_Post = findViewById(R.id.my_post_list);
        About = findViewById(R.id.about_page);
        LogOut = findViewById(R.id.log_out);
        Category = findViewById(R.id.admin_category);
        Profile = findViewById(R.id.profile_page);
        imageView = findViewById(R.id.profile_images);
        Search = findViewById(R.id.searchs);
        builder = new AlertDialog.Builder(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");
        PostId = getIntent().getStringExtra(Intent.EXTRA_UID);
       // Log.d("receive_categoryid", PostId);
        progressDialog = new ProgressDialog(DashBoardScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setTitle("Homepage");

        customenavbar();

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Filter_Page.class));

            }
        });

        api.getprofile(Token).enqueue(new Callback<ProfileContainer>() {
            @Override
            public void onResponse(Call<ProfileContainer> call, Response<ProfileContainer> response) {
                Username.setText(response.body().getUserInfo().getName());
                HeadName.setText(response.body().getUserInfo().getName());
                UserMobile.setText(response.body().getUserInfo().getMobile());
                Glide.with(getApplicationContext()).load("https://rentservicebd.com/public/api/image/" +
                        response.body().getUserInfo().getAvatar()).into(imageView);

            }

            @Override
            public void onFailure(Call<ProfileContainer> call, Throwable t) {

            }
        });

        data();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    public void data() {

//        api.categorysearch(PostId).enqueue(new Callback<CategorySearch>() {
//            @Override
//            public void onResponse(Call<CategorySearch> call, Response<CategorySearch> response) {
//                progressDialog.dismiss();
//                if (response.isSuccessful() && response.body() != null) {
//                    recyclerView.setHasFixedSize(true);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,
//                            false));
//                    Dashboard_adaptar adaptar = new Dashboard_adaptar(response.body().getPosts(), getApplicationContext(), new Dashboard_adaptar.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(int position) {
//
//                        }
//                    });
//                    recyclerView.setAdapter(adaptar);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategorySearch> call, Throwable t) {
//                progressDialog.dismiss();
//            }
//        });


        api.getdashboarddata().enqueue(new Callback<DashboardContainer>() {
            @Override
            public void onResponse(Call<DashboardContainer> call, Response<DashboardContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    Dashboard_adaptar adaptar = new Dashboard_adaptar(response.body().getData(), getApplicationContext(),
                            new Dashboard_adaptar.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {

                                }
                            });

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

    public void customenavbar() {
        api.getprofile(Token).enqueue(new Callback<ProfileContainer>() {
            @Override
            public void onResponse(Call<ProfileContainer> call, Response<ProfileContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (String.valueOf(response.body().getUserInfo().getType()) == UserType) {
                        Category.setVisibility(View.GONE);
                    } else {
                        Category.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileContainer> call, Throwable t) {

            }
        });

        Add_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddPostScreen.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder.setMessage("Do you want to Log out").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MySharedPreference.editor(getApplicationContext()).clear().commit();
                        startActivity(new Intent(DashBoardScreen.this, LoginScreen.class));
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                AlertDialog alert = builder.create();

                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    }
                });
                alert.setTitle("Log Out");
                alert.show();


            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Aintent = new Intent(getApplicationContext(), About_Screen.class);
                startActivity(Aintent);
            }
        });

        Add_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcategory = new Intent(getApplicationContext(), InsertCategoryScreen.class);
                startActivity(intentcategory);
            }
        });

        Add_Division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdivision = new Intent(getApplicationContext(), InsertDivisionScreen.class);
                startActivity(intentdivision);
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Pintent = new Intent(getApplicationContext(), Profile_Screen.class);
                startActivity(Pintent);
            }
        });

        ADD_Police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent P_intent = new Intent(getApplicationContext(), Add_PoliceStation_Screen.class);
                startActivity(P_intent);
            }
        });

        Add_District.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentdistrict = new Intent(getApplicationContext(), Add_District_Screen.class);
                startActivity(intentdistrict);
            }
        });

        Favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentfavouritelist = new Intent(getApplicationContext(), FavouriteListScreen.class);
                startActivity(intentfavouritelist);
            }
        });
        My_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mypost = new Intent(getApplicationContext(), MyPostScreen.class);
                startActivity(mypost);
            }
        });

    }

}