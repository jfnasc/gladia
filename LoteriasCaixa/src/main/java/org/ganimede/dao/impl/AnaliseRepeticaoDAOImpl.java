package org.ganimede.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.ganimede.Concurso;
import org.ganimede.TiposConcurso;
import org.ganimede.dao.AnaliseDAO;
import org.ganimede.dao.BaseDAO;
import org.ganimede.dao.ConcursoDAO;

public class AnaliseRepeticaoDAOImpl extends BaseDAO implements AnaliseDAO {
    private ConcursoDAO concursoDAO;

    public static void main(String[] args) {
        AnaliseDAO analise = new AnaliseRepeticaoDAOImpl();
        analise.executar(TiposConcurso.QUINA.sigla);
    }

    @Override
    public void executar(String tpConcurso){
        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(tpConcurso);
        System.out.println(ultimoConcurso);
        
        StringBuilder sb = new StringBuilder();
        
        sb.append("select nu_dezena, count(nu_dezena) "); 
        sb.append("  from tb_atrasos "); 
        sb.append(" where tp_concurso = 'QN' "); 
        sb.append("   and qt_atraso = 0 "); 
        sb.append("   and nu_concurso between ? and ? "); 
        sb.append(" group by nu_dezena "); 
        sb.append(" having count(nu_dezena) > 1 ");
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        for (int i = 1; i <= ultimoConcurso.getNuConcurso(); i++) {
            System.out.println(i);
        }
    }

    /**
     * @return the concursoDAO
     */
    public ConcursoDAO getConcursoDAO() {
        if (this.concursoDAO == null) {
            this.concursoDAO = new ConcursoDAOImpl();
        }
        return concursoDAO;
    }
}
