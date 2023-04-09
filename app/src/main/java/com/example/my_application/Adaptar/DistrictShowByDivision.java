package com.example.my_application.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.my_application.Data_Model.DistrictshowbyDivision.DistrictShow;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class DistrictShowByDivision extends BaseAdapter {

    List<DistrictShow> districtshowbydiv=new ArrayList<>();
    LayoutInflater inflter;

    public DistrictShowByDivision(List<DistrictShow> districtshowbydiv, Context applicationContext) {
        this.districtshowbydiv = districtshowbydiv;
        this.inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return districtshowbydiv.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return districtshowbydiv.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflter.inflate(R.layout.custome_spinner, null);
        TextView name=convertView.findViewById(R.id.textView);
        name.setText(districtshowbydiv.get(position).getName());
        return convertView;
    }
}
