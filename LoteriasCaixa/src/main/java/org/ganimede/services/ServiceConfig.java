package org.ganimede.services;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class ServiceConfig {
    private String path;
    
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
    
    public static DecimalFormat df = new DecimalFormat("###,##0.00");

    private boolean isProxyEnabled;

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the isProxyEnabled
     */
    public boolean isProxyEnabled() {
        return isProxyEnabled;
    }

    /**
     * @param isProxyEnabled
     *            the isProxyEnabled to set
     */
    public void setProxyEnabled(boolean isProxyEnabled) {
        this.isProxyEnabled = isProxyEnabled;
    }

}
