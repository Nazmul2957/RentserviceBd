package com.rentservice.rentserVice.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rentservice.rentserVice.Data_Model.PoliceStationByDistrict.PolicestationShowByDistrict;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class PoliceStationShowByDisAdaptar extends BaseAdapter {

    List<PolicestationShowByDistrict> policeshow=new ArrayList<>();
    LayoutInflater inflter;

    public PoliceStationShowByDisAdaptar(List<PolicestationShowByDistrict> policeshow, Context applicationContext) {
        this.policeshow = policeshow;
        this.inflter = (LayoutInflater.from(applicationContext));;
    }

    @Override
    public int getCount() {
        return policeshow.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return policeshow.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.custome_spinner, null);
        TextView name=convertView.findViewById(R.id.textView);
        name.setText(policeshow.get(position).getName());
        return convertView;
    }
}
