package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.my_application.Adaptar.FavouriteListAdaptar;
import com.example.my_application.Adaptar.MyPostListAdaptar;
import com.example.my_application.Data_Model.Favourite.FavouriteContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteListScreen extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list_screen);

        recyclerView = findViewById(R.id.favouritepost_list);
        progressDialog = new ProgressDialog(FavouriteListScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

    }


    @Override
    public void onResume() {
        super.onResume();
        getdata();


    }

    public void getdata() {
        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");
        progressDialog.show();
        api.favouritelist(Token).enqueue(new Callback<FavouriteContainer>() {
            @Override
            public void onResponse(Call<FavouriteContainer> call, Response<FavouriteContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    FavouriteListAdaptar adaptar = new FavouriteListAdaptar(response.body().getFavourites()
                            , getApplicationContext(), new FavouriteListAdaptar.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            // finish();
                            // startActivity(new Intent(FavouriteListScreen.this, DashBoardScreen.class));
                            getdata();
                        }
                    });
                    recyclerView.setAdapter(adaptar);


                }
            }

            @Override
            public void onFailure(Call<FavouriteContainer> call, Throwable t) {

            }
        });
    }
}