package com.example.my_application.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_application.Data_Model.PoliceStation.Police;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class PolicaStation_List_Adaptar extends RecyclerView.Adapter<PolicaStation_List_Adaptar.ViewHolders> {

    List<Police> P_List = new ArrayList<>();
    Context context;

    public PolicaStation_List_Adaptar(List<Police> p_List, Context context) {
        P_List = p_List;
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
        holder.police_show.setText(String.valueOf(P_List.get(position).getName()));
    }

    @Override
    public int getItemCount() {
        return P_List.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView police_show;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            police_show = itemView.findViewById(R.id.cate_list);
        }
    }
}
