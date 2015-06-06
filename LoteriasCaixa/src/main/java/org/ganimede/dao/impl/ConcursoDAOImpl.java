/**
 * 
 */
package org.ganimede.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.ganimede.Concurso;
import org.ganimede.Sorteio;
import org.ganimede.dao.BaseDAO;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.utils.DatabaseUtils;

/**
 * @author josen
 * 
 */
public class ConcursoDAOImpl extends BaseDAO implements ConcursoDAO {

    static SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

    /*
     * (non-Javadoc)
     * 
     * @see org.ganimede.dao.ConcursoDAO#salvarConcurso(org.ganimede.Concurso)
     */
    @Override
    public void salvarConcurso(Concurso concurso) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            conn = getDataSource().getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("INSERT INTO tb_concursos(nu_concurso, tp_concurso, dt_concurso) VALUES(?, ?, ?)");

            pstmt.setInt(1, concurso.getNuConcurso());
            pstmt.setString(2, concurso.getTpConcurso());
            pstmt.setString(3, sdf.format(concurso.getDtConcurso()));

            pstmt.executeUpdate();

            salvarSorteios(conn, concurso);

            conn.commit();
        }
        catch (SQLException e) {
            try {
                conn.rollback();
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            DatabaseUtils.close(pstmt, conn);
        }

    }

    private void salvarSorteios(Connection conn, Concurso concurso) throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO tb_sorteios(nu_sorteio, nu_concurso, tp_concurso, hash)"
                            + " VALUES(?, ?, ?, ?)");

            for (Sorteio sorteio : concurso.getSorteios()) {
                pstmt.setInt(1, sorteio.getNuSorteio());
                pstmt.setInt(2, concurso.getNuConcurso());
                pstmt.setString(3, concurso.getTpConcurso());
                pstmt.setString(4, "dsds");

                pstmt.executeUpdate();

                salvarDezenas(conn, sorteio);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DatabaseUtils.close(pstmt);
        }
    }

    private void salvarDezenas(Connection conn, Sorteio sorteio) throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement("INSERT INTO tb_dezenas(nu_sorteio, nu_concurso, tp_concurso, nu_dezena, nu_posicao)"
                            + " VALUES(?, ?, ?, ?, ?)");

            int pos = 1;
            for (Integer dezena : sorteio.getDezenas()) {
                pstmt.setInt(1, sorteio.getNuSorteio());
                pstmt.setInt(2, sorteio.getNuConcurso());
                pstmt.setString(3, sorteio.getTpConcurso());
                pstmt.setInt(4, dezena);
                pstmt.setInt(5, pos);

                pstmt.addBatch();
                pos++;
            }

            pstmt.executeBatch();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DatabaseUtils.close(pstmt);
        }
    }

    @Override
    public Concurso obterConcurso(Concurso concurso) {
        Concurso result = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getDataSource().getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("select nu_concurso, tp_concurso, dt_concurso from tb_concursos "
                            + "where nu_concurso =? and tp_concurso = ?");

            pstmt.setInt(1, concurso.getNuConcurso());
            pstmt.setString(2, concurso.getTpConcurso());
            pstmt.setString(3, sdf.format(concurso.getDtConcurso()));

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = new Concurso();

                result.setNuConcurso(rs.getInt("nu_concurso"));
                result.setTpConcurso(rs.getString("nu_concurso"));
                result.setDtConcurso(sdf.parse(rs.getString("dt_concurso")));
            }
        }
        catch (Exception e) {
            try {
                conn.rollback();
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            DatabaseUtils.close(rs, pstmt, conn);
        }

        return result;
    }

    @Override
    public Concurso obterUltimoConcursoSalvo(String tpConcurso) {
        Concurso result = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getDataSource().getConnection();
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("select nu_concurso, tp_concurso, dt_concurso from tb_concursos "
                            + "where nu_concurso = (select max(nu_concurso) from tb_concursos where tp_concurso = ?)");

            pstmt.setString(1, tpConcurso);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = new Concurso();

                result.setNuConcurso(rs.getInt("nu_concurso"));
                result.setTpConcurso(rs.getString("nu_concurso"));
                result.setDtConcurso(sdf.parse(rs.getString("dt_concurso")));
            }
        }
        catch (Exception e) {
            try {
                conn.rollback();
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        finally {
            DatabaseUtils.close(rs, pstmt, conn);
        }

        return result;
    }

}
