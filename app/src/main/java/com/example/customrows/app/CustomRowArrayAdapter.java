package com.example.customrows.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class CustomRowArrayAdapter extends ArrayAdapter<CustomRowAdapter> {
    List<CustomRowAdapter> listRows = null;

    public CustomRowArrayAdapter(Context context, int resource, List<CustomRowAdapter> objects) {
        super(context, resource, objects);
        this.listRows = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        position
        return listRows.get(position).getView(position,convertView, parent);
    }
}
