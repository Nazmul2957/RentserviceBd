package com.rentservice.rentserVice.Adaptar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_application.R;
import com.rentservice.rentserVice.Data_Model.Category.CategoryList;
import com.rentservice.rentserVice.Screens.DashBoardScreen;

import java.util.ArrayList;
import java.util.List;

public class Homepage_Category_List_Adaptar extends RecyclerView.Adapter<Homepage_Category_List_Adaptar.ViewHolders> {

    List<CategoryList> list_cat = new ArrayList<>();
    Context context;

    public Homepage_Category_List_Adaptar(List<CategoryList> list_cat, Context context) {
        this.list_cat = list_cat;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_model, parent, false);
        return new ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, @SuppressLint("RecyclerView") int position) {
        holder.cat_show.setText(String.valueOf(list_cat.get(position).getName()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Postid = String.valueOf(list_cat.get(position).getId());
                Intent intent = new Intent(context, DashBoardScreen.class);
                Log.d("cat_id_show",Postid);
                intent.putExtra(Intent.EXTRA_UID, Postid);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_cat.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        TextView cat_show;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            cat_show = itemView.findViewById(R.id.cate_list);
        }
    }
}
