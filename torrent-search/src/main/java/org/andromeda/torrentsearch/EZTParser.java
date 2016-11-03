package org.andromeda.torrentsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EZTParser implements Parser {

    private static Logger LOGGER = LogManager.getLogger(EZTParser.class);

    private int limit = 100;

    public EZTParser() {

    }

    @Override
    public List<TorrentDTO> listar(String nomeSerie) {

        if (!isResultInCache(nomeSerie)) {
            writeCache(nomeSerie, pesquisar(nomeSerie));
        }

        String line = restoreFromCache(nomeSerie);

        List<TorrentDTO> result = new ArrayList<>();

        String prefixo = "<tr name=\"hover\" class=\"forum_header_border\">";
        String sufixo = "</tr>";

        Pattern p = Pattern.compile(prefixo + "[\\W\\w\\d\\s]+" + sufixo);
        Matcher m = p.matcher(line);

        String base = null;
        if (m.find()) {
            base = line.substring(m.start(), m.end());
        }

        String[] episodes = base.split("</tr>");

        int count = 0;
        for (String episode : episodes) {

            String[] parts = episode.split("</td>");

            TorrentDTO dto = new TorrentDTO();

            dto.setTitle(replaceAll(parts[1], "[\\w\\s\\W]+class=\"epinfo\">|</a>", ""));
            dto.setMagnetLink(extract(parts[2], "magnet:[\\w\\d\\?=:&\\.\\%\\-]*"));
            dto.setSize(replaceAll(parts[3], "<[\\w\\s\\W]+>", ""));
            dto.setReleased(replaceAll(parts[4], "<[\\w\\s\\W]+>", ""));
            dto.setSeeds(replaceAll(parts[5].replaceAll("</font>", ""), "<[\\w\\s\\W]+>", ""));

            LOGGER.info(dto.getTitle());

            if (dto.getTitle().indexOf("720p") != -1) {
                result.add(dto);
                count++;
            }

            if (count == limit) {
                break;
            }
        }

        Collections.sort(result);

        return result;
    }

    private String pesquisar(String nomeSerie) {

        String result = null;

        String url = "https://eztv.ag/search/" + nomeSerie;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = null;

        String line = null;

        try {
            response1 = httpclient.execute(httpGet);

            LOGGER.info(url);
            LOGGER.info(response1.getStatusLine());

            HttpEntity entity = response1.getEntity();
            InputStreamReader isr = new InputStreamReader(entity.getContent());
            BufferedReader br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }

            result = sb.toString();

        } catch (Exception e) {
            System.out.println(line);
            e.printStackTrace();
        } finally {
            try {
                response1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private String extract(String arg0, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(arg0);

        if (m.find()) {
            return (arg0.substring(m.start(), m.end()));
        }

        return null;
    }

    private String replaceAll(String arg0, String regex, String arg1) {
        return arg0.replaceAll(regex, arg1);
    }

    private void writeCache(String nomeSerie, String line) {
        FileWriter fw = null;

        try {

            fw = new FileWriter(getFileNameCache(nomeSerie));
            fw.write(line + "\n");
            fw.flush();

        } catch (IOException e) {

            e.printStackTrace();

            try {
                fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /*
     * 
     */
    private boolean isResultInCache(String nomeSerie) {
        return (new File(getFileNameCache(nomeSerie))).exists();
    }

    /*
     * 
     */
    private Long getTimestamp() {

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.MILLISECOND, 00);

        return cal.getTimeInMillis();

    }

    /*
     * 
     */
    private String getFileNameCache(String nomeSerie) {

        return String.format("./cache/%s-%s.cache", nomeSerie, getTimestamp());

    }

    private String restoreFromCache(String nomeSerie) {

        StringBuilder sb = new StringBuilder();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File(getFileNameCache(nomeSerie))));

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();

            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return sb.toString();
    }

}
