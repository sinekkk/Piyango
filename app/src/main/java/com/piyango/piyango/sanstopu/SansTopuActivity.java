package com.piyango.piyango.sanstopu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.piyango.R;
import com.piyango.json.CekilisRequest;
import com.piyango.json.Constants;
import com.piyango.json.FetchJsonTask;
import com.piyango.json.RequestManager;
import com.piyango.model.PiyangoSonuc;
import com.piyango.piyango.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SansTopuActivity extends Activity {
    private static ArrayList<String> tarihList = new ArrayList<String>();
    private ProgressDialog mConnectionProgressDialog;
    private String cikanRakam = "";
    private String piyangoTur = "sanstopu";
    public static PiyangoSonuc ps;

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
                                ps = obj;

                                String [] rakam = obj.data.rakamlarNumaraSirasi.split("-");
                                String [] rakam1 = obj.data.rakamlar.split("#");
                                TextView tv = (TextView) findViewById(R.id.numaralarTextView);
                                tv.setText(rakam[0]+rakam[1]+rakam[2]+rakam[3]+rakam[4]+"+ "+rakam1[5]);

                                TextView ikramiyeView = (TextView) findViewById(R.id.buyukIkramiye);
                                TextView kisiSayisiView = (TextView) findViewById(R.id.kisiSayisi);
                                TextView kazananIller = (TextView) findViewById(R.id.kazananIller);

                                ikramiyeView.setText(Helper.formatTutar(obj.data.bilenKisiler.get(7).kisiBasinaDusenIkramiye));
                                String iller = "";
                                if(Double.parseDouble(obj.data.bilenKisiler.get(7).kisiSayisi) ==0 ) {
                                    kisiSayisiView.setText("Devretti");
                                    kazananIller.setText("");
                                }
                                else {
                                    kisiSayisiView.setText(obj.data.bilenKisiler.get(7).kisiSayisi);
                                    for (int kisi = Integer.parseInt(obj.data.bilenKisiler.get(7).kisiSayisi);kisi>0;kisi--){
                                        if(kisi > 1)
                                            iller = iller + obj.data.buyukIkrKazananIlIlceler.get(kisi-1).ilView + ",";
                                        else
                                            iller = iller + obj.data.buyukIkrKazananIlIlceler.get(kisi-1).ilView;
                                    }
                                    kazananIller.setText(iller);
                                }
                                TextView sonrakiCekilisTarihi = (TextView) findViewById(R.id.sonrakiTarihTextView);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar c = Calendar.getInstance();
                                try {
                                    c.setTime(sdf.parse(obj.data.cekilisTarihi));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                c.add(Calendar.DATE, 7);
                                sonrakiCekilisTarihi.setText(sdf.format(c.getTime()));


                                List <String> templist = new ArrayList<String>();
                                Set<String> k =Helper.getDefaults(Constants.SANSTOPU_BENIM_NUMARALARIM_SHAREDPREFS, SansTopuActivity.this);
                                if(k!=null) {
                                    Iterator it = k.iterator();
                                    while(it.hasNext()) {
                                        templist.add((String)it.next());
                                    }
                                }
                                CustomAdaptor adapter2 = new CustomAdaptor(SansTopuActivity.this, R.layout.benimnumaram_sanstopu, templist);
                                ListView lv = (ListView) findViewById(R.id.listView);
                                lv.setAdapter(adapter2);


                                Button yeniNumaraBtn = (Button) findViewById(R.id.numaraEklebtn);
                                yeniNumaraBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(SansTopuActivity.this,YeniNumara.class));
                                    }
                                });


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
                TextView buyukIkramiyeText = (TextView) ((View)v.getParent()).findViewById(R.id.buyukIkramiyeText);
                TextView buyukIkramiye = (TextView) ((View)v.getParent()).findViewById(R.id.buyukIkramiye);
                TextView kisiSayisiText = (TextView) ((View)v.getParent()).findViewById(R.id.kisiSayisiText);
                TextView kisiSayisi = (TextView) ((View)v.getParent()).findViewById(R.id.kisiSayisi);
                TextView kazananIllerText = (TextView) ((View)v.getParent()).findViewById(R.id.kazananIllerText);
                TextView kazananIller = (TextView) ((View)v.getParent()).findViewById(R.id.kazananIller);

                if(!((ToggleButton) v).isChecked()){
                    buyukIkramiyeText.setVisibility(View.VISIBLE);
                    buyukIkramiye.setVisibility(View.VISIBLE);
                    kisiSayisiText.setVisibility(View.VISIBLE);
                    kisiSayisi.setVisibility(View.VISIBLE);
                    kazananIllerText.setVisibility(View.VISIBLE);
                    kazananIller.setVisibility(View.VISIBLE);

                }else{
                    buyukIkramiyeText.setVisibility(View.GONE);
                    buyukIkramiye.setVisibility(View.GONE);
                    kisiSayisiText.setVisibility(View.GONE);
                    kisiSayisi.setVisibility(View.GONE);
                    kazananIllerText.setVisibility(View.GONE);
                    kazananIller.setVisibility(View.GONE);
                }
            }
        });


    }
    public class CustomAdaptor extends ArrayAdapter<String>{
        public CustomAdaptor(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            int count1,count2;
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.benimnumaram_sanstopu, null);
            }

            String p = getItem(position);
            String [] cikanRakamlar = ps.data.rakamlar.split("#");
            if (p != null) {
                ArrayList<TextView> tempnumaram = new ArrayList<TextView>();


                tempnumaram.add(0,(TextView) v.findViewById(R.id.benimNumaralarımNo1));
                tempnumaram.add(1,(TextView) v.findViewById(R.id.benimNumaralarımNo2));
                tempnumaram.add(2,(TextView) v.findViewById(R.id.benimNumaralarımNo3));
                tempnumaram.add(3,(TextView) v.findViewById(R.id.benimNumaralarımNo4));
                tempnumaram.add(4,(TextView) v.findViewById(R.id.benimNumaralarımNo5));
                tempnumaram.add(5,(TextView) v.findViewById(R.id.benimNumaralarımNo6));
                TextView tempikramiye = (TextView)v.findViewById(R.id.benimNumaralarımIkramiye);
                Button analiz = (Button) v.findViewById(R.id.benimNumaralarımButon);



                if(tempnumaram!= null && tempikramiye != null && analiz != null) {
                    tempikramiye.setText("0TL");
                    String[] gelenRakamlar = p.split("#");
                    ArrayList<Integer> sonuclar = Helper.getEslesme(cikanRakamlar,gelenRakamlar,"sanstopu");
                    for (int i = 0; i<6; i++) {
                        tempnumaram.get(i).setText(gelenRakamlar[i]);
                        tempnumaram.get(i).setBackgroundColor(sonuclar.get(i));
                    }
                    if(sonuclar.get(6) != -1)
                        tempikramiye.setText(ps.data.bilenKisiler.get(sonuclar.get(6)).kisiBasinaDusenIkramiye + "TL");


                    //burda hesap yap

                    //tempikramiye.setText(Helper.getMatches(ps.data.rakamlar,p));
                    analiz.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("analiz buttonu",position+"");
                        }
                    });
                }
            }
            return v;
        }
    }


}
