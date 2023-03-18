package com.example.my_application.Listner;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.session.MediaSession;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.my_application.Adaptar.Dashboard_adaptar;
import com.example.my_application.Data_Model.Dashboard.Datum;
import com.example.my_application.Data_Model.Profile.ProfileContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardItemClickMenu implements View.OnClickListener {
    Dashboard_adaptar dashboard_adaptar;
    Dashboard_adaptar.OnItemClickListener listenered;
    Dashboard_adaptar.ViewHolders viewHolders;
    Datum datum;
    ProgressDialog progressDialog;
    Api api;
    Context context;
    String Token;


    public DashboardItemClickMenu(Dashboard_adaptar dashboard_adaptar,
                                  Dashboard_adaptar.OnItemClickListener listenered,
                                  Dashboard_adaptar.ViewHolders viewHolders, Datum datum
    ) {
        this.dashboard_adaptar = dashboard_adaptar;
        this.listenered = listenered;
        this.viewHolders = viewHolders;
        this.datum = datum;
        this.context = viewHolders.itemView.getContext();

         Token = MySharedPreference.getInstance(context).getString(Constant.TOKEN, "not found");

        api = RetrofitClient.get(viewHolders.itemView.getContext()).create(Api.class);
        progressDialog = new ProgressDialog(viewHolders.itemView.getContext());
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);


    }

    @Override
    public void onClick(View v) {

        api.insertfavourite(Token, String.valueOf(datum.getId())).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.show();
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Item not Added", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
