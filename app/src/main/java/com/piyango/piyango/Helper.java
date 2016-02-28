package com.piyango.piyango;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Helper {

    public static String formatTutar(String tutar){
        Double dTutar = Double.parseDouble(tutar);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(dTutar);
    }
    public static void setDefaults(String key, Set<String> value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.commit();
        editor.putStringSet(key, value);
        editor.commit();



    }

    public static Set getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getStringSet(key, null);
    }

    public static int getMatches(String cikanRakamlar, String testRakamlar){
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
    public static ArrayList<Integer> getEslesme(String[] cıkanRakamlar, String[] gelenRakamlar, String tur){
        int artisiz = 0;
        int artili = 0;
        String[] ilkBesli = new String[cıkanRakamlar.length-1];
        Integer[] map = {-1,0,1,3,5,7,-1,-1,-1,2,4,6};
        for (int i = 0; i<cıkanRakamlar.length-1;i++)
            ilkBesli[i] = cıkanRakamlar[i];
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if (tur.equals("sanstopu")){
            for (int i = 0; i<gelenRakamlar.length-1; i++){
                if(tekKarsilastirma(Integer.parseInt(gelenRakamlar[i]),ilkBesli)) {

                    colors.add(i, Color.GREEN);
                    artisiz ++;
                }
                else
                    colors.add(i,Color.RED);
            }
            if(Integer.parseInt(gelenRakamlar[gelenRakamlar.length-1]) == Integer.parseInt(cıkanRakamlar[cıkanRakamlar.length -1])) {
                colors.add(gelenRakamlar.length - 1,Color.GREEN);
                artili ++;
            }
            else
                colors.add(gelenRakamlar.length - 1,Color.RED);
            if(artili == 1)
                colors.add(gelenRakamlar.length,map[artisiz]);
            else
                colors.add(gelenRakamlar.length,map[artisiz+6]);


        }
        return colors;
    }
    public static boolean tekKarsilastirma(int tekGelenRakam, String[] cikanRakamlar){
        for(int i = 0; i < cikanRakamlar.length; i++) {
            if (tekGelenRakam == Integer.parseInt(cikanRakamlar[i])){
                return true;
            }
        }
        return false;
    }


}
