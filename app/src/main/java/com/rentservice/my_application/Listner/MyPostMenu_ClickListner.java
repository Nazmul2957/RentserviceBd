package com.rentservice.my_application.Listner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.rentservice.my_application.Adaptar.MyPostListAdaptar;
import com.rentservice.my_application.Data_Model.Mypost.MypostL;
import com.rentservice.my_application.Network.Api;
import com.rentservice.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.my_application.Screens.Edit_Post;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostMenu_ClickListner implements View.OnClickListener {

    MyPostListAdaptar myPostListAdaptar;
    MyPostListAdaptar.OnItemClickListener listenered;
    MyPostListAdaptar.ViewHolders viewHolders;
    MypostL mypostL;
    ProgressDialog progressDialog;
    Api api;
    Context context;

    public MyPostMenu_ClickListner(MyPostListAdaptar myPostListAdaptar,
                                   MyPostListAdaptar.OnItemClickListener listenered,
                                   MyPostListAdaptar.ViewHolders viewHolders, MypostL mypostL) {
        this.myPostListAdaptar = myPostListAdaptar;
        this.listenered = listenered;
        this.viewHolders = viewHolders;
        this.mypostL = mypostL;
        this.context = viewHolders.itemView.getContext();

        api = RetrofitClient.get(viewHolders.itemView.getContext()).create(Api.class);
        progressDialog = new ProgressDialog(viewHolders.itemView.getContext());
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View v) {

        PopupMenu popupMenu = new PopupMenu(viewHolders.itemView.getContext(), viewHolders.getoption());
        popupMenu.getMenuInflater().inflate(R.menu.mypost_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_mypost:
                        progressDialog.show();
                        api.deletemypost(String.valueOf(mypostL.getId())).enqueue(new Callback<JsonObject>() {
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
                    case R.id.Edit_mypost:
                        //progressDialog.show();
                        Intent intent = new Intent(context, Edit_Post.class);
                        intent.putExtra("Id", String.valueOf(mypostL.getId()));
                        intent.putExtra("Title", String.valueOf(mypostL.getTitle()));
                        intent.putExtra("Description", String.valueOf(mypostL.getDescription()));
                        intent.putExtra("Price", String.valueOf(mypostL.getPrice()));
                        intent.putExtra("Address", String.valueOf(mypostL.getAddress()));
                        intent.putExtra("Division", String.valueOf(mypostL.getDivisionId()));
                        intent.putExtra("District", String.valueOf(mypostL.getDistrictId()));
                        intent.putExtra("PoliceStation", String.valueOf(mypostL.getPoliceStationId()));
                        intent.putExtra("Category", String.valueOf(mypostL.getCategoryId()));
                        intent.putExtra("ImageOne", String.valueOf(mypostL.getImage1()));
                        intent.putExtra("Imagetwo", String.valueOf(mypostL.getImage2()));
                        intent.putExtra("Imagethree", String.valueOf(mypostL.getImage3()));
                        intent.putExtra("Imagefour", String.valueOf(mypostL.getImage4()));
                        intent.putExtra("Imagefive", String.valueOf(mypostL.getImage5()));
                       // listenered.onItemClick(1);
                        context.startActivity(intent);
                        return true;

                    default:
                        return false;

                }

            }
        });
        popupMenu.show();

    }
}
