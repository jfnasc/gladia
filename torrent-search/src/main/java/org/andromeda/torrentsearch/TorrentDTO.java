package org.andromeda.torrentsearch;

import java.io.Serializable;

public class TorrentDTO implements Serializable, Comparable<TorrentDTO> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private String title;

    private String magnetLink;

    private String size;

    private String released;

    private String seeds;

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

    @Override
    public int compareTo(TorrentDTO o) {
        // inverse
        return o.getTitle().compareTo(getTitle());
    }

}
