package org.andromeda.torrentsearch;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class SerieInfoDTO implements Serializable, Comparable<SerieInfoDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name;

    private String searchCode;

    private List<TorrentDTO> listTorrents;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the searchCode
     */
    public String getSearchCode() {
        return searchCode;
    }

    /**
     * @param searchCode
     *            the searchCode to set
     */
    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    @Override
    public int compareTo(SerieInfoDTO o) {
        return getName().compareTo(o.getName());
    }

    /**
     * @return the html
     */
    public String getHtml() {

        Template template = VelocityUtils.getTemplate("table-results-tmpl");

        VelocityContext context = new VelocityContext();

        context.put("allTorrentsInfo", getListTorrents());

        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        return sw.toString();
    }

    /**
     * @return the listTorrents
     */
    public List<TorrentDTO> getListTorrents() {
        if (listTorrents == null) {
            listTorrents = new ArrayList<TorrentDTO>();
        }
        return listTorrents;
    }

    /**
     * @param listTorrents
     *            the listTorrents to set
     */
    public void setListTorrents(List<TorrentDTO> listTorrents) {
        this.listTorrents = listTorrents;
    }

}
