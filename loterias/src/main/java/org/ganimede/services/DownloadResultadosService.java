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
import java.util.Date;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.ganimede.utils.ZipUtils;

public abstract class DownloadResultadosService {

    private static Logger LOGGER = Logger.getLogger(DownloadResultadosService.class);
    
    private ServiceConfig serviceConfig;

    public abstract void processarResultados();

    protected void writeFile(String filename, StringBuilder buffer) throws Exception {
        FileWriter fw = null;

        try {
            fw = new FileWriter(getServiceConfig().getPath() + File.separator + filename);
            LOGGER.info("Gravando o arquivo : " + getServiceConfig().getPath() + File.separator + filename);
            fw.write(buffer.toString());
            fw.flush();
        } catch (Exception e) {
            fw.close();
        }
    }

    protected boolean baixarArquivos(String url, String filepath) {
        CloseableHttpResponse response = null;

        LOGGER.info("Baixando o arquivo : " + url);
        
        try {
            File f = new File(filepath + File.separator + url.substring(url.lastIndexOf("/") + 1));
            
            if (isArquivoAtualizado(f)) {
                LOGGER.info(f.getAbsolutePath());
                LOGGER.info("Nada a fazer. O arquivo esta atualizado.");
                return false;
            }

            SSLContextBuilder builder = buildSSLContext();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            HttpGet request = new HttpGet(url);

            if (getServiceConfig().isProxyEnabled()) {
                HttpHost proxy = new HttpHost("proxy.caixa", 80, "http");
                RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
                request.setConfig(config);
            }

            response = httpclient.execute(request);

            LOGGER.info(response.getStatusLine());

            BufferedInputStream bis = new BufferedInputStream(response.getEntity().getContent());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            int inByte;
            while ((inByte = bis.read()) != -1) {
                bos.write(inByte);
            }
            bis.close();
            bos.close();

            ZipUtils.unzip(f.getAbsolutePath(), filepath);

        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
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

    /**
     * @return the serviceConfig
     */
    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    /**
     * @param serviceConfig
     *            the serviceConfig to set
     */
    public void setServiceConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    private boolean isArquivoAtualizado(File f) {
        System.out.println(new Date(f.lastModified()));
        return false;
    }
}
