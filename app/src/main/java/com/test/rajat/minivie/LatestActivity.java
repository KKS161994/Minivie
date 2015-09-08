package com.test.rajat.minivie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rajat on 9/8/2015.
 */
public class LatestActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private ListView lv_latest;
    private MyAdapter adapter_latest;
    private DetailsActivity details;
    private TextView tv_title,tv_releaseYear;
    private final static CharSequence hint="Search OMDB";
    Typeface face;
    private JSONParser parser;
    private ArrayList<HashMap<String,String>> list_latest;
    private static String url="http://api.themoviedb.org/3/movie/now_playing?api_key=ee0ee24620c88da78edb61892d8bf78b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_latest,frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        lv_latest = (ListView) findViewById(R.id.lv_latest);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_releaseYear = (TextView) findViewById(R.id.tv_releaseYear);
        lv_latest.setOnItemClickListener(this);
       // handleIntent(getIntent());
        parser = new JSONParser();
        new RetrieveJSON().execute(url);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        details=new DetailsActivity();
        HashMap<String,String> movie_details=list_latest.get(position);
        Intent i=new Intent(this,DetailsActivity.class);
        i.putExtra("movie_details", movie_details);
        startActivity(i);
    }
    private class RetrieveJSON extends AsyncTask<String,String,String>
    {
          private ProgressDialog dialog = new ProgressDialog(LatestActivity.this);
//        Timer timer=new Timer();
        String id;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list_latest=new ArrayList<HashMap<String,String>>();
            this.dialog.setMessage("Loading Latest Movies...");
            this.dialog.show();
//            TimerTask task=new TimerTask() {
//                @Override
//                public void run() {
//                    RetrieveJSON.this.dialog.dismiss();
//                }
//            };
//            timer.schedule(task,5000);

        }

        @Override
        protected String doInBackground(String... params) {
            String str_json= parser.getJSONFromUrl(params[0]);
            Log.d("URL", params[0]);
            try {
                JSONObject json=new JSONObject(str_json);
                JSONArray jsonArray=new JSONArray(json.getString("results"));
                Log.d("LEN",Integer.toString(jsonArray.length()));
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject tmp=jsonArray.getJSONObject(i);
                    String title=tmp.getString("title");
                    String release_date=tmp.getString("release_date");
                    String overview=tmp.getString("overview");
                    String rating=tmp.getString("vote_average");
                    String poster_path=tmp.getString("poster_path");
                    String backdrop_path=tmp.getString("backdrop_path");
                    String vote_average=tmp.getString("vote_average");
                     id=tmp.getString("id");
                    HashMap<String,String> movie=new HashMap<>();
                    movie.put("title",title);
                    movie.put("release_date",release_date);
                    movie.put("overview",overview);
                    movie.put("rating",rating);
                    movie.put("poster_path",poster_path);
                    movie.put("backdrop_path",backdrop_path);
                    movie.put("vote_average",vote_average);
                    movie.put("id",id);
                    list_latest.add(movie);
                }

            }

            catch(JSONException e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            adapter_latest=new MyAdapter(LatestActivity.this,R.layout.row,list_latest);
            lv_latest.setAdapter(adapter_latest);
            Toast.makeText(LatestActivity.this,id,Toast.LENGTH_SHORT).show();
        }
    }
}
