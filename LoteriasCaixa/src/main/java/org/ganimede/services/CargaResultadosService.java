package org.ganimede.services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import org.ganimede.Concurso;
import org.ganimede.Sorteio;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;

public abstract class CargaResultadosService {

    protected SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

    private ConcursoDAO concursoDAO;

    public abstract void carregar();

    public abstract Concurso carregarDadosConcurso(String line);

    public abstract List<Sorteio> carregarDadosSorteio(Concurso concurso, String line);

    protected void salvarConcursos(Collection<Concurso> concursos) {
        getConcursoDAO().salvarConcursos(concursos);
    }

    protected ConcursoDAO getConcursoDAO() {
        if (this.concursoDAO == null) {
            this.concursoDAO = new ConcursoDAOImpl();
        }
        return this.concursoDAO;
    }
}
