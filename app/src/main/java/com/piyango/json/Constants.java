package com.piyango.json;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.webkit.MimeTypeMap;


public class Constants {
//http://www.millipiyango.gov.tr/sonuclar/cekilisler/sayisal/20131214.json
	final public static String PROD_URL = "http://www.millipiyango.gov.tr/";
	final public static String BASE_URL = "http://www.millipiyango.gov.tr/";
	final public static String CEKILIS_URL = "http://www.millipiyango.gov.tr/sonuclar/cekilisler/";

	final public static long oneMinute = 60000; 
	final public static String SENDER_ID = "*****967580067239";//developer@
		
	final public static String PIYANGO_SHAREDPREFS= "com.piyango.app";

	final public static String SANSTOPU_BENIM_NUMARALARIM_SHAREDPREFS= "sanstopuBenimNumaralarim";
	
	final public static SimpleDateFormat DateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ROOT);
	final public static SimpleDateFormat DateFormat = new SimpleDateFormat("ddMMyyyy", Locale.ROOT);
	final public static SimpleDateFormat YYYYmmdd_Format = new SimpleDateFormat("yyyyMMdd", Locale.ROOT);
	final public static SimpleDateFormat ShortDateFormat = new SimpleDateFormat("M-d-yyyy", Locale.ROOT);
	
	final static DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(new Locale("tr", "TR"));

	final public static DecimalFormat tutarFormat = new DecimalFormat("###,###.##",symbols);
	final public static DecimalFormat adetFormat = new DecimalFormat("###,###",symbols);

	public static String getMimeType(String url) {
		String extension = url.substring(url.lastIndexOf("."));
		String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extension);
		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
				mimeTypeMap);
		return mimeType;
	}
	public static String thousandsSeperator(String s){
		return String.format("%,14.2f", s);		
	}
	public static String[] returnCityNames(int region) {
		switch (region) {
		default:// tüm bölgeler
			return new String[] { "Tüm Bölgeler" };
		case 1:// akdeniz
			return new String[] { "Tüm Şehirler", "Adana", "Antalya", "Hatay",
					"Burdur", "Isparta", "Mersin", "Kahramanmaraş", "Osmaniye" };
		case 2:// doğu anadolu
			return new String[] { "Tüm Şehirler", "Ağrı", "Bingöl", "Bitlis",
					"Elazığ", "Erzincan", "Erzurum", "Hakkari", "Kars",
					"Malatya", "Muş", "Tunceli", "Van", "Ardahan", "Iğdır" };
		case 3: // ege
			return new String[] { "Tüm Şehirler", "Afyon", "Aydın", "Denizli",
					"İzmir", "Kütahya", "Manisa", "Muğla", "Uşak" };
		case 4:
			return new String[]// Güney Doğu Anadolu
			{ "Tüm Şehirler", "Adıyaman", "Diyarbakır", "Gaziantep", "Mardin",
					"Siirt", "Şanlıurfa", "Batman", "Şırnak", "Kilis" };
		case 5:
			return new String[]// "İç Anadolu"
			{ "Tüm Şehirler", "Ankara", "Çankırı", "Eskişehir", "Kayseri",
					"Kırşehir", "Konya", "Nevşehir", "Niğde", "Sivas",
					"Yozgat", "Aksaray", "Karaman", "Kırıkkale" };
		case 6:
			return new String[]// karadeniz
			{ "Tüm Şehirler", "Amasya", "Artvin", "Bolu", "Çorum", "Giresun",
					"Gümüşhane", "Kastamonu", "Ordu", "Rize", "Samsun",
					"Sinop", "Tokat", "Trabzon", "Zonguldak", "Bayburt",
					"Bartın", "Karabük", "Düzce" };
		case 7:
			return new String[] { "Tüm Şehirler", "Balıkesir", "Bilecik",
					"Bursa", "Çanakkale", "Edirne", "İstanbul", "Kırklareli",
					"Kocaeli", "Sakarya", "Tekirdağ", "Yalova" };
		}
	}

	public static String[] returnCityCodes(int region) {
		switch (region) {
		default:// tüm bölgeler
			return new String[] { "00" };
		case 1:// akdeniz
			return new String[] { "000", "001", "007", "031", "015", "032",
					"033", "046", "080" };

		case 2:// doğu anadolu
			return new String[] { "000", "004", "012", "013", "023", "024",
					"025", "030", "036", "044", "049", "062", "065", "075",
					"076" };

		case 3: // ege
			return new String[] { "000", "003", "009", "020", "035", "043",
					"045", "048", "064" };
		case 4:
			return new String[] { "000", "002", "021", "027", "047", "056",
					"063", "072", "073", "079" };
		case 5:
			return new String[] { "000", "006", "018", "026", "038", "040",
					"042", "050", "051", "058", "066", "068", "070", "071" };
		case 6:
			return new String[] { "000", "005", "008", "014", "019", "028",
					"029", "037", "052", "053", "055", "057", "060", "061",
					"067", "069", "074", "078", "081" };
		case 7:
			return new String[] { "000", "010", "011", "016", "017", "022",
					"034", "039", "041", "054", "059", "077" };
		}

	}
	public static String getFormatted(String s){
		String temp = s;
		try{
			int point = s.lastIndexOf(".");
			if(point != -1){
				if(s.length()<= point+3){
					temp = s.substring(0,point+3);
				}					
			}
				
		}catch(Exception ex){}
		
		return temp;
	}
	public static String formatTutar(String s){
		String temp = s;
		try{
			temp = tutarFormat.format(Double.parseDouble(s));			
		}catch(Exception e){}
		
		return temp;
	}
	public static String formatNumber(String s){
		String temp = s;
		try{
			temp = adetFormat.format(Integer.parseInt(s));			
		}catch(Exception e){}		
		return temp;
	}
	public static String[] returnRegionNames(){
		return new String []{ "Bölge Seçiniz...", "Akdeniz","Doğu Anadolu","Ege","Güney Doğu Anadolu","İç Anadolu","Karadeniz","Marmara" };		
	}
};
