package com.example.my_application.Adaptar;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.my_application.Data_Model.Dashboard.Datum;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class Dashboard_adaptar extends RecyclerView.Adapter<Dashboard_adaptar.ViewHolders> {

    List<Datum> postlist = new ArrayList<>();
    Context context;



    public Dashboard_adaptar(List<Datum> postlist,Context context) {
        this.postlist = postlist;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_list_model, parent, false);
        return new ViewHolders(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {

        holder.Post_title.setText(String.valueOf(postlist.get(position).getTitle()));
        holder.Post_Descrip.setText(String.valueOf(postlist.get(position).getDescription()));
        holder.Post_Pp.setText(String.valueOf(postlist.get(position).getPrice()));
//        Glide.with(Profile_Page.this).load(response.body().
//                getProfilelist().getImage()).into(Profile_pic);
        Glide.with(context).load("https://rentservicebd.com/public/api/image/"+postlist.get(position).getImage1()).into(holder.Post_image);

    }

    @Override
    public int getItemCount() {
        return postlist.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView Post_title, Post_Descrip, Post_Pp;
        ImageView Post_image;


        public ViewHolders(@NonNull View itemView) {
            super(itemView);

            Post_title = itemView.findViewById(R.id.post_title);
            Post_Descrip = itemView.findViewById(R.id.post_description);
            Post_Pp = itemView.findViewById(R.id.post_price);
            Post_image = itemView.findViewById(R.id.post_img);
        }
    }
}
