package com.rentservice.rentserVice.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.my_application.R;
import com.rentservice.rentserVice.Adaptar.Homepage_Category_List_Adaptar;
import com.rentservice.rentserVice.Data_Model.Category.CategoryContainer;
import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_List_Page extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_page);

        recyclerView = findViewById(R.id.category_list);
        api = RetrofitClient.difBaseUrle().create(Api.class);
        progressDialog = new ProgressDialog(Category_List_Page.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getallcategory();
    }

    public void getallcategory() {
        api.getcat().enqueue(new Callback<CategoryContainer>() {
            @Override
            public void onResponse(Call<CategoryContainer> call, Response<CategoryContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
//                            LinearLayoutManager.VERTICAL, false));
                    Log.d("respons", String.valueOf(response.body()));
                    Homepage_Category_List_Adaptar adaptar = new Homepage_Category_List_Adaptar(response.body().getData(), getApplicationContext());
                    recyclerView.setAdapter(adaptar);

                }
            }

            @Override
            public void onFailure(Call<CategoryContainer> call, Throwable t) {

            }
        });
    }
}