package org.avalon.hunterz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static final Date parse(String source, String pattern) {
		try {
			return (new SimpleDateFormat(pattern)).parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String format(Date source, String pattern) {
		try {
			return (new SimpleDateFormat(pattern)).format(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
