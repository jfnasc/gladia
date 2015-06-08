package org.ganimede.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.ganimede.utils.ZipUtils;

public abstract class DownloadResultadosService {
    
    public abstract void processarResultados();
    
    protected void writeFile(String filename, StringBuilder buffer) throws Exception{
        FileWriter fw = null;
        
        try {
            fw = new FileWriter("/projetos/github/gladia/LoteriasCaixa/arquivos/" + filename);
            fw.write(buffer.toString());
            fw.flush();
        } catch (Exception e) {
           fw.close();
        }

    }
    
    protected void baixarResultados(String url, String filepath) {
        CloseableHttpResponse response = null;

        try {
            SSLContextBuilder builder = buildSSLContext();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            HttpGet httpGet = new HttpGet(url);

            response = httpclient.execute(httpGet);

            System.out.println(response.getStatusLine());

            File f = new File(filepath + File.separator + url.substring(url.lastIndexOf("/") + 1));
            BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            int inByte;
            while ((inByte = bis.read()) != -1)
                bos.write(inByte);
            bis.close();
            bos.close();

            ZipUtils.unzip(f.getAbsolutePath(), filepath);

        } catch (Exception e) {

        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static SSLContextBuilder buildSSLContext() throws NoSuchAlgorithmException, KeyStoreException {
        SSLContextBuilder builder = new SSLContextBuilder();

        builder.loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        });
        return builder;
    }
}
