package com.rentservice.rentserVice.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rentservice.rentserVice.Data_Model.DistrictModel.District;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class DistrictList_Spinner_Adaptar extends BaseAdapter {

    List<District> DistrictList=new ArrayList<>();
    LayoutInflater inflter;

    public DistrictList_Spinner_Adaptar(List<District> districtList, Context applicationContext) {
        DistrictList = districtList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return DistrictList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return DistrictList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflter.inflate(R.layout.custome_spinner, null);
        TextView name=convertView.findViewById(R.id.textView);
        name.setText(DistrictList.get(position).getName());
        return convertView;
    }
}
