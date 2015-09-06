package com.test.rajat.minivie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Rajat on 7/16/2015.
 */
public class DetailsActivity extends ActionBarActivity {
    private HashMap<String,String> movie_details;
    private ImageView ivPosterImage;
    private TextView tv_title_b,tvSynopsis,tvVote,tvReleaseDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent intent=getIntent();
        movie_details=(HashMap<String,String>) intent.getSerializableExtra("movie_details");
        ivPosterImage=(ImageView) findViewById(R.id.ivPosterImage);
        tv_title_b=(TextView) findViewById(R.id.tvTitle);
        tvReleaseDate=(TextView) findViewById(R.id.tvReleaseDate);
        tvSynopsis=(TextView) findViewById(R.id.tvSynopsis);
        tvVote=(TextView) findViewById(R.id.tvAudienceScore);
        Log.d("TAG","Created");
       // Toast.makeText(DetailsActivity.this, "title", Toast.LENGTH_SHORT).show();
        setDetails();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setDetails(){
        tvSynopsis.setText(movie_details.get("overview"));
        tv_title_b.setText(movie_details.get("title"));
        tvVote.setText(movie_details.get("vote_average"));
        tvReleaseDate.setText(movie_details.get("release_date"));
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500"+movie_details.get("poster_path")).into(ivPosterImage);

    }
}
