package com.piyango.piyango;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.piyango.R;

public class MainActivity extends Activity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);



		Button onBtn = (Button)findViewById(R.id.onNumaraLaunchBtn);
        onBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OnNumaraActivity.class);
                startActivity(intent);
            }

        });

        Button sBtn = (Button)findViewById(R.id.sayisalLaunchBtn);
        sBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        Button mpBtn = (Button)findViewById(R.id.milliPiyangoLaunchBtn);
        mpBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        Button stBtn = (Button)findViewById(R.id.sansTopuLaunchBtn);
        stBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        Button slBtn = (Button)findViewById(R.id.superLotoLaunchBtn);
        slBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		return true;
	}

}
