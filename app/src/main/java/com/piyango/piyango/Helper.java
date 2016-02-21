package com.piyango.piyango;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Sinek on 21.02.2016.
 */
public class Helper {
    public static String formatTutar(String tutar){
        Double dTutar = Double.parseDouble(tutar);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return df.format(dTutar);
    }


}
