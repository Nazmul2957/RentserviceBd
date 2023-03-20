package com.example.my_application.Adaptar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.my_application.Data_Model.DistrictModel.District;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class DistrictListAdaptar extends BaseAdapter {
    List<District> districtList=new ArrayList<>();
    LayoutInflater inflter;

    public DistrictListAdaptar(List<District> districtList,  Context applicationContext) {
        this.districtList = districtList;
        this.inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return districtList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return districtList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.custome_spinner, null);
        TextView name=convertView.findViewById(R.id.textView);
        name.setText(districtList.get(position).getName());
        return convertView;
    }


}
