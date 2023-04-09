package com.example.my_application.Adaptar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_application.Data_Model.Favourite.Favourite;
import com.example.my_application.Listner.FavouriteMenu_ClickListner;
import com.example.my_application.R;
import com.example.my_application.Screens.SinglePostViewScreen;

import java.util.ArrayList;
import java.util.List;

public class FavouriteListAdaptar extends RecyclerView.Adapter<FavouriteListAdaptar.ViewHolders> {

    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    List<Favourite> favouriteList = new ArrayList<>();
    Context context;

    public FavouriteListAdaptar(List<Favourite> favouriteList, Context context, OnItemClickListener listener) {
        this.favouriteList = favouriteList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_list_model, parent, false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, @SuppressLint("RecyclerView") int position) {
        holder.Post_title.setText(String.valueOf(favouriteList.get(position).getTitle()));
        holder.Post_Descrip.setText(String.valueOf(favouriteList.get(position).getDescription()));
        holder.Post_Pp.setText(String.valueOf(favouriteList.get(position).getPrice()));
        Glide.with(context).load("https://rentservicebd.com/public/api/image/" +
                favouriteList.get(position).getImage1()).into(holder.Post_image);
        holder.optionMenu.setOnClickListener(new FavouriteMenu_ClickListner(this, listener, holder, favouriteList.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Postid = String.valueOf(favouriteList.get(position).getId());
                Log.e("Product id", String.valueOf(Postid));
                Intent intent = new Intent(context, SinglePostViewScreen.class);
                intent.putExtra(Intent.EXTRA_UID, Postid);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {


        TextView Post_title, Post_Descrip, Post_Pp;
        ImageView Post_image, optionMenu;


        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            Post_title = itemView.findViewById(R.id.post_title);
            Post_Descrip = itemView.findViewById(R.id.post_description);
            Post_Pp = itemView.findViewById(R.id.post_price);
            Post_image = itemView.findViewById(R.id.post_img);
            optionMenu = itemView.findViewById(R.id.iv_menu);
        }

        public ImageView getoption() {
            return this.optionMenu;
        }
    }
}
