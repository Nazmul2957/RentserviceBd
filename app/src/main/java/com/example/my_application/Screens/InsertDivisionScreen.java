package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.my_application.Adaptar.Category_list_Adaptar;
import com.example.my_application.Adaptar.DivisionListShow_Adaptar;
import com.example.my_application.Data_Model.Category.CategoryContainer;
import com.example.my_application.Data_Model.Division.DivisionContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertDivisionScreen extends AppCompatActivity {

    EditText Division_add;
    Button Save_Division;
    Api api;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_division_screen);

        Division_add = findViewById(R.id.enter_division);
        recyclerView = findViewById(R.id.div_list);
        Save_Division = findViewById(R.id.insert_div);
        api = RetrofitClient.difBaseUrle().create(Api.class);
        progressDialog = new ProgressDialog(InsertDivisionScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        api.getDivision().enqueue(new Callback<DivisionContainer>() {
            @Override
            public void onResponse(Call<DivisionContainer> call, Response<DivisionContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    Log.d("respons", String.valueOf(response.body()));
                    DivisionListShow_Adaptar adaptar = new DivisionListShow_Adaptar(response.body().getData(), getApplicationContext());
                    // DivisionListShow_Adaptar adaptar = new Category_list_Adaptar(response.body().getData(), getApplicationContext());
                    recyclerView.setAdapter(adaptar);

                }
            }

            @Override
            public void onFailure(Call<DivisionContainer> call, Throwable t) {

            }
        });

        Save_Division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(Division_add.getText().toString())) {
                    api.insertdivision(Division_add.getText().toString()).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Division_add.getText().clear();
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
                    Division_add.setError("Please Input Division Name");
                    Division_add.requestFocus();
                }
            }
        });


    }
}