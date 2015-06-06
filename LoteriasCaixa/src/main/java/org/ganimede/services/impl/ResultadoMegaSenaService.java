package org.ganimede.services.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.ganimede.Concurso;
import org.ganimede.Sorteio;
import org.ganimede.TiposConcurso;
import org.ganimede.services.BaseService;
import org.ganimede.services.ResultadoService;

public class ResultadoMegaSenaService extends BaseService implements ResultadoService {

    private static Logger LOGGER = Logger.getLogger(ResultadoMegaSenaService.class);

    @Override
    public Concurso buscarUltimoConcurso() {
        Concurso resultado = null;

        String url = config.getString("url.ms.ultimosorteio");
        try {
            String consultaWebService = response(url);

            resultado = new Concurso();

            resultado.setNuConcurso(obterNumeroConcurso(consultaWebService));
            resultado.setTpConcurso(TiposConcurso.MEGASENA.sigla);
            resultado.setDtConcurso(obterDataConcurso(consultaWebService));

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public Concurso buscarConcurso(int numeroConcurso) {
        Concurso resultado = null;

        String url = config.getString("url.ms");
        try {
            String consultaWebService = response(url + numeroConcurso);

            resultado = new Concurso();

            resultado.setNuConcurso(obterNumeroConcurso(consultaWebService));
            resultado.setTpConcurso(TiposConcurso.MEGASENA.sigla);
            resultado.setDtConcurso(obterDataConcurso(consultaWebService));

            obterSorteios(resultado, consultaWebService);
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public void atualizarResultados() {

        Concurso ultimoConcurso = buscarUltimoConcurso();

        Concurso ultimoConcursoSalvo = getConcursoDAO().obterUltimoConcursoSalvo(TiposConcurso.MEGASENA.sigla);
        if (ultimoConcursoSalvo == null) {
            ultimoConcursoSalvo = new Concurso();
            ultimoConcursoSalvo.setTpConcurso(TiposConcurso.MEGASENA.sigla);
            ultimoConcursoSalvo.setNuConcurso(1);
        }

        // for (int i = ultimoConcursoSalvo.getNuConcurso(); i <=
        // ultimoConcurso.getNuConcurso(); i++) {
        for (int i = ultimoConcursoSalvo.getNuConcurso(); i <= 10; i++) {
            Concurso concurso = buscarConcurso(i);
            getConcursoDAO().salvarConcurso(concurso);
        }

        System.out.println(ultimoConcurso);
    }

    @Override
    public boolean isConcursoCadastrado(int numeroConcurso) {
        return false;
    }

    /**
     * 
     * @param s
     * @return
     */
    private int obterNumeroConcurso(String s) {
        int result = 0;

        Pattern p = Pattern.compile("<Concurso>[0-9]*</Concurso>");
        Matcher m = p.matcher(s);

        if (m.find()) {
            LOGGER.debug(s.substring(m.start() + 10, m.end() - 11));
            result = Integer.valueOf(s.substring(m.start() + 10, m.end() - 11));
        }

        return result;
    }

    /**
     * 
     * @param s
     * @return
     */
    private Date obterDataConcurso(String s) {

        //
        Date result = null;

        Pattern p = Pattern.compile("<Data>[-T:0-9]*</Data>");
        Matcher m = p.matcher(s);

        try {
            if (m.find()) {
                LOGGER.debug(s.substring(m.start() + 6, m.end() - 16));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                result = sdf.parse(s.substring(m.start() + 6, m.end() - 16));
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 
     * @param s
     * @return
     */
    private void obterSorteios(Concurso concurso, String s) {
        Sorteio sorteio = new Sorteio();
        
        sorteio.setNuSorteio(1);
        sorteio.setNuConcurso(concurso.getNuConcurso());
        sorteio.setTpConcurso(concurso.getTpConcurso());
        
        List<Integer> result = new ArrayList<Integer>();

        Pattern p = Pattern.compile("<Dezenas>[|0-9]*</Dezenas>");
        Matcher m = p.matcher(s);

        if (m.find()) {
            LOGGER.debug(s.substring(m.start() + 9, m.end() - 10));
            String tmp = s.substring(m.start() + 9, m.end() - 10);
            for (String dezena : tmp.split("\\|")) {
                result.add(Integer.valueOf(dezena));
            }
        }

        sorteio.setDezenas(result);
        
        concurso.addSorteio(sorteio);
    }

    public static void main(String[] args) {
        ResultadoMegaSenaService r = new ResultadoMegaSenaService();
        r.atualizarResultados();
    }
}
