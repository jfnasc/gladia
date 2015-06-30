package org.ganimede.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.ganimede.Atraso;
import org.ganimede.Concurso;
import org.ganimede.TiposConcurso;
import org.ganimede.dao.AtrasosDAO;
import org.ganimede.dao.BaseDAO;
import org.ganimede.dao.ConcursoDAO;
import org.ganimede.utils.DatabaseUtils;

public class AtrasosDAOImpl extends BaseDAO implements AtrasosDAO {

    private ConcursoDAO concursoDAO;

    @Override
    public Concurso recuperarUltimoConcurso(String tpConcurso, int numeroSorteio) {
        Concurso result = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getDataSource().getConnection();

            StringBuilder sb = new StringBuilder();

            sb.append("select tb1.nu_concurso, tb1.tp_concurso, tb1.dt_concurso ");
            sb.append("  from tb_concursos tb1 ");
            sb.append(" where tb1.nu_concurso = ( select max(nu_concurso) ");
            sb.append("                             from tb_atrasos ");
            sb.append("                            where tp_concurso = ? ");
            sb.append("                              and nu_sorteio  = ?) ");
            sb.append("   and tb1.tp_concurso = ? ");

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, tpConcurso);
            pstmt.setInt(2, numeroSorteio);
            pstmt.setString(3, tpConcurso);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = new Concurso();

                result.setNuConcurso(rs.getInt("nu_concurso"));
                result.setTpConcurso(rs.getString("nu_concurso"));
                result.setDtConcurso(sdf.parse(rs.getString("dt_concurso")));
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

    public void registrarAtrasos(TiposConcurso tpConcurso, int numeroSorteio) {

        Concurso ultimoConcursoRegistrado = getConcursoDAO().recuperarUltimoConcurso(tpConcurso);
        Concurso ultimoAtrasoRegistrado = recuperarUltimoConcurso(tpConcurso.sigla, numeroSorteio);

        int inicioSerie = 1;
        if (ultimoAtrasoRegistrado != null) {
            inicioSerie = ultimoAtrasoRegistrado.getNuConcurso() + 1;
        }

        for (int i = inicioSerie; i <= ultimoConcursoRegistrado.getNuConcurso(); i++) {
            registrarSerieAtrasos(i, tpConcurso.sigla, numeroSorteio, tpConcurso.nuDezenas);
        }

        calcularAtrasos(tpConcurso.sigla, numeroSorteio);
    }

    private void registrarSerieAtrasos(int nuConcurso, String tpConcurso, int numeroSorteio, int qtDezenas) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getDataSource().getConnection();
            conn.setAutoCommit(false);

            StringBuilder sb = new StringBuilder();

            sb.append("insert into tb_atrasos (nu_sorteio,nu_concurso,tp_concurso,nu_dezena,qt_atraso) ");
            sb.append("values (?,?,?,?,?)");

            pstmt = conn.prepareStatement(sb.toString());

            for (int dezena = 1; dezena <= qtDezenas; dezena++) {
                pstmt.setInt(1, numeroSorteio);
                pstmt.setInt(2, nuConcurso);
                pstmt.setString(3, tpConcurso);
                pstmt.setInt(4, dezena);
                pstmt.setInt(5, 1);

                pstmt.addBatch();
            }

            pstmt.executeBatch();

            conn.commit();

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
    }

    private ConcursoDAO getConcursoDAO() {
        if (this.concursoDAO == null) {
            this.concursoDAO = new ConcursoDAOImpl();
        }
        return this.concursoDAO;
    }

    @Override
    public void calcularAtrasos(String tpConcurso, int numeroSorteio) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            conn = getDataSource().getConnection();
            conn.setAutoCommit(false);

            StringBuilder sb = new StringBuilder();

            sb.append("select nu_sorteio, nu_concurso, tp_concurso, nu_dezena, qt_atraso, ic_calculado");
            sb.append("  from tb_atrasos  ");
            sb.append(" where nu_sorteio = ? ");
            sb.append("   and tp_concurso = ? ");
            sb.append("   and ic_calculado = ?");
            sb.append(" order by nu_concurso");

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setInt(1, numeroSorteio);
            pstmt.setString(2, tpConcurso);
            pstmt.setString(3, "N");

            rs = pstmt.executeQuery();

            List<Atraso> atrasos = new ArrayList<Atraso>();
            while (rs.next()) {
                Atraso atraso = new Atraso();

                atraso.setNuConcurso(rs.getInt("nu_concurso"));
                atraso.setNuSorteio(rs.getInt("nu_sorteio"));
                atraso.setTpConcurso(rs.getString("tp_concurso"));
                atraso.setNuDezena(rs.getInt("nu_dezena"));
                atraso.setQtAtraso(rs.getInt("qt_atraso"));
                atraso.setIcCalculado(rs.getString("ic_calculado"));

                atrasos.add(atraso);

                if (atrasos.size() == 10) {
                    calcularAtrasos(atrasos);
                    atrasos.clear();
                }
            }

            calcularAtrasos(atrasos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtils.close(rs, pstmt, conn);
        }
    }

    private void calcularAtrasos(List<Atraso> atrasos) {
        if (atrasos == null) {
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            conn = getDataSource().getConnection();
            conn.setAutoCommit(false);

            StringBuilder sb = new StringBuilder();

            sb.append("update tb_atrasos");
            sb.append("   set qt_atraso = (");
            sb.append("           select qt_atraso + 1");
            sb.append("             from tb_atrasos");
            sb.append("            where nu_sorteio = ?");
            sb.append("              and nu_concurso = ?");
            sb.append("              and tp_concurso = ?");
            sb.append("              and nu_dezena = ?");
            sb.append("       )");
            sb.append(" where nu_sorteio = ? ");
            sb.append("   and nu_concurso = ? ");
            sb.append("   and tp_concurso = ? ");
            sb.append("   and nu_dezena = ?");

            pstmt = conn.prepareStatement(sb.toString());

            for (Atraso atraso : atrasos) {

                if (atraso.getNuConcurso() > 1) {
                    pstmt.setInt(1, atraso.getNuSorteio());
                    pstmt.setInt(2, atraso.getNuConcurso() - 1);
                    pstmt.setString(3, atraso.getTpConcurso());
                    pstmt.setInt(4, atraso.getNuDezena());
                    pstmt.setInt(5, atraso.getNuSorteio());
                    pstmt.setInt(6, atraso.getNuConcurso());
                    pstmt.setString(7, atraso.getTpConcurso());
                    pstmt.setInt(8, atraso.getNuDezena());

                    pstmt.executeUpdate();
                }

                zerarAtrasoDezenasSorteadas(conn, atraso);

                atualizarStatus(conn, atraso);

                System.out.println(atraso);
            }

            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DatabaseUtils.close(pstmt, conn);
        }
    }

    private void zerarAtrasoDezenasSorteadas(Connection conn, Atraso atraso) {
        PreparedStatement pstmt = null;

        try {
            StringBuilder sb = new StringBuilder();

            sb.append("update tb_atrasos ");
            sb.append("   set qt_atraso = 0 ");
            sb.append(" where nu_sorteio = ? ");
            sb.append("   and nu_concurso = ? ");
            sb.append("   and tp_concurso = ? ");
            sb.append("   and nu_dezena in ( ");
            sb.append("          select nu_dezena ");
            sb.append("            from tb_dezenas ");
            sb.append("           where nu_sorteio = ? ");
            sb.append("             and nu_concurso = ? ");
            sb.append("             and tp_concurso = ? ");
            sb.append("       ); ");

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setInt(1, atraso.getNuSorteio());
            pstmt.setInt(2, atraso.getNuConcurso());
            pstmt.setString(3, atraso.getTpConcurso());
            pstmt.setInt(4, atraso.getNuSorteio());
            pstmt.setInt(5, atraso.getNuConcurso());
            pstmt.setString(6, atraso.getTpConcurso());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtils.close(pstmt);
        }
    }

    private void atualizarStatus(Connection conn, Atraso atraso) {
        PreparedStatement pstmt = null;

        try {
            StringBuilder sb = new StringBuilder();

            sb.append("update tb_atrasos ");
            sb.append("   set ic_calculado = ? ");
            sb.append(" where nu_sorteio = ? ");
            sb.append("   and nu_concurso = ? ");
            sb.append("   and tp_concurso = ? ");

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, "S");
            pstmt.setInt(2, atraso.getNuSorteio());
            pstmt.setInt(3, atraso.getNuConcurso());
            pstmt.setString(4, atraso.getTpConcurso());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseUtils.close(pstmt);
        }
    }

    public static void main(String[] args) {
        AtrasosDAO dao = new AtrasosDAOImpl();
        dao.registrarAtrasos(TiposConcurso.LOTO_FACIL, 1);
    }

}
