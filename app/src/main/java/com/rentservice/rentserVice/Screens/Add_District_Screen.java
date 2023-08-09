package com.rentservice.rentserVice.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rentservice.rentserVice.Adaptar.DistrictListShowAdaptar;
import com.rentservice.rentserVice.Adaptar.DivisionSpinnerAdaptar;
import com.rentservice.rentserVice.Data_Model.DistrictModel.DistrictContainer;
import com.rentservice.rentserVice.Data_Model.Division.Division;
import com.rentservice.rentserVice.Data_Model.Division.DivisionContainer;
import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;
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
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_district_screen);
        DivisionList = findViewById(R.id.division);
        District = findViewById(R.id.enter_district);
        recyclerView = findViewById(R.id.dis_list);
        api = RetrofitClient.difBaseUrle().create(Api.class);
        Save_District = findViewById(R.id.insert_dis);
        progressDialog = new ProgressDialog(Add_District_Screen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getdivision();
        getalldistrict();

        Save_District.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DivisionList.getSelectedItemPosition() > 0) {
                    if (!TextUtils.isEmpty(District.getText().toString())) {
                        api.insertDistrict(District.getText().toString(),
                                String.valueOf(data.get(DivisionList.getSelectedItemPosition()).getId().toString()))
                                .enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            District.getText().clear();
                                            getalldistrict();
                                            String meddage = response.body().get("message").getAsString();
                                            Toast.makeText(getApplicationContext(), meddage, Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select a Division", Toast.LENGTH_SHORT).show();
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
                    //Log.d("division", data.toString());

                }

            }

            @Override
            public void onFailure(Call<DivisionContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getalldistrict() {
        api.getDistrict().enqueue(new Callback<DistrictContainer>() {
            @Override
            public void onResponse(Call<DistrictContainer> call, Response<DistrictContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    DistrictListShowAdaptar adaptar = new DistrictListShowAdaptar(response.body().getData(), getApplicationContext());
                    recyclerView.setAdapter(adaptar);
                }
            }

            @Override
            public void onFailure(Call<DistrictContainer> call, Throwable t) {

            }
        });
    }
}