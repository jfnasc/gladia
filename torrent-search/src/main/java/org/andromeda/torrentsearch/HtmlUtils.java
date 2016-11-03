package org.andromeda.torrentsearch;

import java.util.List;

public class HtmlUtils {
    public static final String toHtml(List<TorrentDTO> lista) {

        StringBuilder sb = new StringBuilder();

        sb.append("<table>\n");

        sb.append("<tr>\n");
        sb.append("<th>Title</th>\n");
        sb.append("<th>Size</th>\n");
        sb.append("<th>Released</th>\n");
        sb.append("<th>Seeds</th>\n");
        sb.append("<th>Magnet</th>\n");
        sb.append("</tr>\n");

        for (TorrentDTO t : lista) {
            sb.append("<tr>\n");
            sb.append("\t<td style=\"width: 60%\">" + t.getTitle() + "</td>\n");
            sb.append("\t<td style=\"width: 10%; text-align:right;\">" + t.getSize() + "</td>\n");
            sb.append("\t<td style=\"width: 10%; text-align:right;\">" + t.getReleased() + "</td>\n");
            sb.append("\t<td style=\"width: 10%; text-align:right;\">" + t.getSeeds() + "</td>\n");
            sb.append("\t<td style=\"width: 10%; text-align:center;\"><a href=\"" + t.getMagnetLink() + "\">baixar</a></td>\n");
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        sb.append("<br/>\n");

        return sb.toString();
    }
}
