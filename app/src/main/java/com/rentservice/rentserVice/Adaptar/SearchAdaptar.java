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
import com.example.my_application.R;
import com.rentservice.rentserVice.Data_Model.Search.Post;
import com.rentservice.rentserVice.Listner.SearchMenuClickListner;
import com.rentservice.rentserVice.Screens.SinglePostViewScreen;

import java.util.ArrayList;
import java.util.List;

public class SearchAdaptar extends RecyclerView.Adapter<SearchAdaptar.ViewHolders> {

    public OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    List<Post> searchpost = new ArrayList<>();
    Context context;

    public SearchAdaptar( List<Post> searchpost, Context context,OnItemClickListener listener) {
        this.listener = listener;
        this.searchpost = searchpost;
        this.context = context;
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

        holder.Post_title.setText(String.valueOf(searchpost.get(position).getTitle()));
        holder.Post_Descrip.setText(String.valueOf(searchpost.get(position).getDescription()));
        holder.Post_Pp.setText(String.valueOf(searchpost.get(position).getPrice()));
        Glide.with(context).load("https://rentservicebd.com/public/api/image/" +
                searchpost.get(position).getImage1()).into(holder.Post_image);

        holder.Add_Favourite.setOnClickListener(new SearchMenuClickListner(this,listener,
                holder,searchpost.get(position)));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Postid = String.valueOf(searchpost.get(position).getId());
                Intent intent = new Intent(context, SinglePostViewScreen.class);
                intent.putExtra(Intent.EXTRA_UID, Postid);
                v.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return searchpost.size();
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
