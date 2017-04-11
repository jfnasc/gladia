package translate;

import java.io.File;
import java.io.FileFilter;

public class Main {

	public static void main(String[] args) {

		File dir = new File("./subtitles");

		for (File str : dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".srt");
			}
		})) {
			Parser p = new Parser();
			p.parse("./subtitles", str.getName());
		}

	}

}
