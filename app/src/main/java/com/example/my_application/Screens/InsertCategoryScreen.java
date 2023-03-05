package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_application.Adaptar.Category_list_Adaptar;
import com.example.my_application.Adaptar.Dashboard_adaptar;
import com.example.my_application.Data_Model.Category.CategoryContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertCategoryScreen extends AppCompatActivity {

    EditText Category_add;
    Button Save_Category;
    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_category_screen);

        Category_add = findViewById(R.id.enter_category);
        recyclerView = findViewById(R.id.category_list);
        Save_Category = findViewById(R.id.insert);
        api = RetrofitClient.difBaseUrle().create(Api.class);
        progressDialog = new ProgressDialog(InsertCategoryScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        api.getcat().enqueue(new Callback<CategoryContainer>() {
            @Override
            public void onResponse(Call<CategoryContainer> call, Response<CategoryContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    Log.d("respons", String.valueOf(response.body()));
                    // Dashboard_adaptar adaptar = new Dashboard_adaptar(response.body().getData(), getApplicationContext());
                    Category_list_Adaptar adaptar = new Category_list_Adaptar(response.body().getData(), getApplicationContext());
                    recyclerView.setAdapter(adaptar);

                }
            }

            @Override
            public void onFailure(Call<CategoryContainer> call, Throwable t) {

            }
        });

        Save_Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Category_add.getText().toString())) {
                    api.insertcategory(Category_add.getText().toString()).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Category_add.getText().clear();
                                progressDialog.dismiss();
                                String messa = response.body().get("message").getAsString();
                                Toast.makeText(getApplicationContext(), messa, Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    Category_add.setError("Please Input Category Name");
                    Category_add.requestFocus();
                }
            }
        });


    }
}