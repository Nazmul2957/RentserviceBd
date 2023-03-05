package com.example.my_application.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.my_application.Data_Model.Division.Division;
import com.example.my_application.Data_Model.PoliceStation.Police;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class PoliceStationAdaptar extends BaseAdapter {

    List<Police> Policelist=new ArrayList<>();
    LayoutInflater inflter;

    public PoliceStationAdaptar(List<Police> policelist, Context applicationContext) {
        Policelist = policelist;
        this.inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Policelist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return Policelist.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.custome_spinner, null);
        TextView name=convertView.findViewById(R.id.textView);

        name.setText(Policelist.get(position).getName());
        return convertView;
    }
}
