package com.piyango.piyango.sanstopu;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.piyango.R;
import com.piyango.piyango.Helper;
import com.piyango.piyango.InputFilterMinMax;
import com.piyango.json.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Sinek on 25.02.2016.
 */
public class YeniNumara extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanstopu_yeni_numara);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        EditText et1 = (EditText) findViewById(R.id.sanstopuYeniNumara1);
        et1.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "34")});
        EditText et2 = (EditText) findViewById(R.id.sanstopuYeniNumara2);
        et2.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "34")});
        EditText et3 = (EditText) findViewById(R.id.sanstopuYeniNumara3);
        et3.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "34")});
        EditText et4 = (EditText) findViewById(R.id.sanstopuYeniNumara4);
        et4.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "34")});
        EditText et5 = (EditText) findViewById(R.id.sanstopuYeniNumara5);
        et5.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "34")});
        EditText et6 = (EditText) findViewById(R.id.sanstopuYeniNumara6);
        et6.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "14")});

    }
    public void yeniNumaraEkle(View v){
        EditText editText1 = (EditText) findViewById(R.id.sanstopuYeniNumara1);
        EditText editText2 = (EditText) findViewById(R.id.sanstopuYeniNumara2);
        EditText editText3 = (EditText) findViewById(R.id.sanstopuYeniNumara3);
        EditText editText4 = (EditText) findViewById(R.id.sanstopuYeniNumara4);
        EditText editText5 = (EditText) findViewById(R.id.sanstopuYeniNumara5);
        EditText editText6 = (EditText) findViewById(R.id.sanstopuYeniNumara6);

        String value = editText1.getText().toString() + "#" + editText2.getText().toString() + "#" + editText3.getText().toString() + "#" + editText4.getText().toString() + "#" + editText5.getText().toString() + "#" + editText6.getText().toString();
        Set<String> c =(Helper.getDefaults(Constants.SANSTOPU_BENIM_NUMARALARIM_SHAREDPREFS, v.getContext()));
        if (c == null)
            c = new HashSet<String>();
        c.add(value);
        Helper.setDefaults(Constants.SANSTOPU_BENIM_NUMARALARIM_SHAREDPREFS,c, v.getContext());
        Iterator it = c.iterator();
        while(it.hasNext()) {
            Log.d("daada",it.next()+"");
        }
    }



}
