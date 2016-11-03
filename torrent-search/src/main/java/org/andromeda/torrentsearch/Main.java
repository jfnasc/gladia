/**
 * 
 */
package org.andromeda.torrentsearch;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author josen
 * 
 */
public class Main {
    public static void main(String[] args) {
        
    }
    
    public static void main2(String[] args) {
        EZTParser p = new EZTParser();

        StringBuilder sb = new StringBuilder();
        sb.append("<html>                                            \n");
        sb.append("<head>                                            \n");
        sb.append("  <title>TORRENT Search Results</title>           \n");
        sb.append("  <style type=\"text/css\">                       \n");
        sb.append("    table {                                       \n");
        sb.append("        border-collapse: collapse;                \n");
        sb.append("        width: 80%;                               \n");
        sb.append("    }                                             \n");
        sb.append("                                                  \n");
        sb.append("    th, td {                                      \n");
        sb.append("        font-family: arial;                       \n");
        sb.append("        font-size: 12px;                          \n");
        sb.append("        text-align: left;                         \n");
        sb.append("        padding: 8px;                             \n");
        sb.append("        height: 15px;                             \n");
        sb.append("    }                                             \n");
        sb.append("                                                  \n");
        sb.append("    tr:nth-child(even){background-color: #f2f2f2} \n");
        sb.append("                                                  \n");
        sb.append("    th {                                          \n");
        sb.append("        background-color: #4CAF50;                \n");
        sb.append("        color: white;                             \n");
        sb.append("        height: 20px;                             \n");
        sb.append("    }                                             \n");
        sb.append("  </style>                                        \n");
        sb.append("</head>                                           \n");
        sb.append("<body>\n");

        sb.append(HtmlUtils.toHtml(p.listar("empire-2015")));
        sb.append(HtmlUtils.toHtml(p.listar("the-walking-dead")));
        sb.append(HtmlUtils.toHtml(p.listar("van-helsing")));
        sb.append(HtmlUtils.toHtml(p.listar("westworld")));
        sb.append(HtmlUtils.toHtml(p.listar("chicago-pd")));
        sb.append(HtmlUtils.toHtml(p.listar("chicago-fire")));
        sb.append(HtmlUtils.toHtml(p.listar("impastor")));
        sb.append(HtmlUtils.toHtml(p.listar("the-exorcist")));
        sb.append(HtmlUtils.toHtml(p.listar("the-fall")));
        sb.append(HtmlUtils.toHtml(p.listar("the-flash-2014")));
        sb.append(HtmlUtils.toHtml(p.listar("blacklist")));
        sb.append(HtmlUtils.toHtml(p.listar("lucifer")));

        sb.append("</body>\n");
        sb.append("</html>\n");

        try {
            FileWriter bw = new FileWriter("/home/josen/result.html");
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
