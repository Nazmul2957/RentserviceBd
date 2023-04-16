package com.rentservice.my_application.Listner;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.rentservice.my_application.Adaptar.FavouriteListAdaptar;
import com.rentservice.my_application.Data_Model.Favourite.Favourite;
import com.rentservice.my_application.Network.Api;
import com.rentservice.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.my_application.Util.Constant;
import com.rentservice.my_application.Util.MySharedPreference;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteMenu_ClickListner implements View.OnClickListener {

    FavouriteListAdaptar favouriteListAdaptar;
    FavouriteListAdaptar.OnItemClickListener listenered;
    FavouriteListAdaptar.ViewHolders viewHolders;
    Favourite favourite;
    ProgressDialog progressDialog;
    Api api;
    Context context;
    String Token;

    public FavouriteMenu_ClickListner(FavouriteListAdaptar favouriteListAdaptar,
                                      FavouriteListAdaptar.OnItemClickListener listenered,
                                      FavouriteListAdaptar.ViewHolders viewHolders, Favourite favourite) {
        this.favouriteListAdaptar = favouriteListAdaptar;
        this.listenered = listenered;
        this.viewHolders = viewHolders;
        this.favourite = favourite;
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
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_favourite:
                        progressDialog.show();
                        api.deletefavourite(Token, String.valueOf(favourite.getId())).enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    progressDialog.dismiss();
                                    listenered.onItemClick(1);
                                    String message = response.body().get("message").getAsString();
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
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
