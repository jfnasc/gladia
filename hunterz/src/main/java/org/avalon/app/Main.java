/**
 * 
 */
package org.avalon.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.avalon.hunterz.Parser;
import org.avalon.hunterz.SeriesDTO;
import org.avalon.hunterz.TorrentDTO;
import org.avalon.hunterz.VelocityUtils;
import org.avalon.hunterz.dao.SeriesDAO;
import org.avalon.hunterz.model.Serie;
import org.avalon.hunterz.parsers.EZTVParser;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author josen
 * 
 */
public class Main {

    static ApplicationContext context;

    static List<Parser> parsers = null;

    static {
        context = new ClassPathXmlApplicationContext("appConfig.xml");

        parsers = new ArrayList<>();

        // parsers.add(new ExtraTorrentParser());
        parsers.add(new EZTVParser());
        // parsers.add(new PirateBayParser());
        // parsers.add(new KickAssParser());
    }

    public static void main(String[] args) {

        try {
            SeriesDAO seriesDAO = (SeriesDAO) context.getBean("SeriesDAO");

            System.out.println(seriesDAO.listar());
        } catch (BeansException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Parser p : parsers) {
            listarMaisRecentes(parsers);
            listar(p);
        }
    }

    public static void listarMaisRecentes(List<Parser> parsers) {
        Template template = VelocityUtils.getTemplate("sidenav-resumo-tmpl");

        VelocityContext context = new VelocityContext();

        List<TorrentDTO> torrents = new ArrayList<>();

        for (Parser p : parsers) {

            List<SeriesDTO> series = listarEpisodiosMaisRecentes(p);

            for (SeriesDTO serie : series) {
                torrents.addAll(serie.getListTorrents());
            }
        }

        Collections.sort(torrents);
        Collections.reverse(torrents);

        // titulo
        context.put("title", new String("Torrents::Search Results"));
        context.put("allTorrentsInfo", torrents);

        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        try {
            FileWriter bw = new FileWriter("pages/resumo.html");
            bw.write(sw.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listar(Parser p) {
        Template template = VelocityUtils.getTemplate("sidenav-tmpl");

        VelocityContext context = new VelocityContext();

        List<SeriesDTO> series = listarEpisodios(p);

        // titulo
        context.put("title", new String("Torrents::Search Results"));
        context.put("firstSerieName", series.get(0).getSerie().getNome());
        context.put("allSeriesInfo", series);

        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        try {

            (new File("./pages")).mkdirs();

            FileWriter bw = new FileWriter("pages/" + p.getClass().getSimpleName() + ".html");
            bw.write(sw.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<SeriesDTO> listarEpisodios(Parser p) {

        List<SeriesDTO> series = listarSeries();

        for (SeriesDTO serieInfoDTO : series) {
            List<TorrentDTO> episodios = (p).listar(serieInfoDTO);
            serieInfoDTO.getListTorrents().addAll(episodios);
        }

        return series;
    }

    private static List<SeriesDTO> listarEpisodiosMaisRecentes(Parser p) {

        List<SeriesDTO> series = listarSeries();

        for (SeriesDTO serieInfoDTO : series) {
            List<TorrentDTO> episodes = (p).listar(serieInfoDTO);
            if (!episodes.isEmpty()) {
                serieInfoDTO.getListTorrents().add(episodes.get(0));
            }
        }

        return series;
    }

    private static List<SeriesDTO> listarSeries() {

        List<SeriesDTO> result = new ArrayList<>();

        try {

            SeriesDAO seriesDAO = (SeriesDAO) context.getBean("SeriesDAO");

            for (Serie serie : seriesDAO.listar()) {
                
                SeriesDTO seriesDTO = new SeriesDTO();
                seriesDTO.setSerie(serie);
                
                result.add(seriesDTO);
            }

        } catch (BeansException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
