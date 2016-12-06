package br.com.sisfashion.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date parse(String strData) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.parse(strData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String format(Date date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Long parseAsTimeStamp(String strData) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.parse(strData).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatTimeStampAsString(Long timestamp) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(new Date(timestamp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
