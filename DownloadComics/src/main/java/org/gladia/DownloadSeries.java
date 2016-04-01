/**
 * 
 */
package org.gladia;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.gladia.utils.PDFUtils;

/**
 * @author Administrador
 * 
 */
public class DownloadSeries {

    public static void main(String... args) throws IOException {

        int qtCapitulos = Config.getInteger("capitulo.qtCapitulos");
        int qtEdicoes = Config.getInteger("capitulo.qtEdicoes");

        String tmpDir = parseTmpDir();

        if (!(new File(tmpDir)).exists()) {
            (new File(tmpDir)).mkdirs();
        }

        for (int j = 0; j <= qtCapitulos; j++) {

            boolean t1 = false;
            boolean t2 = false;

            for (int i = 1; i <= qtEdicoes; i++) {
                t1 = DownloadSeries.downloadFile(Config.getString("url.home"), tmpDir, obterNomeCapitulo(j),
                        obterNomeArquivo(i, false));

                if (!t1) {
                    t2 = DownloadSeries.downloadFile(Config.getString("url.home"), tmpDir, obterNomeCapitulo(j),
                            obterNomeArquivo(i, true));
                }

                if (!t1 && !t2) {
                    break;
                }
            }

            ZipUtils.zip(parseFilename(Config.getString("target.dir"), j, "cbz"), tmpDir + File.separator
                    + obterNomeCapitulo(j));
            PDFUtils.convertJpegToPdf(parseFilename(Config.getString("target.dir"), j, "pdf"), tmpDir + File.separator
                    + obterNomeCapitulo(j));
        }

    }

    private static String parseTmpDir() {
        return String.format("%s%s%s%s%s", Config.getString("tmp.dir"), File.separator, Config.getString("perso.nome"),
                File.separator, Config.getString("serie.nome"));
    }

    private static String parseFilename(String targetDir, int numeroCapitulo, String filetype) {
        StringBuilder sb = new StringBuilder();

        sb.append(targetDir + File.separator);
        sb.append(Config.getString("perso.nome") + File.separator);
        sb.append(Config.getString("serie.nome") + File.separator);
        sb.append(filetype + File.separator);
        sb.append(Config.getString("serie.nome") + " - " + obterNumeroCapitulo(numeroCapitulo) + "." + filetype);

        return sb.toString();
    }

    public static boolean downloadFile(String urlHome, String targetDir, String edicao, String filename)
            throws IOException {
        String url = String.format("%s/%s/%s", urlHome, edicao, filename);
        System.out.println(url);

        String dir = String.format("%s/%s", targetDir, edicao);
        (new File(dir)).mkdirs();

        String filePath = String.format("%s/%s/%s", targetDir, edicao, filename);
        System.out.println(filePath);

        if ((new File(filePath)).exists()) {
            System.out.println(filePath + " ja existe.");
            return true;
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);

        System.out.println(response.getStatusLine());
        if (response.getStatusLine().getStatusCode() == 404) {
            return false;
        }
        HttpEntity entity = response.getEntity();

        try {
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    BufferedInputStream bis = new BufferedInputStream(instream);

                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                    int inByte;
                    while ((inByte = bis.read()) != -1) {
                        bos.write(inByte);
                    }
                    bis.close();
                    bos.close();
                } catch (IOException ex) {
                    throw ex;
                } catch (RuntimeException ex) {
                    throw ex;
                } finally {
                    instream.close();
                }
            }
        } finally {
            response.close();
        }

        return true;
    }

    private static String obterNomeCapitulo(int nuCapitulo) {

        String edicaoPrefixo = Config.getString("edicao.prefixo");
        String result = obterNumeroCapitulo(nuCapitulo);

        if (Utils.isEmpty(edicaoPrefixo)) {
            return String.format("%s", result);
        }

        return String.format("%s%s", edicaoPrefixo, result);

    }

    private static String obterNumeroCapitulo(int nuCapitulo) {

        Integer edicaoQtDigitos = Config.getInteger("edicao.qtDigitos");
        String result = String.valueOf(nuCapitulo);

        while (result.length() < edicaoQtDigitos) {
            result = "0" + result;
        }

        return result;

    }

    private static String obterNomeArquivo(int nuArquivo, boolean upperCase) {

        String arquivoPrefixo = Config.getString("arquivo.prefixo");
        String arquivoSufixo = Config.getString("arquivo.sufixo");
        String result = obterNumeroArquivo(nuArquivo);

        if (Utils.isEmpty(arquivoPrefixo)) {
            return String.format("%s%s", result, (upperCase ? arquivoSufixo.toUpperCase() : arquivoSufixo));
        }

        return String.format("%s%s%s", arquivoPrefixo, result,
                (upperCase ? arquivoSufixo.toUpperCase() : arquivoSufixo));

    }

    private static String obterNumeroArquivo(int nuArquivo) {
        Integer arquivoQtDigitos = Config.getInteger("arquivo.qtDigitos");

        String result = String.valueOf(nuArquivo);

        while (result.length() < arquivoQtDigitos) {
            result = "0" + result;
        }

        return result;

    }

}
