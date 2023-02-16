package com.example.my_application.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.my_application.Data_Model.Division.Division;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class DivisionSpinnerAdaptar extends BaseAdapter {

    List<Division> divisionlist=new ArrayList<>();
    LayoutInflater inflter;

    public DivisionSpinnerAdaptar(List<Division> divisionlist, Context applicationContext) {
        this.divisionlist = divisionlist;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return divisionlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return divisionlist.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custome_spinner, null);
        TextView name=view.findViewById(R.id.textView);

        name.setText(divisionlist.get(i).getName());
        return view;
    }
}
