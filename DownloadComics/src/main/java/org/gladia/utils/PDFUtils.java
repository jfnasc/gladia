package org.gladia.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFUtils {

    public static final void convertJpegToPdf(String filename, String dir) {
        if (dir == null) {
            return;
        }

        File fdir = new File(filename.substring(0, filename.lastIndexOf(File.separator))); 
        if (!fdir.exists() && !fdir.mkdirs()) {
            throw new RuntimeException("Nao foi possivel criar o diretorio!");
        }

        Document document = new Document();

        try {
            FileOutputStream fos = new FileOutputStream(filename);

            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.setPdfVersion(PdfWriter.VERSION_1_5);
            //writer.setViewerPreferences(PdfWriter.PageModeFullScreen);

            writer.open();

            File[] files = (new File(dir)).listFiles();
            Arrays.sort(files);

            document.open();

            for (File file : files) {
                Image image = Image.getInstance(file.getAbsolutePath());
                System.out.println(file.getAbsolutePath());
                addImage(document, image);
            }

            document.close();

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void addImage(Document document, Image image) throws Exception {
        Image img = Image.getInstance(image);
        document.setPageSize(img);
        document.newPage();
        img.setAbsolutePosition(0, 0);
        document.add(img);
    }

    public static void main(String... args) {

        convertJpegToPdf("/projetos/quadrinhos/DC/Mulher Maravilha/A Lenda da Mulher Maravilha/pdf/1.pdf",
                "/projetos/quadrinhos/DC/Mulher Maravilha/A Lenda da Mulher Maravilha/edicao-001");

    }
}
