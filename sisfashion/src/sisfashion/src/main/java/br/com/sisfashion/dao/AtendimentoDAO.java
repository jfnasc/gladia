package br.com.sisfashion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.sisfashion.dto.AtendimentoDTO;
import br.com.sisfashion.dto.ClienteDTO;
import br.com.sisfashion.dto.ServicoDTO;
import br.com.sisfashion.dto.UsuarioDTO;
import br.com.sisfashion.utils.DatabaseUtils;
import br.com.sisfashion.utils.ObjectUtils;

public class AtendimentoDAO {

	public AtendimentoDTO pesquisarAtendimento(AtendimentoDTO dto) throws Exception {

		AtendimentoDTO atendimento = new AtendimentoDTO();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select tb007.*, tb008.co_usuario ");
			sb.append("  from tb006_servicos              tb006, ");
			sb.append("       tb007_atendimentos          tb007, ");
			sb.append("       tb008_atendimentos_usuarios tb008, ");
			sb.append("       tb002_usuario               tb002 ");
			sb.append(" where tb008.co_usuario = tb002.co_usuario ");
			sb.append("   and tb007.co_atendimento = tb008.co_atendimento ");
			sb.append("   and tb007.co_servico = tb006.co_servico ");
			sb.append("   and tb007.co_atendimento = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				atendimento.setCodigo(String.valueOf(rs.getInt("co_atendimento")));
				atendimento.setDtAtendimento(rs.getString("dt_atendimento"));
				atendimento.setHrAtendimento(rs.getString("hr_atendimento"));
				atendimento.setServico(getServicosDAO().pesquisarServico(new ServicoDTO(atendimento.getCodigo())));
				atendimento.setCliente(getClientesDAO().pesquisarCliente(new ClienteDTO(rs.getString("co_cliente"))));
				atendimento.addUsuario(getUsuariosDAO().pesquisarUsuario(new UsuarioDTO(rs.getString("co_usuario"))));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return atendimento;

	}

	public List<AtendimentoDTO> pesquisar(AtendimentoDTO dto) throws Exception {

		List<AtendimentoDTO> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select tb007.*, tb008.co_usuario ");
			sb.append("  from tb006_servicos              tb006, ");
			sb.append("       tb007_atendimentos          tb007, ");
			sb.append("       tb008_atendimentos_usuarios tb008, ");
			sb.append("       tb002_usuario               tb002 ");
			sb.append(" where tb008.co_usuario = tb002.co_usuario ");
			sb.append("   and tb007.co_atendimento = tb008.co_atendimento ");
			sb.append("   and tb007.co_servico = tb006.co_servico ");

			if (dto != null) {

				if (ObjectUtils.isNotEmpty(dto.getDtAtendimento())) {
					sb.append("and tb007.dt_atendimento = ? ");
				}

				if (ObjectUtils.isNotEmpty(dto.getServico().getCodigo())) {
					sb.append("and tb007.co_servico = ? ");
				}

			}

			sb.append("order by upper(tb007.dt_atendimento)");

			pstmt = conn.prepareStatement(sb.toString());

			if (dto != null) {

				int pos = 1;

				if (ObjectUtils.isNotEmpty(dto.getDtAtendimento())) {
					pstmt.setString(pos++, dto.getDtAtendimento());
				}

				if (ObjectUtils.isNotEmpty(dto.getServico().getCodigo())) {
					pstmt.setInt(pos++, Integer.parseInt(dto.getServico().getCodigo()));
				}
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				AtendimentoDTO atendimento = new AtendimentoDTO();

				atendimento.setCodigo(String.valueOf(rs.getInt("co_atendimento")));
				atendimento.setDtAtendimento(rs.getString("dt_atendimento"));
				atendimento.setServico(getServicosDAO().pesquisarServico(new ServicoDTO(atendimento.getCodigo())));
				atendimento.setCliente(getClientesDAO().pesquisarCliente(new ClienteDTO(rs.getString("co_cliente"))));
				atendimento.addUsuario(getUsuariosDAO().pesquisarUsuario(new UsuarioDTO(rs.getString("co_usuario"))));

				result.add(atendimento);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return result;

	}

	public List<AtendimentoDTO> listar() throws Exception {

		return pesquisar(null);

	}

	public void salvar(AtendimentoDTO dto) throws Exception {

		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionFactory.getConnection();

			sb.append(" INSERT INTO tb007_atendimentos (co_cliente,co_servico,dt_atendimento,hr_atendimento) ");
			sb.append(" VALUES (?,?,?,?)");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getCliente().getCodigo());
			pstmt.setString(2, dto.getServico().getCodigo());
			pstmt.setString(3, dto.getDtAtendimento());
			pstmt.setString(4, dto.getHrAtendimento());

			pstmt.executeUpdate();

			dto.setCodigo(DatabaseUtils.getHighestID(conn).toString());

			pstmt.close();

			sb.setLength(0);

			sb.append(" INSERT INTO tb008_atendimentos_usuarios (co_atendimento, co_usuario) ");
			sb.append(" VALUES (?,?)");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getCodigo());
			pstmt.setString(2, dto.getUsuario().getCodigo());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, conn);
		}
	}

	public void atualizar(AtendimentoDTO dto) throws Exception {

		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionFactory.getConnection();

			sb.append(" UPDATE tb007_atendimentos ");
			sb.append("    set co_cliente=?,co_servico=?,dt_atendimento=?");
			sb.append("  WHERE co_atendimento = ?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getCliente().getCodigo());
			pstmt.setString(2, dto.getServico().getCodigo());
			pstmt.setString(3, dto.getDtAtendimento());
			pstmt.setString(4, dto.getCodigo());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, conn);
		}
	}

	public void excluir(AtendimentoDTO dto) throws Exception {

		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionFactory.getConnection();

			sb.append(" delete from tb008_atendimentos_usuarios");
			sb.append("  WHERE co_atendimento = ?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			pstmt.executeUpdate();

			pstmt.close();

			sb.setLength(0);

			sb.append(" delete from tb007_atendimentos");
			sb.append("  WHERE co_atendimento = ?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, conn);
		}
	}

	private ClientesDAO clientesDAO;

	private ClientesDAO getClientesDAO() {
		if (this.clientesDAO == null) {
			this.clientesDAO = new ClientesDAO();
		}
		return this.clientesDAO;
	}

	private ServicosDAO servicosDAO;

	private ServicosDAO getServicosDAO() {
		if (this.servicosDAO == null) {
			this.servicosDAO = new ServicosDAO();
		}
		return this.servicosDAO;
	}

	private UsuariosDAO usuariosDAO;

	private UsuariosDAO getUsuariosDAO() {
		if (this.usuariosDAO == null) {
			this.usuariosDAO = new UsuariosDAO();
		}
		return this.usuariosDAO;
	}

}
