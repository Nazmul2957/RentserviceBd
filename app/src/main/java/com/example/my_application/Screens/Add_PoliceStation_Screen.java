package com.example.my_application.Screens;

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

import com.example.my_application.Adaptar.DistrictList_Spinner_Adaptar;
import com.example.my_application.Adaptar.DivisionSpinnerAdaptar;
import com.example.my_application.Adaptar.PolicaStation_List_Adaptar;
import com.example.my_application.Data_Model.DistrictModel.District;
import com.example.my_application.Data_Model.DistrictModel.DistrictContainer;
import com.example.my_application.Data_Model.Division.Division;
import com.example.my_application.Data_Model.Division.DivisionContainer;
import com.example.my_application.Data_Model.PoliceStation.PoliceStationContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_PoliceStation_Screen extends AppCompatActivity {

    Api api;
    ProgressDialog progressDialog;
    List<Division> data;
    List<District> dis_data;
    Spinner DivisionList, Districtlist_p;
    EditText Policestation;
    Button Save_Policestation;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_police_station_screen);

        DivisionList = findViewById(R.id.division_list);
        Districtlist_p = findViewById(R.id.district_list);
        Policestation = findViewById(R.id.enter_policestation);
        Save_Policestation = findViewById(R.id.insert_police);
        recyclerView = findViewById(R.id.police_list);
        api = RetrofitClient.difBaseUrle().create(Api.class);
        progressDialog = new ProgressDialog(Add_PoliceStation_Screen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getdivision();
        getdistrict();
        save_police_station();
        getallPolicstation();

    }

    public void save_police_station() {
        Save_Policestation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DivisionList.getSelectedItemPosition() > 0) {
                    if (Districtlist_p.getSelectedItemPosition() > 0) {
                        if (!TextUtils.isEmpty(Policestation.getText().toString())) {
                            api.insertpolicestation(Policestation.getText().toString(),
                                    String.valueOf(data.get(DivisionList.getSelectedItemPosition()).getId().toString()),
                                    String.valueOf(dis_data.get(Districtlist_p.getSelectedItemPosition()).getId().toString())).enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        Policestation.getText().clear();
                                        getallPolicstation();
                                        String meddage = response.body().get("message").getAsString();
                                        Toast.makeText(getApplicationContext(), meddage, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Policestation.setError("Please Input PoliceStation");
                            Policestation.requestFocus();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Please Select a District", Toast.LENGTH_SHORT).show();

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
                }

            }

            @Override
            public void onFailure(Call<DivisionContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getdistrict() {
        progressDialog.show();
        api.getDistrict().enqueue(new Callback<DistrictContainer>() {
            @Override
            public void onResponse(Call<DistrictContainer> call, Response<DistrictContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dis_data = response.body().getData();
                    dis_data.add(0, new District(0, "Select District"));
                    DistrictList_Spinner_Adaptar customAdap = new DistrictList_Spinner_Adaptar(dis_data, getApplicationContext());
                    Districtlist_p.setAdapter(customAdap);

                }
            }

            @Override
            public void onFailure(Call<DistrictContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void getallPolicstation() {
        api.getpolice().enqueue(new Callback<PoliceStationContainer>() {
            @Override
            public void onResponse(Call<PoliceStationContainer> call, Response<PoliceStationContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    PolicaStation_List_Adaptar adaptar = new PolicaStation_List_Adaptar(response.body().getData(), getApplicationContext());
                    recyclerView.setAdapter(adaptar);
                }
            }

            @Override
            public void onFailure(Call<PoliceStationContainer> call, Throwable t) {

            }
        });
    }
}