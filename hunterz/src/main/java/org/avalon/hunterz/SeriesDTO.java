package org.avalon.hunterz;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.avalon.hunterz.model.Serie;
import org.avalon.hunterz.model.TorrentInfo;

public class SeriesDTO implements Serializable, Comparable<SeriesDTO> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Serie serie;

    private List<TorrentInfo> listTorrents;

    /**
     * @return the serie
     */
    public Serie getSerie() {
        return serie;
    }

    /**
     * @param serie
     *            the serie to set
     */
    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    @Override
    public int compareTo(SeriesDTO o) {
        return serie.getNome().compareTo(serie.getNome());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SeriesDTO [serie=" + serie + ", listTorrents=" + listTorrents + "]";
    }

    /**
     * @return the html
     */
    public String getHtml() {

        Template template = VelocityUtils.getTemplate("table-results-tmpl");

        VelocityContext context = new VelocityContext();

        context.put("allTorrentsInfo", listarTorrents());

        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        return sw.toString();
    }

    /**
     * @return the listTorrents
     */
    public List<TorrentInfo> listarTorrents() {
        if (listTorrents == null) {
            listTorrents = new ArrayList<TorrentInfo>();
        }
        return listTorrents;
    }

    /**
     * @param listTorrents
     *            the listTorrents to set
     */
    public void setListTorrents(List<TorrentInfo> listTorrents) {
        this.listTorrents = listTorrents;
    }

}
