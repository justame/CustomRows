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

import com.nostra13.universalimageloader.core.ImageLoader;

import javax.xml.transform.Result;


public class CustomRowAdapter extends BaseAdapter {
    public FeedRow feedRow;
    public Context context;
    public LayoutInflater inflater;

    public CustomRowAdapter(Context context, FeedRow feedRow) {
        super();

        this.context = context;
        this.feedRow = feedRow;
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

//        holder.imgViewLogo.setImageResource(R.drawable.ic_launcher);

        ImageLoader.getInstance().displayImage(feedRow.pictureMain, holder.imgViewLogo);
        holder.txtViewTitle.setText(feedRow.message);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Toast.makeText(context,feedRow.message,Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
