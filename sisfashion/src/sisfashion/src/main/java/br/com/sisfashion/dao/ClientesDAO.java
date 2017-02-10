package br.com.sisfashion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.sisfashion.dto.ClienteDTO;
import br.com.sisfashion.utils.DatabaseUtils;

public class ClientesDAO {

	public ClienteDTO pesquisarCliente(ClienteDTO dto) throws Exception {

		ClienteDTO cliente = new ClienteDTO();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * ");
			sb.append("  from tb005_clientes ");
			sb.append(" where co_cliente = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cliente.setCodigo(String.valueOf(rs.getInt("co_cliente")));
				cliente.setNome(rs.getString("no_cliente"));
				cliente.setEmail(rs.getString("de_email"));
				cliente.setDtNascimento(new Date(rs.getLong("dt_nascimento")));
				cliente.setTelefone(rs.getString("de_telefone"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return cliente;

	}

	public List<ClienteDTO> pesquisar(ClienteDTO dto) throws Exception {

		List<ClienteDTO> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * from tb005_clientes where 1 = 1 ");

			if (dto != null) {

				if (dto.getNome() != null) {
					sb.append("and upper(no_cliente) like ? ");
				}

				if (dto.getEmail() != null) {
					sb.append("and upper(de_email) like ? ");
				}

			}

			sb.append("order by upper(no_cliente)");

			pstmt = conn.prepareStatement(sb.toString());

			if (dto != null) {

				int pos = 1;

				if (dto.getNome() != null) {
					pstmt.setString(pos++, "%" + dto.getNome() + "%");
				}

				if (dto.getEmail() != null) {
					pstmt.setString(pos++, "%" + dto.getEmail() + "%");
				}

			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ClienteDTO cliente = new ClienteDTO();

				cliente.setCodigo(String.valueOf(rs.getInt("co_cliente")));
				cliente.setNome(rs.getString("no_cliente"));
				cliente.setEmail(rs.getString("de_email"));
				cliente.setDtNascimento(new Date(rs.getLong("dt_nascimento")));
				cliente.setTelefone(rs.getString("de_telefone"));

				result.add(cliente);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return result;

	}

	public List<ClienteDTO> listar() throws Exception {

		return pesquisar(null);

	}

	public void salvar(ClienteDTO dto) throws Exception {

		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionFactory.getConnection();

			sb.append("insert into tb005_clientes ( ");
			sb.append("    no_cliente    ,          ");
			sb.append("    de_email,                ");
			sb.append("    dt_nascimento,           ");
			sb.append("    de_telefone)             ");
			sb.append("values(                      ");
			sb.append("    ?,                       ");
			sb.append("    ?,                       ");
			sb.append("    ?,                       ");
			sb.append("    ?)                       ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getNome());
			pstmt.setString(2, dto.getEmail());
			pstmt.setLong(3, dto.getDtNascimento().getTime());
			pstmt.setString(4, dto.getTelefone());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, conn);
		}
	}

	public void atualizar(ClienteDTO dto) throws Exception {

		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionFactory.getConnection();

			sb.append(" UPDATE tb005_clientes ");
			sb.append("    set no_cliente=?,de_email=?,dt_nascimento=?,de_telefone=?");
			sb.append("  WHERE co_cliente = ?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getNome());
			pstmt.setString(2, dto.getEmail());
			pstmt.setLong(3, dto.getDtNascimento().getTime());
			pstmt.setString(4, dto.getTelefone());
			pstmt.setInt(5, Integer.valueOf(dto.getCodigo()));

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, conn);
		}
	}

	public void excluir(ClienteDTO dto) throws Exception {

		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionFactory.getConnection();

			sb.append(" delete from tb005_clientes");
			sb.append("  WHERE co_cliente = ?");

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

}
