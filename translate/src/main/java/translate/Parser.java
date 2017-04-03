package translate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

	public static void main(String[] args) {

		String filename = "./subtitles/template.srt";

		List<String> srt = read(filename);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < srt.size(); i++) {

			if (isText(srt.get(i))) {
				// String str = String.format("%s # %s\n", i, srt.get(i));
				// sb.append(str);
				// if (sb.length() > 1000){
				// System.out.println(sb.toString());
				// System.exit(0);
				// }
				System.out.println(md5sum(srt.get(i).trim()));
			} else {
				System.out.println(srt.get(i));
			}
		}
	}

	private static String md5sum(String str) {
		try {
			byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());

			BigInteger bigInt = new BigInteger(1, digest);
			return bigInt.toString(16);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean isText(String line) {

		//
		if (line.trim().matches("[\\d]+")) {
			return false;
		}

		// 00:40:54,119 --> 00:40:57,054
		if (line.trim().matches("^[\\d]*:[\\d]*:[\\d]*,[\\d]* --> [\\d]*:[\\d]*:[\\d]*,[\\d]*")) {
			return false;
		}

		//
		if (line.trim().length() == 0) {
			return false;
		}

		return true;
	}

	private static List<String> read(String filename) {
		BufferedReader buffer = null;

		List<String> s = new ArrayList<String>();
		String line = null;

		try {
			buffer = new BufferedReader(new FileReader(new File(filename)));

			while ((line = buffer.readLine()) != null) {
				s.add(String.format("%s", line));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;

	}

}
