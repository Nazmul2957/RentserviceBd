package com.example.my_application.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_application.Data_Model.Category.CategoryList;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;


public class Category_list_Adaptar extends RecyclerView.Adapter<Category_list_Adaptar.ViewHolders> {
    List<CategoryList> list_cat = new ArrayList<>();
    Context context;

    public Category_list_Adaptar(List<CategoryList> list_cat, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        holder.cat_show.setText(String.valueOf(list_cat.get(position).getName()));

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
