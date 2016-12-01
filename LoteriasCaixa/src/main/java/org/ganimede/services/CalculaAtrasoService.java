package org.ganimede.services;

import org.ganimede.dao.ConcursoDAO;
import org.ganimede.dao.impl.ConcursoDAOImpl;

public abstract class CalculaAtrasoService {

    private ConcursoDAO concursoDAO;

    public abstract void calcular();

    protected ConcursoDAO getConcursoDAO() {
        if (this.concursoDAO == null) {
            this.concursoDAO = new ConcursoDAOImpl();
        }
        return this.concursoDAO;
    }

}
