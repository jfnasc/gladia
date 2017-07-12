package org.ganimede.services.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.ganimede.Concurso;
import org.ganimede.Sorteio;
import org.ganimede.TiposConcurso;
import org.ganimede.services.CargaResultadosService;
import org.ganimede.services.ServiceConfig;
import org.ganimede.utils.MathUtils;

public class CargaResultadosDuplaSenaImpl extends CargaResultadosService {

    @Override
    public void carregar() {
        Concurso ultimoConcursoGravado = getConcursoDAO().recuperarUltimoConcurso(TiposConcurso.DUPLA_SENA);

        String filepath = "/projetos/github/gladia/LoteriasCaixa/arquivos/resultados_dupla_sena.csv";
        BufferedReader reader = null;
        InputStreamReader in = null;

        try {
            File f = new File(filepath);
            in = new InputStreamReader(new FileInputStream(f));
            reader = new BufferedReader(in);

            List<Concurso> concursos = new ArrayList<>();

            String line = null;
            while ((line = reader.readLine()) != null) {
                Concurso concurso = carregarDadosConcurso(line);
                if (ultimoConcursoGravado == null || (concurso.getNuConcurso() > ultimoConcursoGravado.getNuConcurso())) {
                    // if (concurso.getNuConcurso() == 921 ||
                    // concurso.getNuConcurso() == 341) {
                    concursos.add(concurso);
                    // }
                }
            }

            salvarConcursos(concursos);

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

    @Override
    public Concurso carregarDadosConcurso(String line) {
        Concurso concurso = new Concurso();

        String[] dados = line.split(";");

        concurso.setNuConcurso(Integer.valueOf(dados[0]));
        try {
            concurso.setDtConcurso(ServiceConfig.sdf.parse(dados[1]));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        concurso.setTpConcurso(TiposConcurso.DUPLA_SENA.sigla);

        concurso.setSorteios(carregarDadosSorteio(concurso, line));

        return concurso;
    }

    @Override
    public List<Sorteio> carregarDadosSorteio(Concurso concurso, String line) {

        List<Sorteio> sorteios = new ArrayList<>();

        sorteios.add(carregarDadosSorteio(concurso, line, 1, 2));
        sorteios.add(carregarDadosSorteio(concurso, line, 2, 19));

        return sorteios;
    }

    private Sorteio carregarDadosSorteio(Concurso concurso, String line, int numeroSorteio, int pos) {
        Sorteio sorteio = new Sorteio();

        sorteio.setNuConcurso(concurso.getNuConcurso());
        sorteio.setNuSorteio(numeroSorteio);
        sorteio.setTpConcurso(concurso.getTpConcurso());

        String[] dados = line.split(";");

        List<Integer> dezenas = new ArrayList<>();
        while (dezenas.size() < 6) {
            dezenas.add(Integer.valueOf(dados[pos]));
            pos++;
        }
        sorteio.setDezenas(dezenas);
        sorteio.setHash(MathUtils.hash(dezenas));

        return sorteio;
    }

    public static void main(String[] args) {
        CargaResultadosService cgs = new CargaResultadosDuplaSenaImpl();
        cgs.carregar();
    }

}
