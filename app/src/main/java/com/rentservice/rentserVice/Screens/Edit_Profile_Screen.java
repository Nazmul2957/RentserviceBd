package com.rentservice.rentserVice.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rentservice.rentserVice.Adaptar.DistrictShowByDivision;
import com.rentservice.rentserVice.Adaptar.DivisionSpinnerAdaptar;
import com.rentservice.rentserVice.Adaptar.PoliceStationShowByDisAdaptar;
import com.rentservice.rentserVice.Data_Model.DistrictshowbyDivision.DistrictShow;
import com.rentservice.rentserVice.Data_Model.DistrictshowbyDivision.DistrictshowContainer;
import com.rentservice.rentserVice.Data_Model.Division.Division;
import com.rentservice.rentserVice.Data_Model.Division.DivisionContainer;
import com.rentservice.rentserVice.Data_Model.PoliceStationByDistrict.PolicestationByDistrictContainer;
import com.rentservice.rentserVice.Data_Model.PoliceStationByDistrict.PolicestationShowByDistrict;
import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.rentserVice.Util.Constant;
import com.rentservice.rentserVice.Util.MySharedPreference;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Profile_Screen extends AppCompatActivity {
    String ReceiveName, ReceiveAddress, ReceiveMobile, ReceiveId;
    TextView Name, Mobile, DivisionName, DistrictName, PoliceStation;
    EditText Address;
    Spinner Profile_Division, Profile_District, Profile_Police;
    Api api;
    ProgressDialog progressDialog;
    List<Division> data;
    List<DistrictShow> datas;
    List<PolicestationShowByDistrict> datass;
    String Token;
    LinearLayout DisHide, PoliceHide;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_screen);

        Name = findViewById(R.id.profile_name);
        Mobile = findViewById(R.id.profile_number);
        Address = findViewById(R.id.profile_address);
        Profile_Division = findViewById(R.id.profile_dibision);
        Profile_District = findViewById(R.id.profile_district);
        DisHide = findViewById(R.id.dis_hide);
        Profile_Police = findViewById(R.id.profile_police);
        PoliceHide = findViewById(R.id.police_hide);
        button = findViewById(R.id.update_Profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Update Your Profile");

        ReceiveName = getIntent().getStringExtra("name");
        ReceiveAddress = getIntent().getStringExtra("Address");
        ReceiveMobile = getIntent().getStringExtra("Mobile");
        ReceiveId = getIntent().getStringExtra("Pid");

        Name.setText(ReceiveName);
        Address.setText(ReceiveAddress);
        Mobile.setText(ReceiveMobile);

        api = RetrofitClient.difBaseUrle().create(Api.class);
        Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");
        progressDialog = new ProgressDialog(Edit_Profile_Screen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);

        getdivision();


        Profile_Division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Profile_Division.getSelectedItemId() != 0) {
                    DisHide.setVisibility(View.VISIBLE);
                    zilla(String.valueOf(data.get(position).getId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Profile_District.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (Profile_District.getSelectedItemId() != 0) {
                    PoliceHide.setVisibility(View.VISIBLE);
                    police(String.valueOf(datas.get(position).getId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataValication();
            }
        });
        //Upload();
        //

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
                    Profile_Division.setAdapter(customeadaptar);
                }
            }

            @Override
            public void onFailure(Call<DivisionContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void zilla(String id) {
        progressDialog.show();
        api.getDistrictbydivision(id).enqueue(new Callback<DistrictshowContainer>() {
            @Override
            public void onResponse(Call<DistrictshowContainer> call, Response<DistrictshowContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    datas = response.body().getData();
                    datas.add(0, new DistrictShow(0, "Select District"));
                    DistrictShowByDivision cusadaptar = new DistrictShowByDivision(datas, getApplicationContext());
                    Profile_District.setAdapter(cusadaptar);
                    //  Log.d("District", datas.toString());

                }
            }

            @Override
            public void onFailure(Call<DistrictshowContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void police(String id) {
        progressDialog.show();
        api.getpolicebydistrict(id).enqueue(new Callback<PolicestationByDistrictContainer>() {
            @Override
            public void onResponse(Call<PolicestationByDistrictContainer> call, Response<PolicestationByDistrictContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    datass = response.body().getData();
                    datass.add(0, new PolicestationShowByDistrict(0, "Select Police Station"));
                    PoliceStationShowByDisAdaptar policeadaptar = new PoliceStationShowByDisAdaptar(datass, getApplicationContext());
                    Profile_Police.setAdapter(policeadaptar);
                }
            }

            @Override
            public void onFailure(Call<PolicestationByDistrictContainer> call, Throwable t) {

            }
        });
    }


    public void DataValication() {
        if (Profile_Division.getSelectedItemPosition() > 0) {
            if (Profile_District.getSelectedItemPosition() > 0) {
                if (Profile_Police.getSelectedItemPosition() > 0) {
                    Upload();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select Police Station", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(getApplicationContext(), "Please Select District", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Please Select Division", Toast.LENGTH_SHORT).show();
        }
    }

    public void Upload() {


        api.ProfileEdit(String.valueOf(data.get(Profile_Division.getSelectedItemPosition()).getId().toString()),
                String.valueOf(datas.get(Profile_District.getSelectedItemPosition()).getId().toString()),
                String.valueOf(datass.get(Profile_Police.getSelectedItemPosition()).getId().toString()),
                Token, ReceiveId, ReceiveAddress).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    String message = response.body().get("message").toString();
                    // Log.d("error message", message);
                    Intent intent = new Intent(getApplicationContext(), Profile_Screen.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "No Update Profile", Toast.LENGTH_SHORT).show();
            }
        });


    }
}