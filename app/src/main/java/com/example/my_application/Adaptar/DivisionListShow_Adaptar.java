package com.example.my_application.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_application.Data_Model.Category.CategoryList;
import com.example.my_application.Data_Model.Division.Division;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class DivisionListShow_Adaptar extends RecyclerView.Adapter<DivisionListShow_Adaptar.ViewHolders> {

    List<Division> list_division = new ArrayList<>();
    Context context;

    public DivisionListShow_Adaptar(List<Division> list_division, Context context) {
        this.list_division = list_division;
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
        holder.cat_show.setText(String.valueOf(list_division.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return list_division.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView cat_show;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            cat_show = itemView.findViewById(R.id.cate_list);
        }
    }
}
