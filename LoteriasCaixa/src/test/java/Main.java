import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
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

            while (m.find()) {
                String str = sb.toString().substring(m.start(), m.end());
                str = str.replaceAll(" rowspan=\"[0-9]*\"", "");
                str = str.replaceAll("<tr><td>", "");
                str = str.replaceAll("</td><td>", ";");
                str = str.replaceAll("</td>", "");
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
