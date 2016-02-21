package com.piyango.piyango.sanstopu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

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

        mConnectionProgressDialog = new ProgressDialog(SansTopuActivity.this);
        mConnectionProgressDialog.setCancelable(false);
        mConnectionProgressDialog.setMessage("Tarih bilgileri yükleniyor...");

        RequestManager.getSonucTarihleri(new CekilisRequest.Callback<String>() {
            @Override
            public void onFail() {
                mConnectionProgressDialog.dismiss();
            }

            @Override
            public void onStart() {
                mConnectionProgressDialog.show();
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
                                Log.d("MilliPiyango", "HATA!");
                            }

                            @Override
                            public void onStart() {
                                Log.d("MilliPiyango", "ONSTART");
                            }

                            @Override
                            public void onSuccess(PiyangoSonuc obj) {

                                String [] rakam = obj.data.rakamlarNumaraSirasi.split("-");
                                String [] rakam1 = obj.data.rakamlar.split("#");
                                TextView tv = (TextView) findViewById(R.id.numaralarTextView);
                                tv.setText(rakam[0]+rakam[1]+rakam[2]+rakam[3]+rakam[4]+"+ "+rakam1[5]);

                                TextView ikramiyeView = (TextView) findViewById(R.id.büyükIkramiye2);
                                TextView kisiSayisiView = (TextView) findViewById(R.id.kisiSayisi);
                                TextView kazananIller = (TextView) findViewById(R.id.kazananIller);
                                ikramiyeView.setText(obj.data.bilenKisiler.get(7).kisiBasinaDusenIkramiye);
                                kisiSayisiView.setText(obj.data.bilenKisiler.get(7).kisiSayisi);
                                //kazananIller.setText(obj.data.buyukIkramiyeKazananIl);

                            }
                        },piyangoTur,(String)parent.getAdapter().getItem(position));



                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                mConnectionProgressDialog.dismiss();
            }
        }, piyangoTur);

        ToggleButton detay = (ToggleButton) findViewById(R.id.cekilisDetayButton);

        detay.setChecked(true);
        detay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout detaylayout = (LinearLayout) ((View)v.getParent()).findViewById(R.id.detayLayout);
                if(!((ToggleButton) v).isChecked()){
                    detaylayout.setVisibility(View.VISIBLE);
                }else{
                    detaylayout.setVisibility(View.GONE);
                }
            }
        });


    }
}
