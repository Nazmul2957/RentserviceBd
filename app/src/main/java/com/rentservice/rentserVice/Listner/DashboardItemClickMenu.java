package com.rentservice.rentserVice.Listner;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.rentservice.rentserVice.Adaptar.Dashboard_adaptar;
import com.rentservice.rentserVice.Data_Model.Category_Search.PostCategory;
import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.rentserVice.Util.Constant;
import com.rentservice.rentserVice.Util.MySharedPreference;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardItemClickMenu implements View.OnClickListener {
    Dashboard_adaptar dashboard_adaptar;
    Dashboard_adaptar.OnItemClickListener listenered;
    Dashboard_adaptar.ViewHolders viewHolders;
    PostCategory datum;
    ProgressDialog progressDialog;
    Api api;
    Context context;
    String Token;


    public DashboardItemClickMenu(Dashboard_adaptar dashboard_adaptar,
                                  Dashboard_adaptar.OnItemClickListener listenered,
                                  Dashboard_adaptar.ViewHolders viewHolders, PostCategory datum
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

        PopupMenu popupMenu = new PopupMenu(viewHolders.itemView.getContext(), viewHolders.getoption());
        popupMenu.getMenuInflater().inflate(R.menu.dashboard_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.added_favourite:
                        progressDialog.show();
                        api.insertfavourite(Token, String.valueOf(datum.getId())).enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                progressDialog.show();
                                if (response.isSuccessful() && response.body() != null) {
                                    progressDialog.dismiss();
                                    listenered.onItemClick(1);
                                    Toast.makeText(context, "Item Added", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Item not Added", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return true;
                    default:
                        return false;

                }

            }
        });
        popupMenu.show();




    }
}
