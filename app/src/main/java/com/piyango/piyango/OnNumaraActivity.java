package com.piyango.piyango;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.piyango.R;
import com.piyango.json.CekilisRequest;
import com.piyango.json.FetchJsonTask;
import com.piyango.json.RequestManager;
import com.piyango.model.SayisalSonuc;
import com.piyango.model.SonucTarih;

import org.json.JSONArray;
import org.json.JSONObject;

public class OnNumaraActivity extends Activity {
    public static ArrayList<String> tarihList = new ArrayList<String>();
    private ProgressDialog mConnectionProgressDialog;
    private String cikanRakam = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mConnectionProgressDialog = new ProgressDialog(OnNumaraActivity.this);
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

                    ArrayAdapter<CharSequence> adapter = new ArrayAdapter(OnNumaraActivity.this, android.R.layout.simple_spinner_item, tarihList);
                    Spinner spn = (Spinner) findViewById(R.id.tarihSpinner);
                    spn.setAdapter(adapter);

                    spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            RequestManager.getSayisal(new FetchJsonTask.Callback<SayisalSonuc>() {
                                @Override
                                public void onFail() {
                                    Log.d("MilliPiyango", "HATA!");
                                }

                                @Override
                                public void onStart() {
                                    Log.d("MilliPiyango", "ONSTART");
                                }

                                @Override
                                public void onSuccess(final SayisalSonuc obj) {
                                    updateDash(obj);
                                }
                            }, parent.getAdapter().getItem(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            RequestManager.getSayisal(new FetchJsonTask.Callback<SayisalSonuc>() {
                                @Override
                                public void onFail() {
                                    Log.d("MilliPiyango", "HATA!");
                                }

                                @Override
                                public void onStart() {
                                    Log.d("MilliPiyango", "ONSTART");
                                }

                                @Override
                                public void onSuccess(final SayisalSonuc obj) {
                                    updateDash(obj);
                                }
                            }, parent.getAdapter().getItem(0).toString());
                        }

                    });

                } catch (Exception e) {

                }
                mConnectionProgressDialog.dismiss();
            }
        }, "onnumara");


        Button btn = (Button)findViewById(R.id.requestButton);
        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String tempStr = "";
                tempStr += ((EditText) findViewById(R.id.editText)).getText().toString().trim()+"#";
                tempStr += ((EditText) findViewById(R.id.editText2)).getText().toString().trim()+"#";
                tempStr += ((EditText) findViewById(R.id.editText3)).getText().toString().trim()+"#";
                tempStr += ((EditText) findViewById(R.id.editText4)).getText().toString().trim()+"#";
                tempStr += ((EditText) findViewById(R.id.editText5)).getText().toString().trim()+"#";
                tempStr += ((EditText) findViewById(R.id.editText6)).getText().toString().trim();
                ((ProgressBar) findViewById(R.id.kacBildimBar)).setProgress(0);
                ((ProgressBar) findViewById(R.id.kacBildimBar)).setProgress(getMatches(cikanRakam, tempStr));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    public void updateDash( SayisalSonuc obj){
        cikanRakam = obj.data.rakamlar;
        Log.d("MilliPiyango", obj.data.cekilisTarihi + "\t" + obj.data.rakamlarNumaraSirasi );
        TextView v1 = (TextView) findViewById(R.id.sonucTextView);
        v1.setText(obj.data.rakamlarNumaraSirasi);
        ((TextView) findViewById(R.id.tarihTextView)).setText(obj.data.cekilisTarihi);
        String tempStr = "";
        tempStr += ((EditText) findViewById(R.id.editText)).getText().toString().trim()+"#";
        tempStr += ((EditText) findViewById(R.id.editText2)).getText().toString().trim()+"#";
        tempStr += ((EditText) findViewById(R.id.editText3)).getText().toString().trim()+"#";
        tempStr += ((EditText) findViewById(R.id.editText4)).getText().toString().trim()+"#";
        tempStr += ((EditText) findViewById(R.id.editText5)).getText().toString().trim()+"#";
        tempStr += ((EditText) findViewById(R.id.editText6)).getText().toString().trim();
        ((ProgressBar) findViewById(R.id.kacBildimBar)).setProgress(0);

        ((ProgressBar) findViewById(R.id.kacBildimBar)).setProgress(getMatches(obj.data.rakamlar, tempStr));
    }

    public int getMatches(String cikanRakamlar, String testRakamlar){
        String[] cikan = cikanRakamlar.split("#");
        String[] test = testRakamlar.split("#");
        if(cikan.length != test.length)
            return -1;

        int count = 0;
        for (int i = 0 ; cikan.length > i ; i++){
            for (int k = 0 ; test.length > k ; k++) {
                if (Integer.parseInt(cikan[i])==Integer.parseInt(test[k])) {
                    count++;
                    break;
                }
            }
        }

        Log.d("MilliPiyango", count+"");
        return count;
    }

}
