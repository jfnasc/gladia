package org.gladia;

import java.io.File;
import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		String path = "D:/projetos/tmp/Lazarus/redux/projetos/tmp/Lazarus";
		try {
			for (File dir : (new File(path)).listFiles()) {
				if (dir.isDirectory()) {
					System.out.println(Utils.extrairUltimaParte(dir.getAbsolutePath(), File.separator));
					ZipUtils.zip(Utils.extrairUltimaParte(dir.getAbsolutePath(), File.separator), path);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
