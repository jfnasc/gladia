package org.ganimede.services.impl;

import org.ganimede.Concurso;
import org.ganimede.TiposConcurso;
import org.ganimede.services.CalculaAtrasoService;

public class CalculaAtrasoDuplaSena extends CalculaAtrasoService {

    @Override
    public void calcular() {
        // verifica qual o ultimo concurso calculado
        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(TiposConcurso.DUPLA_SENA.sigla);

        // se não há nenhum insere o primeiro da serie
        // para cada sorteio, vai inserindo a serie, ja computando os atrasos
    }

}
