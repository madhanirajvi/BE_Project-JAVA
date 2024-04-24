package com.example.maliba;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsRowAdapter extends ArrayAdapter<Item> {

	 Context context;
	 
	    public NewsRowAdapter(Context context, int resourceId,
	            List<Item> items) {
	        super(context, resourceId, items);
	        this.context = context;
	    }
	     
	    /*private view holder class*/
	    private class ViewHolder {
	        ImageView imageView;
	        TextView txtTitle;
	        TextView txtDesc;
	        TextView tvdate;
	    }
	     
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder = null;
	        Item rowItem = getItem(position);
	         
	        LayoutInflater mInflater = (LayoutInflater) context
	                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.row, null);
	            holder = new ViewHolder();
	            holder.txtDesc = (TextView) convertView.findViewById(R.id.tvtitle);
	            holder.txtTitle = (TextView) convertView.findViewById(R.id.tvdesc);
	            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
	            holder.tvdate = (TextView) convertView.findViewById(R.id.tvdate);
	            convertView.setTag(holder);
	        } else
	            holder = (ViewHolder) convertView.getTag();
	                 
	        holder.txtDesc.setText(rowItem.getDesc());
	        holder.txtTitle.setText(rowItem.getTitle());
	        holder.tvdate.setText(rowItem.getdate());
	        Bitmap image;
			try {
				Picasso.with(context).load(rowItem.getLink()).into( holder.imageView);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	       
	         
	        return convertView;
	    }
	}