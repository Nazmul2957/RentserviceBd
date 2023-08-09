package com.rentservice.rentserVice.Adaptar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.rentservice.rentserVice.Data_Model.Mypost.MypostL;
import com.rentservice.rentserVice.Listner.MyPostMenu_ClickListner;
import com.example.my_application.R;
import com.rentservice.rentserVice.Screens.SinglePostViewScreen;

import java.util.ArrayList;
import java.util.List;

public class MyPostListAdaptar extends RecyclerView.Adapter<MyPostListAdaptar.ViewHolders> {

    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    List<MypostL> postlist = new ArrayList<>();
    Context context;

    public MyPostListAdaptar(List<MypostL> postlist, Context context, OnItemClickListener listener) {
        this.postlist = postlist;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mypostlist_model, parent, false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, @SuppressLint("RecyclerView") int position) {
        holder.Post_title.setText(String.valueOf(postlist.get(position).getTitle()));
        holder.Post_Descrip.setText(String.valueOf(postlist.get(position).getDescription()));
        holder.Post_Pp.setText(String.valueOf(postlist.get(position).getPrice()));
        Glide.with(context).load("https://rentservicebd.com/public/api/image/" +
                postlist.get(position).getImage1()).into(holder.Post_image);

        holder.optionMenu.setOnClickListener(new MyPostMenu_ClickListner(this,
                listener,holder,postlist.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Postid = String.valueOf(postlist.get(position).getId());
                Log.e("Product id", String.valueOf(Postid));
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
        ImageView Post_image,optionMenu;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);

            Post_title = itemView.findViewById(R.id.post_title);
            Post_Descrip = itemView.findViewById(R.id.post_description);
            Post_Pp = itemView.findViewById(R.id.post_price);
            Post_image = itemView.findViewById(R.id.post_img);
            optionMenu=itemView.findViewById(R.id.deletemenu);
        }

        public ImageView getoption() {
            return this.optionMenu;
        }
    }
}
