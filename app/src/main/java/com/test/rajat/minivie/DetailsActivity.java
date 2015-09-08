package com.test.rajat.minivie;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import db.WatchlistOpenHelper;

/**
 * Created by Rajat on 7/16/2015.
 */
public class DetailsActivity extends BaseActivity {
    private HashMap<String,String> movie_details;
    private ImageView ivPosterImage;
    private TextView tv_title_b,tvSynopsis,tvVote,tvReleaseDate;
    private WatchlistOpenHelper helper;
    private SQLiteDatabase db_watchlist;
    private JSONParser dparser;
    private ShareActionProvider shareActionProvider;
    private Intent shareIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_movie_details, frameLayout);
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);
        Intent intent=getIntent();
        movie_details=(HashMap<String,String>) intent.getSerializableExtra("movie_details");
        dparser=new JSONParser();
        shareIntent =new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Check out this movie. \n www.themoviedb.org/movie/" + movie_details.get("id") + movie_details.get("title")
        );

        ivPosterImage=(ImageView) findViewById(R.id.ivPosterImage);
        tv_title_b=(TextView) findViewById(R.id.tvTitle);
        tvReleaseDate=(TextView) findViewById(R.id.tvReleaseDate);
        tvSynopsis=(TextView) findViewById(R.id.tvSynopsis);
        tvVote=(TextView) findViewById(R.id.tvAudienceScore);
        Log.d("TAG","Created");
        setDetails();
        helper=new WatchlistOpenHelper(this);
        db_watchlist=helper.getWritableDatabase();

   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        MenuItem share=menu.findItem(R.id.menu_item_share);
        shareActionProvider= (ShareActionProvider) MenuItemCompat.getActionProvider(share);
        shareActionProvider.setShareIntent(shareIntent);
        return true;
    }

    private void setDetails(){
        tvSynopsis.setText(movie_details.get("overview"));
        tv_title_b.setText(movie_details.get("title"));
        tvVote.setText(movie_details.get("vote_average"));
        tvReleaseDate.setText(movie_details.get("release_date"));
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500"+movie_details.get("poster_path")).into(ivPosterImage);

    }
    public void watchtrailer(View v){
        new TrailerPath().execute();
    }
    public void addMovie(View v){
        ContentValues values=new ContentValues();
        values.put("title",movie_details.get("title"));
        values.put("image",movie_details.get("poster_path"));
        values.put("year",movie_details.get("release_date"));
        try {
            db_watchlist.insert("watchlist", null, values);
            Toast.makeText(this,"Movie added",Toast.LENGTH_SHORT).show();

        }
        catch(Exception e){
            Toast.makeText(this,"Movie already in watchlist",Toast.LENGTH_SHORT).show();
        }
        finally {
            db_watchlist.close();
        }
    }
private class TrailerPath extends AsyncTask<Void,Void,Void>{
   private String id,trailerpath,json_trailer_str;
    private ProgressDialog dialog=new ProgressDialog(DetailsActivity.this);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage("Connecting Youtube..");
        this.dialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        id=movie_details.get("id");
       json_trailer_str=dparser.getJSONFromUrl("http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=ee0ee24620c88da78edb61892d8bf78b");

       try{
           JSONObject json_trailer=new JSONObject(json_trailer_str);

          JSONArray results=new JSONArray(json_trailer.getString("results"));

           JSONObject json_trailerpath=results.getJSONObject(0);
           trailerpath=json_trailerpath.getString("key");

       }
       catch(JSONException e){

       }
        return null;
    }

    @Override
    protected void onPostExecute(Void avoid) {
        super.onPostExecute(avoid);
        this.dialog.dismiss();
       startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailerpath)));
     //  Toast.makeText(DetailsActivity.this,trailerpath,Toast.LENGTH_SHORT).show();
    }
}
}
