package org.ganimede.services;

public class ServiceConfig {
    private String path;

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
