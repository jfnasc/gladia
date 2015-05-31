/**
 * 
 */
package org.manekineko.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.manekineko.Sorteio;
import org.manekineko.dao.BaseDAO;
import org.manekineko.dao.ResultadoDAO;
import org.manekineko.utils.DatabaseUtils;

/**
 * @author josen
 * 
 */
public class ResultadoBaseDAOImpl extends BaseDAO implements ResultadoDAO {

	/**
	 * 
	 */
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

	@Override
	public void atualizarHashSorteios(String tpSorteio) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement("select nu_sorteio FROM TB_SORTEIOS "
					+ " where hash is null and tp_sorteio = ? order by nu_sorteio");
			pstmt.setString(1, tpSorteio);

			rs = pstmt.executeQuery();

			Map<Integer, String> p = new HashMap<Integer, String>();
			while (rs.next()) {
				p.put(rs.getInt(1), calcularHash(rs.getInt(1), tpSorteio));
			}

			rs.close();
			pstmt.close();

			pstmt = conn.prepareStatement("update TB_SORTEIOS"
					+ " set hash = ? where nu_sorteio = ? and tp_sorteio = ?");

			int count = 0;
			for (Integer key : p.keySet()) {

				pstmt.setString(1, p.get(key));
				pstmt.setInt(2, key);
				pstmt.setString(3, tpSorteio);

				pstmt.addBatch();

				if (++count % 10 == 0) {
					pstmt.executeBatch();
					conn.commit();
					System.out.println(count);
				}
			}

			pstmt.executeBatch();
			conn.commit();
			System.out.println(count);

		} catch (SQLException e) {
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

	@Override
	public List<Integer> buscarDezenasSorteadas(int nuSorteio, String tpSorteio) {
		List<Integer> result = new ArrayList<Integer>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("select nu_dezena from tb_dezenas where nu_sorteio = ? and tp_sorteio = ?");
			pstmt.setInt(1, nuSorteio);
			pstmt.setString(2, tpSorteio);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt("nu_dezena"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;
	}

	@Override
	public int buscarNroUltimoSorteioGravado(String tpSorteio) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("SELECT max(nu_sorteio) FROM TB_SORTEIOS where tp_sorteio = ?");
			pstmt.setString(1, tpSorteio);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;

	}

	@Override
	public int buscarNumeroUltimoSorteioAtraso(String tpSorteio) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("SELECT MAX(nu_sorteio) FROM TB_ATRASOS where tp_sorteio = ?");
			pstmt.setString(1, tpSorteio);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1) + 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;

	}

	@Override
	public String calcularHash(int nuSorteio, String tpSorteio) {
		List<Integer> dezenas = buscarDezenasSorteadas(nuSorteio, tpSorteio);
		return calcularHash(dezenas);
	}

	@Override
	public String calcularHash(List<Integer> dezenas) {
		StringBuilder sb = new StringBuilder();
		for (Integer dezena : dezenas) {
			sb.append("#" + dezena);
		}

		return DatabaseUtils.hash(sb.toString().substring(1));
	}

	@Override
	public boolean isDezenaEmAtraso(String tpSorteio, int qtSorteios, int dezena) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("select nu_dezena FROM TB_ATRASOS");
			sb.append(" where nu_sorteio = (");
			sb.append("   SELECT MAX(nu_sorteio) FROM TB_ATRASOS");
			sb.append(" ) and qt_atraso >= ?");
			sb.append(" and tp_sorteio = ?");
			sb.append(" and nu_dezena = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, qtSorteios);
			pstmt.setString(2, tpSorteio);
			pstmt.setInt(3, dezena);

			rs = pstmt.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;

	}

	@Override
	public boolean existeAtrasoRegistrado(int nuSorteio, String tpSorteio) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("SELECT nu_sorteio FROM TB_ATRASOS where nu_sorteio = ? and tp_sorteio = ?");

			pstmt.setInt(1, nuSorteio);
			pstmt.setString(2, tpSorteio);

			rs = pstmt.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;

	}

	@Override
	public boolean existeSorteio(String tpSorteio, String hash) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("select hash from tb_sorteios where tp_sorteio = ? and hash = ?");
			pstmt.setString(1, tpSorteio);
			pstmt.setString(2, hash);

			rs = pstmt.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;

	}

	@Override
	public boolean existeSorteioIgual(String tpSorteio, String hash) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("select hash from tb_sorteios where tp_sorteio = ? and hash = ?");
			pstmt.setString(1, tpSorteio);
			pstmt.setString(2, hash);

			rs = pstmt.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;

	}

	@Override
	public void exportarAtrasos(String tpSorteio) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();
			pstmt = conn.prepareStatement("SELECT distinct nu_sorteio FROM TB_ATRASOS order by nu_sorteio");

			rs = pstmt.executeQuery();

			List<Integer> sorteios = new ArrayList<Integer>();
			while (rs.next()) {
				sorteios.add(rs.getInt(1));
			}

			rs.close();
			pstmt.close();

			pstmt = conn.prepareStatement("SELECT * FROM TB_ATRASOS where nu_sorteio = ?");
			for (Integer nuSorteio : sorteios) {
				StringBuilder sb = new StringBuilder();
				sb.append(nuSorteio);

				pstmt.setInt(1, nuSorteio);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					sb.append("," + rs.getInt(3));
				}
				System.out.println(sb.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}
	}

	@Override
	public void registrarAtrasos(int nroUltimoSorteio, String tpSorteio, int qtDezenas) {

		//
		int nuUltimoSorteioAtraso = buscarNumeroUltimoSorteioAtraso(tpSorteio);

		//
		if (nroUltimoSorteio == nuUltimoSorteioAtraso) {
			return;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement("INSERT INTO TB_ATRASOS VALUES(?, ?, ?, ?)");

			Map<Integer, Integer> p = buscarDezenasEmAtraso(nuUltimoSorteioAtraso, tpSorteio, qtDezenas);

			int count = 0;
			for (int i = nuUltimoSorteioAtraso; i <= nroUltimoSorteio; i++) {

				// atrasos
				for (Integer key : p.keySet()) {
					Integer value = p.get(key) + 1;
					p.put(key, value);
				}

				// resultados do sorteio posterior, para esses, zerar o atraso
				List<Integer> dezenas = buscarDezenasSorteadas(i, tpSorteio);
				System.out.println(dezenas);
				System.out.println(p);
				for (Integer dezena : dezenas) {
					p.put(dezena, 0);
				}

				for (int key : p.keySet()) {
					pstmt.setInt(1, i);
					pstmt.setString(2, tpSorteio);
					pstmt.setInt(3, key);
					pstmt.setInt(4, p.get(key));

					pstmt.addBatch();
				}

				pstmt.executeBatch();

				if (++count % 10 == 0) {
					conn.commit();
					System.out.println(count);
				}
			}

			conn.commit();
			System.out.println(count);

		} catch (SQLException e) {
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

	@Override
	public List<Integer> buscarUltimoResultado(String tpSorteio) {
		List<Integer> result = new ArrayList<Integer>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("select nu_dezena ");
			sb.append("  from tb_dezenas ");
			sb.append(" where tp_sorteio = ? ");
			sb.append("   and nu_sorteio = (select max(nu_sorteio) from tb_sorteios where tp_sorteio = ?) ");
			sb.append(" order by nu_sorteio, nu_dezena ");
			sb.append("; ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, tpSorteio);
			pstmt.setString(2, tpSorteio);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt("nu_dezena"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;
	}

	@Override
	public void salvarSorteios(List<Sorteio> sorteios) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getDataSource().getConnection();
			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(" insert into tb_sorteios" + " (nu_sorteio, tp_sorteio, dt_sorteio)"
					+ " values (?,?,?)");

			for (Sorteio sorteio : sorteios) {
				pstmt.setInt(1, sorteio.getNuSorteio());
				pstmt.setString(2, sorteio.getTpSorteio());
				pstmt.setString(3, sdf.format(sorteio.getDtSorteio()));

				pstmt.execute();

				salvarDezenas(conn, sorteio);

				conn.commit();

				System.out.println(sorteio);
			}

		} catch (SQLException e) {
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

	private void salvarDezenas(Connection conn, Sorteio sorteio) {

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(" insert into tb_dezenas"
					+ " (nu_sorteio, tp_sorteio, nu_dezena, nu_posicao)" + " values (?,?,?,?)");

			for (int i = 0; i < sorteio.getDezenas().size(); i++) {
				pstmt.setInt(1, sorteio.getNuSorteio());
				pstmt.setString(2, sorteio.getTpSorteio());
				pstmt.setInt(3, sorteio.getDezenas().get(i));
				pstmt.setInt(4, i + 1);

				pstmt.addBatch();
			}

			pstmt.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(pstmt);
		}
	}

	@Override
	public List<Integer> buscarDezenasEmAtraso(String tpSorteio, int qtSorteios) {
		List<Integer> result = new ArrayList<Integer>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("select nu_dezena from tb_atrasos where tp_sorteio = ? "
					+ " and qt_atraso >= ?" + " and nu_sorteio = (select max(nu_sorteio) from tb_atrasos);");
			pstmt.setString(1, tpSorteio);
			pstmt.setInt(2, qtSorteios);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt("nu_dezena"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;
	}

	@Override
	public int mediaAtraso(String tpSorteio) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("select ceiling(avg(qt_atraso)) from tb_atrasos");
			pstmt.setString(1, tpSorteio);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;
	}

	@Override
	public List<Integer> buscarDezenasEmAtraso(String tpSorteio) {
		List<Integer> result = new ArrayList<Integer>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("select nu_dezena " + " from tb_atrasos where tp_sorteio = ? "
					+ " and qt_atraso >= (select ceiling(avg(qt_atraso)) from tb_atrasos)"
					+ " and nu_sorteio = (select max(nu_sorteio) from tb_atrasos);");

			pstmt.setString(1, tpSorteio);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt("nu_dezena"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;
	}

	@Override
	public boolean isDezenaEmAtraso(String tpSorteio, int dezena) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("select nu_dezena FROM TB_ATRASOS");
			sb.append(" where nu_sorteio = (");
			sb.append("   SELECT MAX(nu_sorteio) FROM TB_ATRASOS");
			sb.append(" ) and qt_atraso >= (select ceiling(avg(qt_atraso)) from tb_atrasos)");
			sb.append(" and tp_sorteio = ?");
			sb.append(" and nu_dezena = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, tpSorteio);
			pstmt.setInt(2, dezena);

			rs = pstmt.executeQuery();

			result = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;

	}

	@Override
	public void limparResultados(String tpSorteio) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {

			int result = 0;

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("delete from tb_dezenas where nu_sorteio in ("
					+ "select nu_sorteio from tb_sorteios where tp_sorteio = ?)");
			pstmt.setString(1, tpSorteio);

			result = pstmt.executeUpdate();

			pstmt.close();

			System.out.println(String.format("[%s] Dezenas removidas", result));

			pstmt = conn.prepareStatement("delete from tb_sorteios where tp_sorteio = ?");
			pstmt.setString(1, tpSorteio);

			result = pstmt.executeUpdate();

			System.out.println(String.format("[%s] Registros removidos", result));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(pstmt, conn);
		}
	}

	private Map<Integer, Integer> buscarDezenasEmAtraso(int nuUltimoSorteioAtraso, String tpSorteio, int qtDezenas) {
		Map<Integer, Integer> p = new HashMap<Integer, Integer>();

		if (nuUltimoSorteioAtraso - 1 == 0) {
			for (int j = 1; j <= qtDezenas; j++) {
				p.put(j, 0);
			}
			return p;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getDataSource().getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM TB_ATRASOS " + "where nu_sorteio = ? and tp_sorteio = ?");
			pstmt.setInt(1, nuUltimoSorteioAtraso - 1);
			pstmt.setString(2, tpSorteio);

			rs = pstmt.executeQuery();

			// contabiliza os atrasos, +1 para cada ocorrencia

			while (rs.next()) {
				p.put(rs.getInt("nu_dezena"), rs.getInt("qt_atraso"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return p;
	}

	@Override
	public List<Sorteio> listarSorteios(String tpSorteio) {
		List<Sorteio> result = new ArrayList<Sorteio>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("select * from tb_sorteios " + "where tp_sorteio = ? order by nu_sorteio");
			pstmt.setString(1, tpSorteio);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Sorteio sorteio = new Sorteio(tpSorteio);
				sorteio.setNuSorteio(rs.getInt("nu_sorteio"));
				sorteio.setDezenas(buscarDezenasSorteadas(rs.getInt("nu_sorteio"), tpSorteio));
				result.add(sorteio);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;
	}

	@Override
	public List<Integer> listarAtrasos(String tpSorteio, Integer nuDezena) {
		List<Integer> result = new ArrayList<Integer>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getDataSource().getConnection();

			pstmt = conn.prepareStatement("select qt_atraso from tb_atrasos "
					+ "where tp_sorteio = ? and nu_dezena = ? order by nu_sorteio");
			pstmt.setString(1, tpSorteio);
			pstmt.setInt(2, nuDezena);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseUtils.close(rs, pstmt, conn);
		}

		return result;
	}
}
