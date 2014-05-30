package com.example.customrows.app;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CustomRowAdapter extends BaseAdapter {
    public String title;
    public String description;
    public Activity context;
    public LayoutInflater inflater;

    public CustomRowAdapter(Activity context, String title) {
        super();

        this.context = context;
        this.title = title;

        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder
    {
        ImageView imgViewLogo;
        TextView txtViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_row, null);

            holder.imgViewLogo = (ImageView)convertView.findViewById(R.id.imageView);
            holder.txtViewTitle = (TextView)convertView.findViewById(R.id.textView);


            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.imgViewLogo.setImageResource(R.drawable.ic_launcher);
        holder.txtViewTitle.setText(title);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context,title,Toast.LENGTH_SHORT).show();

            }
        });
        return convertView;
    }
}
