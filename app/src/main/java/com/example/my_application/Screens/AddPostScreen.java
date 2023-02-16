package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostScreen extends AppCompatActivity {
    Api api;
    ProgressDialog progressDialog;
    Spinner DivisionList, DistrictList;
    List<Division> data;
    List<District> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_screen);

        DivisionList = findViewById(R.id.dibision);
        DistrictList = findViewById(R.id.districts);
        api = RetrofitClient.difBaseUrle().create(Api.class);

        progressDialog = new ProgressDialog(AddPostScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        getdivision();
        zilla();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    public void zilla() {
        progressDialog.show();
        api.getDistrict().enqueue(new Callback<DistrictContainer>() {
            @Override
            public void onResponse(Call<DistrictContainer> call, Response<DistrictContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    datas = response.body().getData();
                    datas.add(0, new District(0, "Select District"));
                    DistrictListAdaptar customeadaptar = new DistrictListAdaptar(datas, getApplicationContext());
                    DistrictList.setAdapter(customeadaptar);
                    Log.d("District", datas.toString());

                }

            }

            @Override
            public void onFailure(Call<DistrictContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}