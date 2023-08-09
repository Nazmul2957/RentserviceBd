package com.rentservice.rentserVice.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rentservice.rentserVice.Data_Model.Category.CategoryList;
import com.example.my_application.R;

import java.util.ArrayList;
import java.util.List;

public class Category_Spinner_Adaptar extends BaseAdapter {

    List<CategoryList> categoryListList=new ArrayList<>();
    LayoutInflater inflter;

    public Category_Spinner_Adaptar(List<CategoryList> categoryListList, Context applicationContext) {
        this.categoryListList = categoryListList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return categoryListList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return categoryListList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.custome_spinner, null);
        TextView name=convertView.findViewById(R.id.textView);
        name.setText(categoryListList.get(position).getName());
        return convertView;
    }
}
