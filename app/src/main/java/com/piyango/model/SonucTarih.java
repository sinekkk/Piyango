package com.piyango.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by koray on 14/02/16.
 */
public class SonucTarih {

    public List<SonucTarihModel> data;

    public class SonucTarihModel{
        public String tarihView ,tarih;

    }

}
