package com.test.rajat.minivie;

import android.content.res.TypedArray;
import android.widget.BaseAdapter;

/**
 * Created by Kartikey on 9/8/2015.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private TypedArray mDrawerIcons;
    protected String[] listArray = {"Home", "Latest", "WatchList","AboutUs","LogOut"};

    public ListAdapter(Context context,TypedArray mDrawerIcons) {
this.context=context;
        this.mDrawerIcons = mDrawerIcons;
    }

    @Override
    public int getCount() {
        return listArray.length;
    }

    @Override
    public Object getItem(int arg0) {
        return listArray[arg0];
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.listitems, null);
        }

        ImageView imgIcon = (ImageView) view.findViewById(R.id.ivlistitems);
        TextView itemTxt = (TextView) view.findViewById(R.id.tvlistitems);

        imgIcon.setImageResource(mDrawerIcons
                .getResourceId(arg0, -1));
        itemTxt.setText(listArray[arg0]);
        return view;
    }
}