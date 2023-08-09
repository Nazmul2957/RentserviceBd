package com.rentservice.rentserVice.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rentservice.rentserVice.Data_Model.DistrictModel.District;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class DistrictListShowAdaptar extends RecyclerView.Adapter<DistrictListShowAdaptar.ViewHolders> {

    List<District> listshow = new ArrayList<>();
    Context context;

    public DistrictListShowAdaptar(List<District> listshow, Context context) {
        this.listshow = listshow;
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

        holder.district_show.setText(String.valueOf(listshow.get(position).getName()));

    }

    @Override
    public int getItemCount() {
        return listshow.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView district_show;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            district_show = itemView.findViewById(R.id.cate_list);
        }
    }
}
