package org.ganimede.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ganimede.services.DownloadResultadosService;
import org.ganimede.utils.StringUtils;

public class DownloadResultadosQuina extends DownloadResultadosService {

    private String urlArquivo;

    @Override
    public void processarResultados() {
        baixarArquivos(getUrlArquivo(), getServiceConfig().getPath());

        BufferedReader reader = null;
        InputStreamReader in = null;

        try {
            File f = new File(getServiceConfig().getPath() + File.separator + "D_QUINA.HTM");
            in = new InputStreamReader(new FileInputStream(f), "ISO-8859-1");
            reader = new BufferedReader(in);

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(StringUtils.normalize(line).replaceAll(" bgcolor=#D9E6F4", "").trim());
            }

            StringBuilder template = new StringBuilder();
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*/[0-9]*/[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9,.]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[-\\(\\),&;\\s\\w]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[-\\(\\),&;\\s\\w]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9,.]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9,.]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9,.]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[a-zA-Z]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9,.]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9,.]*</td>");
            // template.append("<td rowspan=\"[0-9]*\">[0-9,.]*</td>");

            Pattern p = Pattern.compile(template.toString());
            Matcher m = p.matcher(sb.toString());

            StringBuilder buffer = new StringBuilder();

            int count = 0;
            while (m.find()) {
                String str = sb.toString().substring(m.start(), m.end());
                str = str.replaceAll(" rowspan=\"[0-9]*\"", "");
                str = str.replaceAll("^<td>", "");
                str = str.replaceAll("</td><td>", ";");
                str = str.replaceAll("</td>", "");
                buffer.append(str + "\n");

                count++;
                if (count != Integer.valueOf(str.substring(0, str.indexOf(";")))) {
                    reader.close();
                    throw new RuntimeException("Erro de contagem: " + count);
                }
            }

            writeFile("resultados_quina.csv", buffer);

            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return the urlArquivo
     */
    public String getUrlArquivo() {
        return urlArquivo;
    }

    /**
     * @param urlArquivo
     *            the urlArquivo to set
     */
    public void setUrlArquivo(String urlArquivo) {
        this.urlArquivo = urlArquivo;
    }

    public static void main(String[] args) {
        DownloadResultadosService service = new DownloadResultadosQuina();
        service.processarResultados();
    }
}
