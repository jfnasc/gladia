package org.gladia;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResizer {

	public static void main(String[] args) throws IOException {

		File folder = new File("D:/projetos/tmp/Guerras Secretas/0000");
		File[] listOfFiles = folder.listFiles();
		System.out.println("Total No of Files:" + listOfFiles.length);
		BufferedImage img = null;
		BufferedImage tempPNG = null;
		BufferedImage tempJPG = null;
		File newFilePNG = null;
		File newFileJPG = null;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				img = ImageIO.read(listOfFiles[i].getAbsoluteFile());
				System.out.println(img.getWidth() + "x" + img.getHeight());
				System.out.println(img.getWidth() * img.getHeight());

				tempJPG = resizeImage(img, img.getWidth(), img.getHeight());
				newFileJPG = new File("D:/projetos/tmp/Guerras Secretas/0000/" + listOfFiles[i].getName().toLowerCase());
				ImageIO.write(tempJPG, "jpg", newFileJPG);
			}
		}
		System.out.println("DONE");
	}

	/**
	 * This function resize the image file and returns the BufferedImage object
	 * that can be saved to file system.
	 */
	public static BufferedImage resizeImage(final Image image, int width, int height) {
		
		int targetw = width / 2;
		int targeth = height / 2;

		final BufferedImage bufferedImage = new BufferedImage(targetw, targeth, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.drawImage(image, 0, 0, targetw, targeth, null);
		graphics2D.dispose();

		return bufferedImage;
	}
}
