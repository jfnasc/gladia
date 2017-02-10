package org.gladia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static void zip(String filename, String dir) throws IOException {

        if (dir == null) {
            return;
        }

        File fdir = new File(filename.substring(0, filename.lastIndexOf(File.separator))); 
        if (!fdir.exists() && !fdir.mkdirs()) {
            throw new RuntimeException("Nao foi possivel criar o diretorio!");
        }

        File directoryToZip = new File(dir);

        List<File> fileList = new ArrayList<File>();
        getAllFiles(directoryToZip, fileList);
        writeZipFile(filename, directoryToZip, fileList);
    }

    public static void getAllFiles(File dir, List<File> fileList) {
        try {
            System.out.println("--- Getting references to all files in: " + dir.getCanonicalPath());
            File[] files = dir.listFiles();
            for (File file : files) {
                fileList.add(file);
                if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    getAllFiles(file, fileList);
                } else {
                    System.out.println("file:" + file.getCanonicalPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeZipFile(String filename, File directoryToZip, List<File> fileList) {

        try {
            System.out.println("Generating file. Initializing..." + filename);
            System.out.println("Generating file. " + filename);
            FileOutputStream fos = new FileOutputStream(filename);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (File file : fileList) {
                if (!file.isDirectory()) { // we only zip files, not directories
                    addToZip(directoryToZip, file, zos);
                }
            }

            zos.close();
            fos.close();
            System.out.println("Generating file. Done!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException,
            IOException {

        FileInputStream fis = new FileInputStream(file);

        // we want the zipEntry's path to be a relative path that is relative
        // to the directory being zipped, so chop off the rest of the path
        String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
                file.getCanonicalPath().length());
        System.out.println("Writing '" + zipFilePath + "' to zip file");
        ZipEntry zipEntry = new ZipEntry(zipFilePath);
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }
}
