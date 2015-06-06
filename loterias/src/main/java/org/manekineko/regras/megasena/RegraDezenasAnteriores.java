package org.manekineko.regras.megasena;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.manekineko.Sorteio;
import org.manekineko.TiposSorteio;
import org.manekineko.regras.Regra;
import org.manekineko.regras.RegraBase;

public class RegraDezenasAnteriores extends RegraBase implements Regra {

    private Logger LOGGER = Logger.getLogger(RegraDezenasAnteriores.class);

    private int qtSorteios;

    public RegraDezenasAnteriores(int qtSorteios) {
        this.qtSorteios = qtSorteios;
    }

    @Override
    public void aplicar(List<Integer[]> p) {

        if (p == null || p.size() == 0) {
            return;
        }

        float total = Float.valueOf(p.size());

        List<Integer> dezenasAIgnorar = new ArrayList<>();

        Integer numeroUltimoSorteio = getResultadoDAO().buscarNroUltimoSorteioGravado(TiposSorteio.MEGASENA.sigla);

        for (int i = (numeroUltimoSorteio - (qtSorteios - 1)); i <= numeroUltimoSorteio; i++) {
            Sorteio sorteio = getResultadoDAO().buscarSorteio(TiposSorteio.MEGASENA.sigla, numeroUltimoSorteio);
            dezenasAIgnorar.addAll(sorteio.getDezenas());
        }

        LOGGER.debug(String.format("RegraSorteiosAnteriores: Dezenas a ignorar (sairam nos %s ultimos sorteios) [%s]",
                        qtSorteios, dezenasAIgnorar));

        try {

            List<Integer[]> aRemover = new ArrayList<Integer[]>();

            // senas
            for (Integer[] aposta : p) {
                for (Integer dezena : aposta) {
                    if (dezenasAIgnorar.contains(dezena)) {
                        aRemover.add(aposta);
                        break;
                    }
                }
            }

            p.removeAll(aRemover);

            LOGGER.debug("RegraSorteiosAnteriores: " + (Float.valueOf(aRemover.size()) / total * 100));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RegraDezenasAnteriores regra = new RegraDezenasAnteriores(2);

        List<Integer[]> p = new ArrayList<>();
        p.add(new Integer[] { 7, 19, 30, 35, 42, 47 });

        regra.aplicar(p);
    }
}
