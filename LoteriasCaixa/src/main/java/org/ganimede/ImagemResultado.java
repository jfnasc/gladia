package org.ganimede;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class ImagemResultado {
    
    private static DecimalFormat df = new DecimalFormat("000000");
    
    public static void write(TiposConcurso tpConcurso, Integer nuConcurso, Integer[] dezenas) {
        try {
            int width = 16;
            int height = width / 2;

            BufferedImage bi = new BufferedImage(width * 10, height * tpConcurso.nuDezenas / 10,
                    BufferedImage.TYPE_INT_ARGB);

            Graphics2D ig2 = bi.createGraphics();

            ig2.setPaint(Color.white);
            ig2.fillRect(0, 0, width * 10, height * 8);

            ig2.setPaint(Color.black);

            for (int dezena : dezenas) {
                int x = (dezena - 1) * width;
                if (dezena > 10) {
                    x = ((dezena - 1) % 10) * width;
                }

                int y = 0;
                if (dezena > 10) {
                    y = (dezena / 10) * height;
                    if (dezena % 10 == 0) {
                        y = ((dezena / 10) - 1) * height;
                    }
                }

                System.out.print(String.format("%s\t%s\n", x, y));

                ig2.fillRect(x, y, width, height);
            }

            ImageIO.write(bi, "PNG", new File(String.format("/home/josen/%s-%s.png", tpConcurso.sigla, df.format(nuConcurso))));

        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }
}
