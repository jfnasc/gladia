package translate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

	private TranslateText service;

	public void parse(String dir, String filename) {

		// diretorio destino
		new File(dir + "/translated").mkdirs();

		System.out.println(filename);

		List<String> srt = read(dir, filename);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < srt.size(); i++) {
			if (isText(srt.get(i))) {
				sb.append(i + "#" + srt.get(i) + "\n");
				if (i == 10) {
					System.out.println(getService().translate(sb.toString()));
					System.exit(0);
				}
			}
		}

		writeFile(dir + "/translated", filename, sb);
	}

	public void writeFile(String dir, String filename, StringBuilder sb) {
		System.out.println(dir);
		System.out.println(filename);
		FileWriter fw = null;

		try {
			fw = new FileWriter(new File(dir + File.separator + filename));
			fw.write(sb.toString());
			fw.flush();
		} catch (IOException e) {
			try {
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public String md5sum(String str) {
		try {
			byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes());

			BigInteger bigInt = new BigInteger(1, digest);
			return bigInt.toString(16);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isText(String line) {

		//
		if (line.matches("[\\S\\d]*")) {
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

	public List<String> read(String dir, String filename) {
		BufferedReader buffer = null;

		List<String> s = new ArrayList<String>();
		String line = null;

		try {
			buffer = new BufferedReader(new FileReader(new File(dir + File.separator + filename)));

			while ((line = buffer.readLine()) != null) {
				s.add(String.format("%s", line));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;

	}

	public TranslateText getService() {
		if (this.service == null) {
			this.service = new TranslateText();
		}
		return service;
	}
}
