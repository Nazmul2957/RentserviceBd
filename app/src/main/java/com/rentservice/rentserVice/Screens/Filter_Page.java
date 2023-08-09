package com.rentservice.rentserVice.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.my_application.R;
import com.rentservice.rentserVice.Adaptar.SearchAdaptar;
import com.rentservice.rentserVice.Data_Model.Search.SearchContainer;
import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Filter_Page extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText Filter_Text;
    ImageView Filter_Search;
    ProgressDialog progressDialog;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_page);

        recyclerView = findViewById(R.id.dash_board_list_filter);
        Filter_Text = findViewById(R.id.filter_text);
        Filter_Search = findViewById(R.id.filter_search);

        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        progressDialog = new ProgressDialog(Filter_Page.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);


        Filter_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                api.getsearch(Filter_Text.getText().toString()).enqueue(new Callback<SearchContainer>() {
                    @Override
                    public void onResponse(Call<SearchContainer> call, Response<SearchContainer> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                                    LinearLayoutManager.VERTICAL, false));

                            SearchAdaptar adaptar=new SearchAdaptar(response.body().getPosts(),getApplicationContext(),
                                    new SearchAdaptar.OnItemClickListener(){
                                        @Override
                                        public void onItemClick(int position) {

                                        }
                                    });
                            Log.d("filter_search", String.valueOf(response.body().getPosts().toString()));

                            recyclerView.setAdapter(adaptar);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchContainer> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });

            }
        });


    }
}