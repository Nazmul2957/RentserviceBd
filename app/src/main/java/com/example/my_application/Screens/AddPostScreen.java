package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.my_application.Adaptar.DistrictListAdaptar;
import com.example.my_application.Adaptar.DivisionSpinnerAdaptar;
import com.example.my_application.Adaptar.PoliceStationAdaptar;
import com.example.my_application.Data_Model.DistrictModel.District;
import com.example.my_application.Data_Model.DistrictModel.DistrictContainer;
import com.example.my_application.Data_Model.Division.Division;
import com.example.my_application.Data_Model.Division.DivisionContainer;
import com.example.my_application.Data_Model.PoliceStation.Police;
import com.example.my_application.Data_Model.PoliceStation.PoliceStationContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostScreen extends AppCompatActivity {
    Api api;
    ProgressDialog progressDialog;
    Spinner DivisionList, DistrictList, Police_Station;
    List<Division> data;
    List<District> datas;
    List<Police> datass;
    EditText Title, Description, Price, Address;
    Button POSTDATA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_screen);

        DivisionList = findViewById(R.id.dibision);
        DistrictList = findViewById(R.id.districts);
        Police_Station = findViewById(R.id.policestation);
        Title = findViewById(R.id.insert_title);
        Description = findViewById(R.id.insert_description);
        Price = findViewById(R.id.insert_price);
        Address = findViewById(R.id.insert_address);
        POSTDATA = findViewById(R.id.Post);


        api = RetrofitClient.difBaseUrle().create(Api.class);
        String Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(AddPostScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        getdivision();
        zilla();
        police();

        POSTDATA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.insertPost(Title.getText().toString(), Description.getText().toString(), Price.getText().toString(), Address.getText().toString(),
                        String.valueOf(data.get(DivisionList.getSelectedItemPosition()).getId().toString()), String.valueOf(datas.get(DistrictList.getSelectedItemPosition()).getId().toString()),
                        String.valueOf(datass.get(Police_Station.getSelectedItemPosition()).getId().toString()), Token).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(getApplicationContext(), "Post Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddPostScreen.this, DashBoardScreen.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Your Internet Connection Error", Toast.LENGTH_SHORT).show();

                    }
                });

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

    public void police() {
        progressDialog.show();
        api.getpolice().enqueue(new Callback<PoliceStationContainer>() {
            @Override
            public void onResponse(Call<PoliceStationContainer> call, Response<PoliceStationContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    datass = response.body().getData();
                    datass.add(0, new Police(0, "Select Police Station"));
                    PoliceStationAdaptar adaptar = new PoliceStationAdaptar(datass, getApplicationContext());
                    Police_Station.setAdapter(adaptar);

                }
            }

            @Override
            public void onFailure(Call<PoliceStationContainer> call, Throwable t) {

            }
        });
    }
}