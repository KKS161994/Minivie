package com.test.rajat.minivie;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

/**
 * Created by Rajat on 7/16/2015.
 */
public class DetailsActivity extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(Color.parseColor("#ff9900"))
                .headerLayout(R.layout.header)
                .contentLayout(R.layout.activity_movie_details);
        setContentView(helper.createView(this));
        helper.initActionBar(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
