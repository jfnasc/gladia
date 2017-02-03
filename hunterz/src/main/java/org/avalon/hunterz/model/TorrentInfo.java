package org.avalon.hunterz.model;

import java.io.Serializable;

public class TorrentInfo implements Serializable, Comparable<TorrentInfo> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private Integer codigo;

    private String title = "";

    private String magnetLink = "";

    private String size = "0";

    private String released = "";

    private String seeds = "0";

    private String leechers = "0";

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the magnetLink
     */
    public String getMagnetLink() {
        return magnetLink;
    }

    /**
     * @param magnetLink
     *            the magnetLink to set
     */
    public void setMagnetLink(String magnetLink) {
        this.magnetLink = magnetLink;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the released
     */
    public String getReleased() {
        return released;
    }

    /**
     * @param released
     *            the released to set
     */
    public void setReleased(String released) {
        this.released = released;
    }

    /**
     * @return the seeds
     */
    public String getSeeds() {
        return seeds;
    }

    /**
     * @param seeds
     *            the seeds to set
     */
    public void setSeeds(String seeds) {
        this.seeds = seeds;
    }

    /**
     * 
     * @return
     */
    public String getLeechers() {
        return leechers;
    }

    /**
     * 
     * @param leechers
     */
    public void setLeechers(String leechers) {
        this.leechers = leechers;
    }

    /**
     * @return the codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * @param codigo
     *            the codigo to set
     */
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    @Override
    public int compareTo(TorrentInfo o) {
        // inverse
        return o.getTitle().compareTo(getTitle());
    }

}
