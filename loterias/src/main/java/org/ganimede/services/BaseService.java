package org.ganimede.services;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;

public abstract class BaseService {

    protected static ResourceBundle config = ResourceBundle.getBundle("application");

    private ConcursoDAO concursoDAO;

    protected String response(String url) throws IOException {
        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        HttpHost target = new HttpHost(config.getString("host"), 80, "http");

        HttpHost proxy = new HttpHost("proxy.caixa", 80, "http");
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        // RequestConfig config = RequestConfig.custom().build();

        HttpGet request = new HttpGet(url);
        request.setConfig(config);

        System.out.println("Executing request " + request.getRequestLine() + " to " + target);

        response = httpclient.execute(target, request);
        if (response.getStatusLine().getStatusCode() == 200) {
            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }

    /**
     * 
     * @param result
     */
    protected void print(Integer[] result) {
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    protected ConcursoDAO getConcursoDAO() {
        if (this.concursoDAO == null) {
            this.concursoDAO = new ConcursoDAOImpl();
        }
        return this.concursoDAO;
    }
}
