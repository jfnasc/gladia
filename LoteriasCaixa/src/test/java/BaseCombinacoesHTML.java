import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import org.ganimede.RegistrarEventosSISAR;
import org.ganimede.utils.StringUtils;

public abstract class BaseCombinacoesHTML {

    protected NumberFormat df = new DecimalFormat("##0.00");

    public void gerar() {
        int n = getTamanhoBase();

        Integer[] base = new Integer[n];
        for (int i = 0; i < n; i++) {
            base[i] = i + 1;
        }

        List<Integer[]> apostas = RegistrarEventosSISAR.gerar(base, getNumeroDezenasFixas(), getNumeroPrognosticos());
        gerarProva(base, apostas);
    }

    protected String calcularValor(List<Integer[]> apostas) {
        return df.format(Float.valueOf(apostas.size()) * getValorAposta(apostas.get(0).length));
    }

    abstract int getNumeroPrognosticos();

    abstract int getMinimoAcertos();

    abstract int getMenorFaixaPremiavel();

    abstract int getTamanhoBase();

    abstract int getNumeroDezenasFixas();

    abstract boolean isPararSeErro();

    abstract float getValorPremio(int numeroAcertos);

    abstract float getValorAposta(int numeroPrognosticos);

    protected void gerarProva(Integer[] base, List<Integer[]> apostas) {

        StringBuilder html = abrirHtml();

        html.append("<tr><td colspan=2> " + StringUtils.print(base) + "</td></tr>");

        html.append("<tr><td colspan=2><textarea rows=\"10\">");
        for (Integer[] aposta : apostas) {
            html.append(StringUtils.print(aposta) + "\n");
        }
        html.append("</textarea></td></tr>");
        html.append("<tr><td>Qtd. Prognosticos:</td><td style=\"width: 50px; text-align: right\">"
                + getNumeroPrognosticos() + "</td></tr>");
        html.append("<tr><td>Qtd. Jogos: </td><td style=\"width: 50px; text-align: right\">" + apostas.size()
                + "</td></tr>");
        html.append("<tr><td>Valor Aposta: </td><td style=\"width: 50px; text-align: right\">R$ "
                + getValorAposta(apostas.get(0).length) + "</td></tr>");
        html.append("<tr><td>Valor Total:</td><td style=\"width: 50px; text-align: right\">R$ "
                + calcularValor(apostas) + "</td></tr>");
        html.append("</table>");
        html.append("<br/>");
        html.append("<table width=\"50%\">");

        int nuPrognosticos = apostas.get(0).length;

        int tentativas = 0;
        while (tentativas < 10) {

            Integer[] resultado = RegistrarEventosSISAR.simularResultado(6, 60);
            Arrays.sort(resultado);

            if (RegistrarEventosSISAR.conferir(resultado, base) >= getMinimoAcertos()) {

                html.append("<tr><td colspan=" + (nuPrognosticos + 2) + ">&nbsp;</td></tr>");
                html.append("<tr><td colspan=" + (nuPrognosticos + 2) + " style=\"background-color: #f5f5f5\"> "
                        + StringUtils.print(resultado) + "</td></tr>");

                float premio = Float.valueOf(0);
                for (Integer[] aposta : apostas) {
                    Arrays.sort(aposta);

                    int numeroAcertos = RegistrarEventosSISAR.conferir(resultado, aposta);
                    if (numeroAcertos >= getMenorFaixaPremiavel()) {
                        html.append(conferirToHtml(resultado, aposta));
                        premio += getValorPremio(numeroAcertos);
                    }
                }

                html.append(String.format("<tr><td colspan=" + (nuPrognosticos - 1)
                        + "></td><td colspan=2>Premio: R$ </td><td>%s</td></tr>", premio));

                if (isPararSeErro()) {
                    if (premio == 0) {
                        throw new RuntimeException("Falha!");
                    }
                }

                tentativas++;

                if (tentativas % 100 == 0) {
                    System.out.println(tentativas);
                }
            }

        }
        html.append("</table>");
        html.append("</body>");
        html.append("</html>");

        write(html.toString(), "/home/josen/1.html");
    }

    private StringBuilder abrirHtml() {
        StringBuilder html = new StringBuilder();

        html.append("<html> ");
        html.append("<head> ");
        html.append("<style type=\"text/css\"> ");
        html.append("body { ");
        html.append("    font-style: normal; ");
        html.append("    font-family: Arial, Helvetica, sans-serif; ");
        html.append("    font-size: 12px; ");
        html.append("    color: #000; ");
        html.append("} ");
        html.append(" ");
        html.append("textarea { ");
        html.append("    font-size: 12px; ");
        html.append("    width: 100%; ");
        html.append("    background-color: #FFF; ");
        html.append("} ");
        html.append("table { ");
        html.append("    font-style: normal; ");
        html.append("    font-family: Arial, Helvetica, sans-serif; ");
        html.append("    font-size: 12px; ");
        html.append("    border: 1px solid silver; ");
        html.append("    color: #000; ");
        html.append("} ");
        html.append(" ");
        html.append("table tr { ");
        html.append("    background-color: #fff; ");
        html.append("} ");
        html.append(" ");
        html.append("table td { ");
        html.append("    background-color: #fff; ");
        html.append("    border: 1px solid silver; ");
        html.append("    width: 50px; ");
        html.append("} ");
        html.append("</style> ");
        html.append("</head> ");
        html.append("<body> ");
        html.append("<table width=\"50%\"> ");

        return html;
    }

    private void write(String str, String file) {
        FileWriter f = null;
        try {
            f = new FileWriter(file);
            f.write(str + "\n");
            f.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String conferirToHtml(Integer[] resultado, Integer[] aposta) {
        StringBuilder result = new StringBuilder();

        result.append("<tr>");
        for (Integer dezena : aposta) {
            if (Arrays.asList(resultado).contains(dezena)) {
                result.append(String.format(
                        "<td style=\"width: 50px; text-align: right; text-decoration: underline;\">%s</td>", dezena));
            } else {
                result.append(String.format("<td style=\"width: 50px; text-align: right\">%s</td>", dezena));
            }
        }

        result.append("<td>&nbsp;</td>");
        result.append(String.format("<td style=\"width: 50px; text-align: right\"><b>%s</b></td>",
                RegistrarEventosSISAR.conferir(resultado, aposta)));

        result.append("</tr>\n");

        return result.toString();
    }
}
