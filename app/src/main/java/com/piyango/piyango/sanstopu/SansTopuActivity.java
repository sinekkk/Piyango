package com.piyango.piyango.sanstopu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.piyango.R;
import com.piyango.json.CekilisRequest;
import com.piyango.json.FetchJsonTask;
import com.piyango.json.RequestManager;
import com.piyango.model.PiyangoSonuc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SansTopuActivity extends Activity {
    private static ArrayList<String> tarihList = new ArrayList<String>();
    private ProgressDialog mConnectionProgressDialog;
    private String cikanRakam = "";
    private String piyangoTur = "sanstopu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanstopu);

        RequestManager.getSonucTarihleri(new CekilisRequest.Callback<String>() {
            @Override
            public void onFail() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String obj) {
                JSONArray mJsonArray = null;
                try {
                    mJsonArray = new JSONArray(obj);
                    tarihList = new ArrayList<>();
                    JSONObject mJsonObject ;
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        tarihList.add(mJsonObject.getString("tarih"));
                        mJsonObject.getString("tarihView");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(SansTopuActivity.this, android.R.layout.simple_spinner_item, tarihList);
                Spinner spinner = (Spinner) findViewById(R.id.sanTopuTarihSpinner);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        RequestManager.getPiyangoSonuc(new FetchJsonTask.Callback<PiyangoSonuc>() {
                            @Override
                            public void onFail() {

                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(PiyangoSonuc obj) {

                                String [] rakam = obj.data.rakamlarNumaraSirasi.split("-");
                                String [] rakam1 = obj.data.rakamlar.split("#");
                                TextView tv = (TextView) findViewById(R.id.numaralarTextView);
                                tv.setText(rakam[0]+rakam[1]+rakam[2]+rakam[3]+rakam[4]+"+ "+rakam1[5]);

                            }
                        },piyangoTur,(String)parent.getAdapter().getItem(position));

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }, piyangoTur);
    }
}
