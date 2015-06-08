package org.ganimede.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ganimede.services.DownloadResultadosService;

public class DownloadResultadosMegaSena extends DownloadResultadosService {

    @Override
    public void processarResultados() {
        baixarResultados("https://www1.caixa.gov.br/loterias/_arquivos/loterias/D_mgsasc.zip",
                "/projetos/github/gladia/LoteriasCaixa/arquivos");

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(new File(
                    "/projetos/github/gladia/LoteriasCaixa/arquivos/d_megasc.htm")));

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line.replaceAll(" bgcolor=#D9E6F4", "").trim());

            }

            StringBuilder template = new StringBuilder();
            template.append("<tr><td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*/[0-9]*/[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");
            template.append("<td rowspan=\"[0-9]*\">[0-9]*</td>");

            Pattern p = Pattern.compile(template.toString());
            Matcher m = p.matcher(sb.toString());

            StringBuilder buffer = new StringBuilder();

            int count = 0;
            while (m.find()) {
                String str = sb.toString().substring(m.start(), m.end());
                str = str.replaceAll(" rowspan=\"[0-9]*\"", "");
                str = str.replaceAll("<tr><td>", "");
                str = str.replaceAll("</td><td>", ";");
                str = str.replaceAll("</td>", "");
                buffer.append(str + "\n");

                count++;
                if (count != Integer.valueOf(str.substring(0, str.indexOf(";")))) {
                    System.exit(1);
                }
            }
            
            writeFile("resultados_mega_sena.csv", buffer);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DownloadResultadosService service = new DownloadResultadosMegaSena();
        service.processarResultados();
    }
}
