package org.avalon.hunterz.tests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

	public static void main(String[] args) {

		//String base = "Uploaded 01-23&nbsp;2012,";
		String base = "Uploaded 08-31&nbsp;15:38,";

		Pattern p = Pattern.compile("Uploaded[\\s\\d-]+");
		Matcher m = p.matcher(base);

		if (m.find()) {
			System.out.println(base.substring(m.start(), m.end()));
		}

	}

}
