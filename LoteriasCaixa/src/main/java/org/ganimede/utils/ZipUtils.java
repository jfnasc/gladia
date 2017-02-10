package org.ganimede.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

public class ZipUtils {

    private static Logger LOGGER = Logger.getLogger(ZipUtils.class);

    /**
     * Unzip it
     * 
     * @param zipFile
     *            input zip file
     * @param output
     *            zip file output folder
     */
    public static void unzip(String zipFile, String outputFolder) {

        byte[] buffer = new byte[1024];

        try {

            // create output directory is not exists
            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }

            // get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));

            // get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                LOGGER.info("file unzip : " + newFile.getAbsoluteFile());

                // create all non exists folders
                // else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            LOGGER.info("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ZipUtils.unzip("/home/josen/megasena.zip", "/projetos/github/gladia/LoteriasCaixa/arquivos");
    }
}