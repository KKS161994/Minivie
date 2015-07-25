package com.test.rajat.minivie;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajat on 7/14/2015.
 */
public class MyAdapter extends ArrayAdapter<HashMap<String,String>> {

    private ArrayList<HashMap<String,String>> movies_list;
    private Context context;
    private Typeface roboto_thin,roboto_reg;


    public MyAdapter(Context context,int resource,ArrayList<HashMap<String,String>> movies_list) {
        super(context, resource,movies_list);
        this.context=context;
        this.movies_list=movies_list;
        roboto_thin=Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Thin.ttf");
        roboto_reg=Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf");

        Log.d("LENCA",Integer.toString(movies_list.size()));


    }
    static class ViewHolder {
        ImageView iv_poster;
        TextView tv_title;
        TextView tv_releaseYear;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;

       ViewHolder holder;
        Log.d("IR", "row inflated");
        if(v==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.row,null);
            holder=new ViewHolder();
            holder.iv_poster=(ImageView) v.findViewById(R.id.iv_poster);
            holder.tv_title=(TextView) v.findViewById(R.id.tv_title);
            holder.tv_releaseYear=(TextView) v.findViewById(R.id.tv_releaseYear);
            v.setTag(holder);

       }
        else{
            holder=(ViewHolder) v.getTag();
        }
        HashMap<String,String> movie=movies_list.get(position);
        if(movie!=null){
            holder.tv_title.setTypeface(roboto_reg);
            holder.tv_releaseYear.setTextColor(Color.parseColor("#8B5742"));
            holder.tv_releaseYear.setTypeface(roboto_thin);
            holder.tv_title.setText(movie.get("title"));
            holder.tv_releaseYear.setText("("+movie.get("release_date").substring(0,4)+")");
            Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+movie.get("poster_path")).into(holder.iv_poster);
        }
        return v;
    }
}
