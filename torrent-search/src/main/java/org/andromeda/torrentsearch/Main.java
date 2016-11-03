/**
 * 
 */
package org.andromeda.torrentsearch;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * @author josen
 * 
 */
public class Main {

    public static void main(String[] args) {
        Template template = VelocityUtils.getTemplate("page2-tmpl");

        VelocityContext context = new VelocityContext();

        // titulo
        context.put("title", new String("Torrents::Search Results"));

        //
        List<SerieInfoDTO> series = getSeriesInfo();
        context.put("firstSerieName", series.get(0).getName());
        context.put("allSeriesInfo", series);

        //
        EZTVParser p = new EZTVParser();

        for (SerieInfoDTO serieInfoDTO : series) {
            serieInfoDTO.getListTorrents().addAll(p.listar(serieInfoDTO.getSearchCode()));
        }

        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        try {
            FileWriter bw = new FileWriter("/home/josen/result.html");
            bw.write(sw.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<SerieInfoDTO> getSeriesInfo() {

        List<SerieInfoDTO> result = new ArrayList<>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/series.lst")));

            String line = null;
            while ((line = reader.readLine()) != null) {
                SerieInfoDTO dto = new SerieInfoDTO();

                dto.setName(line.split(";")[0].trim());
                dto.setSearchCode(line.split(";")[1].trim());

                result.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();

            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        Collections.sort(result);

        return result;
    }

}
