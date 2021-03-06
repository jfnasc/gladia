package org.ganimede.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ganimede.Sorteio;
import org.ganimede.dao.BaseDAO;
import org.ganimede.dao.ResultadoDAO;
import org.ganimede.utils.DatabaseUtils;

public class ResultadoDAOImpl extends BaseDAO implements ResultadoDAO {

    @Override
    public int qtAtrasoDezena(String tpConcurso, int nuSorteio, int nuDezena) {
        int result = 0;

        StringBuilder sb = new StringBuilder();

        sb.append("select qt_atraso "); 
        sb.append("  from loterias.tb_atrasos "); 
        sb.append(" where nu_concurso = ( "); 
        sb.append("     select max(nu_concurso) "); 
        sb.append("       from loterias.tb_atrasos "); 
        sb.append("      where tp_concurso = ? "); 
        sb.append("        and nu_sorteio  = ? "); 
        sb.append("       ) "); 
        sb.append("   and nu_dezena = ? ");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, tpConcurso);
            pstmt.setInt(2, nuSorteio);
            pstmt.setInt(3, nuDezena);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DatabaseUtils.close(rs, pstmt, conn);
        }

        return result;
    }

    @Override
    public boolean existeSorteioIgual(String tpConcurso, String hash) {
        boolean result = false;

        StringBuilder sb = new StringBuilder();
        sb.append("select hash ");
        sb.append("  from loterias.tb_sorteios ");
        sb.append(" where hash = ? ");
        sb.append("   and tp_concurso = ? ");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, hash);
            pstmt.setString(2, tpConcurso);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DatabaseUtils.close(rs, pstmt, conn);
        }

        return result;
    }

    @Override
    public List<Integer> buscarDezenasEmAtraso(String tpConcurso, int qtConcursos) {
        return buscarDezenasEmAtraso(tpConcurso, 1, qtConcursos);
    }

    @Override
    public List<Integer> buscarDezenasEmAtraso(String tpConcurso, int nuSorteio, int qtConcursos) {
        List<Integer> result = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("select nu_dezena ");
        sb.append("  from loterias.tb_atrasos ");
        sb.append(" where nu_sorteio = ? ");
        sb.append("   and tp_concurso = ? ");
        sb.append("   and nu_concurso = ( select max(nu_concurso) ");
        sb.append("                         from loterias.tb_concursos ");
        sb.append("                        where tp_concurso = ? ) ");
        sb.append("   and qt_atraso >= ? ");
        sb.append(" order by qt_atraso desc ");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setInt(1, nuSorteio);
            pstmt.setString(2, tpConcurso);
            pstmt.setString(3, tpConcurso);
            pstmt.setInt(4, qtConcursos);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                result.add(rs.getInt("nu_dezena"));
            }

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DatabaseUtils.close(rs, pstmt, conn);
        }

        return result;
    }

    @Override
    public boolean isDezenaFrequente(String tpConcurso, int nuDezena, int limite) {
        boolean result = false;

        StringBuilder sb = new StringBuilder();

        sb.append("select tb1.nu_dezena ");
        sb.append("  from ( ");
        sb.append("         select nu_dezena, ");
        sb.append("                count(nu_concurso) ");
        sb.append("           from loterias.tb_dezenas ");
        sb.append("          where tp_concurso = ? ");
        sb.append("          group by nu_dezena ");
        sb.append("          order by count(nu_concurso) desc ");
        sb.append("          limit ? ");
        sb.append("       ) tb1 ");
        sb.append("  where tb1.nu_dezena = ? ");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, tpConcurso);
            pstmt.setInt(2, limite);
            pstmt.setInt(3, nuDezena);

            rs = pstmt.executeQuery();

            result = rs.next();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DatabaseUtils.close(rs, pstmt, conn);
        }

        return result;
    }

    @Override
    public List<Sorteio> buscarSorteios(String tpConcurso, int nuSorteio) {
        return null;
    }

}
