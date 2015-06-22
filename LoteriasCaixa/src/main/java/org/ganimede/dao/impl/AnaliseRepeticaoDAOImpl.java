package org.ganimede.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ganimede.Concurso;
import org.ganimede.TiposConcurso;
import org.ganimede.dao.AnaliseDAO;
import org.ganimede.dao.BaseDAO;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.utils.DatabaseUtils;

public class AnaliseRepeticaoDAOImpl extends BaseDAO implements AnaliseDAO {

    ConcursoDAO concursoDAO = null;

    public static void main(String[] args) {
        AnaliseDAO analise = new AnaliseRepeticaoDAOImpl();
        analise.executar(TiposConcurso.QUINA.sigla, 1);
        
        analise.executar(TiposConcurso.MEGA_SENA.sigla, 1);
        
        analise.executar(TiposConcurso.DUPLA_SENA.sigla, 1);
        analise.executar(TiposConcurso.DUPLA_SENA.sigla, 2);
    }

    @Override
    public void executar(String tpConcurso, int nuSorteio) {
        Concurso ultimoConcurso = getConcursoDAO().recuperarUltimoConcurso(tpConcurso);
        int ultimoConcursoRegistrado = recuperarUltimoConcursoRegistrado(tpConcurso, nuSorteio);

        int count = 0;
        List<Integer> p = new ArrayList<>();

        for (int i = ultimoConcursoRegistrado; i < ultimoConcurso.getNuConcurso(); i++) {
            for (int j = i + 1; j < ultimoConcurso.getNuConcurso(); j++) {
                if (contar(tpConcurso, i, j) == 0) {
                    count++;
                } else {
                    if (count == 0) {
                        count++;
                    }
                    p.add(new Integer(j - i));
                    System.out.println(String.format("%s\t%s\t%s\t%s", i, j, j - i, count));
                    salvar(nuSorteio, tpConcurso, i, j, j - i, count);
                    count = 0;
                    i = j - 1;
                    break;
                }
            }
        }
    }

    private int recuperarUltimoConcursoRegistrado(String tpConcurso, int nuSorteio) {
        int result = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("select max(nu_concurso_2)");
        sb.append("  from tb_frequencia_repeticao");
        sb.append(" where nu_sorteio = ?");
        sb.append("   and tp_concurso = ?");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, nuSorteio);
            pstmt.setString(2, tpConcurso);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            DatabaseUtils.close(rs, pstmt, conn);
        }

        return result;

    }

    private void salvar(int nuSorteio, String tpConcurso, int nuConcurso1, int nuConcurso2, int qtConcursos,
            int qtDezenas) {
        StringBuilder sb = new StringBuilder();

        sb.append("insert into tb_frequencia_repeticao( ");
        sb.append(" nu_sorteio, ");
        sb.append(" tp_concurso, ");
        sb.append(" nu_concurso_1, ");
        sb.append(" nu_concurso_2, ");
        sb.append(" qt_concursos, ");
        sb.append(" qt_dezenas) ");
        sb.append("values( ");
        sb.append(" ?, ");
        sb.append(" ?, ");
        sb.append(" ?, ");
        sb.append(" ?, ");
        sb.append(" ?, ");
        sb.append(" ?) ");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, nuSorteio);
            pstmt.setString(2, tpConcurso);
            pstmt.setInt(3, nuConcurso1);
            pstmt.setInt(4, nuConcurso2);
            pstmt.setInt(5, qtConcursos);
            pstmt.setInt(6, qtDezenas);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            DatabaseUtils.close(pstmt, conn);
        }

    }

    private int contar(String tpConcurso, int p1, int p2) {
        int result = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("select count(1) from (");
        sb.append("  select nu_dezena, count(nu_dezena) ");
        sb.append("    from tb_atrasos ");
        sb.append("   where tp_concurso = ? ");
        sb.append("     and qt_atraso = 0 ");
        sb.append("     and nu_concurso between ? and ? ");
        sb.append("   group by nu_dezena ");
        sb.append("  having count(nu_dezena) > 1 ");
        sb.append(") tb1");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, tpConcurso);
            pstmt.setInt(2, p1);
            pstmt.setInt(3, p2);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            DatabaseUtils.close(rs, pstmt, conn);
        }

        return result;
    }

    /**
     * @return the concursoDAO
     */
    public ConcursoDAO getConcursoDAO() {
        if (this.concursoDAO == null) {
            this.concursoDAO = new ConcursoDAOImpl();
        }
        return this.concursoDAO;
    }
}
