package com.piyango.json;

import com.piyango.model.SayisalSonuc;
import com.piyango.model.SonucTarih;

public class RequestManager {

	public static void getSayisal(FetchJsonTask.Callback<SayisalSonuc> c,String hafta){
		new FetchJsonTask<SayisalSonuc>(SayisalSonuc.class, "sonuclar/cekilisler/sayisal/"+hafta+".json", c).execute();
	}
//http://www.millipiyango.gov.tr/sonuclar/listCekilisleriTarihleri.php
	public static void getSayisalSonucTarihleri(FetchJsonTask.Callback<SonucTarih> c,String prm1,String tur){
		new FetchJsonTask<SonucTarih>(SonucTarih.class, "sonuclar/listCekilisleriTarihleri.php", c).execute(prm1,tur);
	}

	public static void getSonucTarihleri(CekilisRequest.Callback<String> c,String tur){
		new CekilisRequest(tur,c).execute();
	}

}
