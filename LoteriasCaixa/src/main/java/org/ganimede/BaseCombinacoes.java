package org.ganimede;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ganimede.utils.MathUtils;
import org.ganimede.utils.StringUtils;

public abstract class BaseCombinacoes {

    private NumberFormat nf = new DecimalFormat("###,##0.00");
    private int nuPrognosticos;
    private int numeroDezenasFixas;
    private int tamanhoBase;
    private Integer[] base;

    public List<Integer[]> gerar(int tamanhoBase, int nuPrognosticos, int numeroDezenasFixas) {
        this.tamanhoBase = tamanhoBase;
        this.nuPrognosticos = nuPrognosticos;
        this.numeroDezenasFixas = numeroDezenasFixas;

        List<Integer[]> bases = gerarCombinacoes().prognosticos(1, this.tamanhoBase);
        this.base = bases.get(0);
        
        return Combinacoes.calcularFechamento(this.base, this.numeroDezenasFixas, this.nuPrognosticos);
    }

    abstract GerarCombinacoesBase gerarCombinacoes();
    
    abstract BigDecimal valorAposta(int numeroPrognosticos);

    abstract Integer[] simularResultado();

    abstract int menorFaixaPremiavel();

    abstract int maiorFaixaPremiavel();

    abstract BigDecimal valorPremio(int numeroAcertos);

    abstract int numeroDezenas();

    public BigDecimal calcularValor(int tamanhoBase, int numeroPrognosticos, int numeroDezenasFixas) {
        List<Integer[]> apostas = gerar(tamanhoBase, numeroPrognosticos, numeroDezenasFixas);
        return calcularValor(apostas);
    }

    public BigDecimal calcularValor(List<Integer[]> apostas) {
        return BigDecimal.valueOf(apostas.size()).multiply(valorAposta(apostas.get(0).length));
    }

    public void gerarProvaHTML(int tamanhoBase, int numeroPrognosticos, int numeroDezenasFixas) {

        List<Integer[]> apostas = gerar(tamanhoBase, numeroPrognosticos, numeroDezenasFixas);

        StringBuilder html = abrirHtml();

        Arrays.sort(base);
        
        html.append("<tr><td colspan=2> " + StringUtils.print(base) + "</td></tr>");
        html.append("<tr><td colspan=2> " + base.length + "</td></tr>");
        html.append("<tr><td colspan=2> 1:"
                + MathUtils.calcularChances(numeroDezenas(), maiorFaixaPremiavel(), tamanhoBase, numeroDezenasFixas)
                + "</td></tr>");

        html.append("<tr><td colspan=2><textarea rows=\"10\">");
        for (Integer[] aposta : apostas) {
            html.append(StringUtils.print(aposta) + "\n");
        }
        html.append("</textarea></td></tr>");
        html.append("<tr><td>Qtd. Prognosticos:</td><td style=\"width: 50px; text-align: right\">" + numeroPrognosticos
                + "</td></tr>");
        html.append("<tr><td>Qtd. Jogos: </td><td style=\"width: 50px; text-align: right\">" + apostas.size()
                + "</td></tr>");
        html.append("<tr><td>Valor Aposta: </td><td style=\"width: 50px; text-align: right\">R$ "
                + nf.format(valorAposta(apostas.get(0).length)) + "</td></tr>");
        html.append("<tr><td>Valor Total:</td><td style=\"width: 50px; text-align: right\">R$ "
                + nf.format(calcularValor(apostas)) + "</td></tr>");
        html.append("</table>");
        html.append("<br/>");
        html.append("<table width=\"40%\">");

        int nuPrognosticos = apostas.get(0).length;

        int tentativas = 0;
        while (tentativas < 100) {

            Integer[] resultado = Combinacoes.simularResultado(maiorFaixaPremiavel(), numeroDezenas());
            Arrays.sort(resultado);

            if (Combinacoes.conferir(resultado, base) >= 0) {

                html.append("<tr><td colspan=" + (nuPrognosticos + 2) + " style=\"background-color: #f5f5f5\"> "
                        + StringUtils.print(resultado) + "</td></tr>");

                BigDecimal premio = BigDecimal.ZERO;
                for (Integer[] aposta : apostas) {
                    Arrays.sort(aposta);

                    int numeroAcertos = Combinacoes.conferir(resultado, aposta);
                    if (numeroAcertos >= menorFaixaPremiavel()) {
                        html.append(conferirToHtml(resultado, aposta));
                        premio = premio.add(valorPremio(numeroAcertos));
                    }
                }

                html.append(String
                        .format("<tr><td colspan="
                                + (nuPrognosticos - 1)
                                + " width=\"50\"></td><td colspan=2 width=\"30\">Premio: R$ </td><td align=\"right\" width=\"20\">%s</td></tr>",
                                nf.format(premio)));

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
        html.append("<table width=\"40%\"> ");

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
                Combinacoes.conferir(resultado, aposta)));

        result.append("</tr>\n");

        return result.toString();
    }

    public void gerarProva(int tamanhoBase, int numeroPrognosticos, int numeroDezenasFixas) {
        List<Integer[]> apostas = gerar(tamanhoBase, numeroPrognosticos, numeroDezenasFixas);

        float premiadas = 0;
        float tentativas = 0;
        while (tentativas < 1000000) {

            Integer[] resultado = Combinacoes.simularResultado(maiorFaixaPremiavel(), numeroDezenas());
            Arrays.sort(resultado);

            BigDecimal premio = BigDecimal.ZERO;
            for (Integer[] aposta : apostas) {
                Arrays.sort(aposta);
                int numeroAcertos = Combinacoes.conferir(resultado, aposta);
                if (numeroAcertos >= menorFaixaPremiavel()) {
                    premio = premio.add(valorPremio(numeroAcertos));
                }
                if (premio.intValue() > 0) {
                    premiadas++;
                }
            }

            tentativas++;
        }

        System.out.println(String.format("%s %s %s ", premiadas, tentativas, nf.format(premiadas / tentativas * 100)));
    }
}
