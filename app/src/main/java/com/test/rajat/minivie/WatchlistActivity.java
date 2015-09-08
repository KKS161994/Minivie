package com.test.rajat.minivie;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import db.WatchlistOpenHelper;

/**
 * Created by Rajat on 9/7/2015.
 */
public class WatchlistActivity extends BaseActivity {

    private WatchlistOpenHelper dbhelper;
    private SQLiteDatabase db_watchlist;
    private ArrayList<HashMap<String,String>> watchlist;
    private ListView lv_watchlist;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_watchlist, frameLayout);
        mDrawerList.setItemChecked(position, true);
        watchlist=new ArrayList<>();

        setTitle(listArray[position]);
        lv_watchlist=(ListView) findViewById(R.id.lv_watchlist);
        dbhelper=new WatchlistOpenHelper(this);
        db_watchlist=dbhelper.getWritableDatabase();
        new PopulateWatchlist().execute();



    }
private class PopulateWatchlist extends AsyncTask<Void,Void,Void>{

    @Override
    protected Void doInBackground(Void... params) {
        Cursor c=db_watchlist.rawQuery("SELECT TITLE,IMAGE,YEAR FROM watchlist",null);
        if(c!=null){
            if(c.moveToFirst()){
                do{
                    String title=c.getString(c.getColumnIndex("title"));
                    String image=c.getString(c.getColumnIndex("image"));
                    String year=c.getString(c.getColumnIndex("year"));
                    HashMap<String,String> movie=new HashMap<>();
                    movie.put("title",title);
                    movie.put("poster_path",image);
                    movie.put("release_date",year);
                    watchlist.add(movie);
                }while(c.moveToNext());
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter=new MyAdapter(WatchlistActivity.this,R.layout.row,watchlist);
        lv_watchlist.setAdapter(adapter);
    }
}

}
