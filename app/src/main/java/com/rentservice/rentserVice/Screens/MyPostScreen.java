package com.rentservice.rentserVice.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.rentservice.rentserVice.Adaptar.MyPostListAdaptar;
import com.rentservice.rentserVice.Data_Model.Mypost.MypostListContainer;
import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.rentserVice.Util.Constant;
import com.rentservice.rentserVice.Util.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostScreen extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_screen);

        recyclerView = findViewById(R.id.mypost_list);

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(MyPostScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mypost();

    }


    public void mypost() {
        api.getuserpost(Token).enqueue(new Callback<MypostListContainer>() {
            @Override
            public void onResponse(Call<MypostListContainer> call, Response<MypostListContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    MyPostListAdaptar adaptar = new MyPostListAdaptar(response.body().getPosts(),
                            getApplicationContext(), new MyPostListAdaptar.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            mypost();
                        }
                    });
                    recyclerView.setAdapter(adaptar);

                }
            }

            @Override
            public void onFailure(Call<MypostListContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}