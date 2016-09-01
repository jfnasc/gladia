package org.ganimede;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import org.ganimede.utils.MathUtils;
import org.ganimede.utils.StringUtils;

public abstract class BaseCombinacoes {

    private NumberFormat nf = new DecimalFormat("###,##0.00");

    private Integer[] base;

    public Integer[] simularResultado() {
        return Fechamentos.simularResultado(maiorFaixaPremiavel(), numeroDezenas());
    }

    public List<Integer[]> gerar(int tamanhoBase, int nuPrognosticos, int numeroDezenasFixas) {
//        List<Integer[]> bases = gerarCombinacoes().prognosticos(1, tamanhoBase);
//        this.base = bases.get(0);
        
        int n = tamanhoBase;
        this.base = new Integer[n];
        for (int i = 0; i < n; i++) {
            base[i] = i + 1;
        }   
        
        return Fechamentos.calcular(this.base, numeroDezenasFixas, nuPrognosticos);
    }

    public List<Integer[]> gerar(int nuApostas, int nuPrognosticos) {
        return gerarCombinacoes().gerarPrognosticos(nuApostas, nuPrognosticos);
    }

    abstract GerarCombinacoesBase gerarCombinacoes();

    abstract BigDecimal valorAposta(int numeroPrognosticos);

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

    public void gerarProvaHTML(int tamanhoBase, int nuPrognosticos) {
        List<Integer[]> apostas = gerar(tamanhoBase, nuPrognosticos);
        gerarHtml(apostas, tamanhoBase, 0, nuPrognosticos);
    }

    public void gerarProvaHTML(int tamanhoBase, int nuPrognosticos, int numeroDezenasFixas) {
        List<Integer[]> apostas = gerar(tamanhoBase, nuPrognosticos, numeroDezenasFixas);
        gerarHtml(apostas, tamanhoBase, numeroDezenasFixas, nuPrognosticos);
    }

    private void gerarHtml(List<Integer[]> apostas, int tamanhoBase, int numeroDezenasFixas, int nuPrognosticos) {
        StringBuilder html = abrirHtml();

        if (this.base != null) {
            Arrays.sort(this.base);
            html.append("<tr><td colspan=2> " + Arrays.toString(base) + "</td></tr>");
            html.append("<tr><td colspan=2> " + base.length + "</td></tr>");
            html.append("<tr><td colspan=2> 1:"
                    + MathUtils
                            .calcularChances(numeroDezenas(), maiorFaixaPremiavel(), tamanhoBase, numeroDezenasFixas)
                    + "</td></tr>");
        }

        html.append("<tr><td colspan=2><textarea rows=\"10\">");
        for (Integer[] aposta : apostas) {
            Arrays.sort(aposta);
            html.append(Arrays.toString(aposta) + "\n");
        }
        html.append("</textarea></td></tr>");
        html.append("<tr><td>Qtd. Prognosticos:</td><td style=\"width: 50px; text-align: right\">" + nuPrognosticos
                + "</td></tr>");
        html.append("<tr><td>Qtd. Jogos: </td><td style=\"width: 50px; text-align: right\">" + apostas.size()
                + "</td></tr>");
        html.append("<tr><td>Valor Aposta: </td><td style=\"width: 50px; text-align: right\">R$ "
                + nf.format(valorAposta(apostas.get(0).length)) + "</td></tr>");
        html.append("<tr><td>Valor Total:</td><td style=\"width: 50px; text-align: right\">R$ "
                + nf.format(calcularValor(apostas)) + "</td></tr>");
        html.append("</table>");
        html.append("<br/>");
        html.append("<table cellpadding=0 cellspacing=0 width=\"40%\">");

        int tentativas = 0;
        while (tentativas < 100) {

            Integer[] resultado = Fechamentos.simularResultado(maiorFaixaPremiavel(), numeroDezenas());
            Arrays.sort(resultado);

            if (base == null || Fechamentos.conferir(resultado, base) >= 0) {

                html.append("<tr><td colspan=" + (nuPrognosticos + 2) + " style=\"background-color: #f5f5f5\"> "
                        + Arrays.toString(resultado) + "</td></tr>");

                BigDecimal premio = BigDecimal.ZERO;
                for (Integer[] aposta : apostas) {
                    Arrays.sort(aposta);

                    int numeroAcertos = Fechamentos.conferir(resultado, aposta);
                    if (numeroAcertos >= menorFaixaPremiavel()) {
                        html.append(conferirToHtml(resultado, aposta));
                        premio = premio.add(valorPremio(numeroAcertos));
                    }
                }

                html.append(String.format("<tr><td colspan=" + (nuPrognosticos - 1)
                        + " width=\"%s\"></td><td colspan=2 width=\"%s\">Premio: R$ </td>"
                        + "<td align=\"right\" width=\"%s\">%s</td></tr>", "50%", "30%", "20%", nf.format(premio)));

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
                Fechamentos.conferir(resultado, aposta)));

        result.append("</tr>\n");

        return result.toString();
    }

    public void gerarProva(int tamanhoBase, int numeroPrognosticos, int numeroDezenasFixas) {
        List<Integer[]> apostas = gerar(tamanhoBase, numeroPrognosticos, numeroDezenasFixas);

        float premiadas = 0;
        float tentativas = 0;
        while (tentativas < 1000000) {

            Integer[] resultado = Fechamentos.simularResultado(maiorFaixaPremiavel(), numeroDezenas());
            Arrays.sort(resultado);

            BigDecimal premio = BigDecimal.ZERO;
            for (Integer[] aposta : apostas) {
                Arrays.sort(aposta);
                int numeroAcertos = Fechamentos.conferir(resultado, aposta);
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
