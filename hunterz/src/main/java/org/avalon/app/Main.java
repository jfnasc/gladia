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
import org.avalon.hunterz.VelocityUtils;
import org.avalon.hunterz.dao.SeriesDAO;
import org.avalon.hunterz.dao.TorrentsDAO;
import org.avalon.hunterz.model.Serie;
import org.avalon.hunterz.model.TorrentInfo;
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

    private static SeriesDAO seriesDAO;

    private static TorrentsDAO torrentsDAO;

    public static void main(String[] args) {

        for (Parser p : parsers) {
            try {
                buscar(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void buscar(Parser p) throws Exception {
        List<SeriesDTO> series = listarEpisodios(p);
        for (SeriesDTO dto : series) {
            getTorrentsDAO().salvar(dto);
        }
    }

    private static List<SeriesDTO> listarEpisodios(Parser p) {

        List<SeriesDTO> series = listarSeries();

        for (SeriesDTO serieInfoDTO : series) {
            List<TorrentInfo> episodios = (p).listar(serieInfoDTO);
            serieInfoDTO.listarTorrents().addAll(episodios);
        }

        return series;
    }

    private static List<SeriesDTO> listarSeries() {

        List<SeriesDTO> result = new ArrayList<>();

        try {

            for (Serie serie : getSeriesDAO().listar()) {

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

    /**
     * @return the seriesDAO
     */
    private static SeriesDAO getSeriesDAO() {
        if (seriesDAO == null) {
            seriesDAO = (SeriesDAO) context.getBean("SeriesDAO");
        }
        return seriesDAO;
    }

    /**
     * @return the torrentsDAO
     */
    private static TorrentsDAO getTorrentsDAO() {
        if (torrentsDAO == null) {
            torrentsDAO = (TorrentsDAO) context.getBean("TorrentsDAO");
        }
        return torrentsDAO;
    }

}
