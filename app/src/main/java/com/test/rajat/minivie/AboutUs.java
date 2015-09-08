package com.test.rajat.minivie;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Kartikey on 9/9/2015.
 */
public class AboutUs extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.aboutus);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    startActivity(new Intent(this,MainActivity.class));
    }
}
