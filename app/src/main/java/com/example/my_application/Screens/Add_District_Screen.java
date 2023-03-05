package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.my_application.Adaptar.DistrictListAdaptar;
import com.example.my_application.Adaptar.DivisionSpinnerAdaptar;
import com.example.my_application.Data_Model.DistrictModel.District;
import com.example.my_application.Data_Model.DistrictModel.DistrictContainer;
import com.example.my_application.Data_Model.Division.Division;
import com.example.my_application.Data_Model.Division.DivisionContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_District_Screen extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    List<Division> data;
    Spinner DivisionList;
    EditText District;
    Button Save_District;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_district_screen);
        DivisionList = findViewById(R.id.division);
        District = findViewById(R.id.enter_district);
        api = RetrofitClient.difBaseUrle().create(Api.class);
        Save_District = findViewById(R.id.insert_dis);
        progressDialog = new ProgressDialog(Add_District_Screen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        getdivision();

        Save_District.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("div_id", String.valueOf(DivisionList.getId()));
                Log.d("div_idp", "csssfsfsf");
                if (!TextUtils.isEmpty(District.getText().toString())) {
                    api.insertDistrict(District.getText().toString(),
                            String.valueOf(data.get(DivisionList.getSelectedItemPosition()).getId().toString()))
                            .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                District.getText().clear();
                                String meddage = response.body().get("message").getAsString();
                                Toast.makeText(getApplicationContext(), meddage, Toast.LENGTH_SHORT).show();
                               // getdivision();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                        }
                    });
                } else {
                    District.setError("Please Input District Name");
                    District.requestFocus();
                }


            }
        });


    }

    public void getdivision() {
        progressDialog.show();
        api.getDivision().enqueue(new Callback<DivisionContainer>() {
            @Override
            public void onResponse(Call<DivisionContainer> call, Response<DivisionContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    data = response.body().getData();
                    data.add(0, new Division(0, "Select Division"));
                    DivisionSpinnerAdaptar customeadaptar = new DivisionSpinnerAdaptar(data, getApplicationContext());
                    DivisionList.setAdapter(customeadaptar);
                    Log.d("division", data.toString());

                }

            }

            @Override
            public void onFailure(Call<DivisionContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}