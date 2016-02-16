package com.piyango.piyango.sayisal;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.piyango.R;

/**
 * Created by Sinek on 16.02.2016.
 */
public class SayisalActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
