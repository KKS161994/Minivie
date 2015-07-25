package com.test.rajat.minivie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    private ListView list_movies;
    private MyAdapter adapter;
    private SearchView searchView;
    private DetailsActivity details;
    TextView tv_title,tv_releaseYear;
    Typeface face;
    JSONParser parser;
    ArrayList<HashMap<String,String>> movies_list;
    private static String url="https://api.themoviedb.org/3/search/movie?api_key=ee0ee24620c88da78edb61892d8bf78b&&query=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        list_movies = (ListView) findViewById(R.id.list_movies);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_releaseYear = (TextView) findViewById(R.id.tv_releaseYear);
        list_movies.setOnItemClickListener(this);
        handleIntent(getIntent());
        parser = new JSONParser();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
         searchView=
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
//                TextView textView=(TextView)findViewById(R.id.tvb_title);
//                textView.setText(query);
                String newquery=url+query.replaceAll(" ","%20");
                new RetrieveJSON().execute(newquery);
                searchView.clearFocus();
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        switch(id)
        {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        details=new DetailsActivity();

        HashMap<String,String> movie_details=movies_list.get(position);
        Intent i=new Intent(this,DetailsActivity.class);
        i.putExtra("movie_details", movie_details);
        startActivity(i);


    }

    class RetrieveJSON extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            movies_list=new ArrayList<HashMap<String,String>>();
        }

        @Override
        protected String doInBackground(String... params) {

            String str_json= parser.getJSONFromUrl(params[0]);
            Log.d("URL",params[0]);
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
                    HashMap<String,String> movie=new HashMap<>();
                    movie.put("title",title);
                    movie.put("release_date",release_date);
                    movie.put("overview",overview);
                    movie.put("rating",rating);
                    movie.put("poster_path",poster_path);
                    movie.put("backdrop_path",backdrop_path);
                    movie.put("vote_average",vote_average);
                    movies_list.add(movie);
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
            adapter=new MyAdapter(MainActivity.this,R.layout.row,movies_list);
            list_movies.setAdapter(adapter);
        }
    }
}
