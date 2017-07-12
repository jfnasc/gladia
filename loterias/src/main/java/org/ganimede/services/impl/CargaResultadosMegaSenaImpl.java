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

public class CargaResultadosMegaSenaImpl extends CargaResultadosService {

    @Override
    public void carregar() {
        Concurso ultimoConcursoGravado = getConcursoDAO().recuperarUltimoConcurso(TiposConcurso.MEGA_SENA);

        String filepath = getServiceConfig().getPath() + File.separator + "resultados_mega_sena.csv";
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
                    concursos.add(concurso);
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

        concurso.setTpConcurso(TiposConcurso.MEGA_SENA.sigla);

        concurso.setSorteios(carregarDadosSorteio(concurso, line));

        return concurso;
    }

    @Override
    public List<Sorteio> carregarDadosSorteio(Concurso concurso, String line) {

        List<Sorteio> sorteios = new ArrayList<>();

        Sorteio sorteio = new Sorteio();

        sorteio.setNuConcurso(concurso.getNuConcurso());
        sorteio.setNuSorteio(1);
        sorteio.setTpConcurso(concurso.getTpConcurso());

        List<Integer> dezenas = new ArrayList<>();

        String[] dados = line.split(";");
        for (int i = 2; i < dados.length; i++) {
            dezenas.add(Integer.valueOf(dados[i]));
        }
        sorteio.setDezenas(dezenas);
        sorteio.setHash(MathUtils.hash(dezenas));

        sorteios.add(sorteio);

        return sorteios;
    }

    public static void main(String[] args) {
        CargaResultadosService cgs = new CargaResultadosMegaSenaImpl();
        cgs.carregar();
    }

}
