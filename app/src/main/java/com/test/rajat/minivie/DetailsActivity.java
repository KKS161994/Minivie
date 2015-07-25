package com.test.rajat.minivie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by Rajat on 7/16/2015.
 */
public class DetailsActivity extends ActionBarActivity {
    private HashMap<String,String> movie_details;
    private ImageView iv_poster_b;
    private TextView tv_title_b;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Intent intent=getIntent();
        movie_details=(HashMap<String,String>) intent.getSerializableExtra("movie_details");
        iv_poster_b=(ImageView) findViewById(R.id.iv_poster_b);
        tv_title_b=(TextView) findViewById(R.id.tv_title_b);
        Log.d("TAG","Created");
        Toast.makeText(DetailsActivity.this, "title", Toast.LENGTH_SHORT).show();
        setDetails();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setDetails(){
        tv_title_b.setText(movie_details.get("title"));
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500"+movie_details.get("poster_path")).into(iv_poster_b);

    }
}
