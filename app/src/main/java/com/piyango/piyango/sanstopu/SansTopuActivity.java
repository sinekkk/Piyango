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

import com.piyango.R;
import com.piyango.json.CekilisRequest;
import com.piyango.json.FetchJsonTask;
import com.piyango.json.RequestManager;
import com.piyango.model.PiyangoSonuc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SansTopuActivity extends Activity {
    public static ArrayList<String> tarihList = new ArrayList<String>();
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
                try {
                    JSONArray mJsonArray = new JSONArray(obj);
                    ArrayList<String> tarihList = new ArrayList<>();
                    JSONObject mJsonObject ;
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        tarihList.add(mJsonObject.getString("tarih"));
                        mJsonObject.getString("tarihView");
                    }

                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(SansTopuActivity.this, android.R.layout.simple_spinner_item, tarihList);
                    Spinner spn = (Spinner) findViewById(R.id.tarihSpinner);
                    spn.setAdapter(adapter);

                    spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                                public void onSuccess(final PiyangoSonuc obj) {
                                    updateDash(obj);
                                }
                            }, piyangoTur, parent.getAdapter().getItem(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
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
                                public void onSuccess(final PiyangoSonuc obj) {
                                    updateDash(obj);
                                }
                            }, piyangoTur, parent.getAdapter().getItem(0).toString());
                        }

                    });

                } catch (Exception e) {

                }
                mConnectionProgressDialog.dismiss();
            }
        }, "onnumara");
    }
}
