package com.rentservice.rentserVice.Adaptar;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rentservice.rentserVice.Data_Model.Category_Search.PostCategory;
import com.rentservice.rentserVice.Data_Model.Dashboard.Datum;
import com.rentservice.rentserVice.Listner.DashboardItemClickMenu;
import com.example.my_application.R;
import com.rentservice.rentserVice.Screens.SinglePostViewScreen;

import java.util.ArrayList;
import java.util.List;

public class Dashboard_adaptar extends RecyclerView.Adapter<Dashboard_adaptar.ViewHolders> {

    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    List<Datum> postlist = new ArrayList<>();
    Context context;

    public Dashboard_adaptar(List<Datum> postlist, Context context,
                             Dashboard_adaptar.OnItemClickListener listener) {
        this.postlist = postlist;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dashboard_list_model, parent, false);
        return new ViewHolders(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, @SuppressLint("RecyclerView") int position) {

        holder.Post_title.setText(String.valueOf(postlist.get(position).getTitle()));
        holder.Post_Descrip.setText(String.valueOf(postlist.get(position).getDescription()));
        holder.Post_Pp.setText(String.valueOf(postlist.get(position).getPrice()));
        Glide.with(context).load("https://rentservicebd.com/public/api/image/" +
                postlist.get(position).getImage1()).into(holder.Post_image);

        holder.Add_Favourite.setOnClickListener(new DashboardItemClickMenu(this,
                (OnItemClickListener) listener, holder, postlist.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Postid = String.valueOf(postlist.get(position).getId());
                Intent intent = new Intent(context, SinglePostViewScreen.class);
                intent.putExtra(Intent.EXTRA_UID, Postid);
                v.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return postlist.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView Post_title, Post_Descrip, Post_Pp;
        ImageView Post_image, Add_Favourite;


        public ViewHolders(@NonNull View itemView) {
            super(itemView);

            Post_title = itemView.findViewById(R.id.post_title);
            Post_Descrip = itemView.findViewById(R.id.post_description);
            Post_Pp = itemView.findViewById(R.id.post_price);
            Post_image = itemView.findViewById(R.id.post_img);
            Add_Favourite = itemView.findViewById(R.id.add_favourite);
        }

        public ImageView getoption() {
            return this.Add_Favourite;
        }
    }
}
