package br.com.sisfashion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.sisfashion.dto.AtendimentoDTO;
import br.com.sisfashion.dto.FuncaoDTO;
import br.com.sisfashion.dto.PerfilDTO;
import br.com.sisfashion.dto.UsuarioDTO;
import br.com.sisfashion.utils.DatabaseUtils;

public class UsuariosDAO {

	public UsuarioDTO pesquisarUsuarioPorEmail(UsuarioDTO dto) throws Exception {

		UsuarioDTO usuario = new UsuarioDTO();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * from tb002_usuario ");
			sb.append(" where upper(de_email) = upper(?)");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, dto.getEmail());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				usuario = new UsuarioDTO();

				usuario.setCodigo(String.valueOf(rs.getInt("co_usuario")));
				usuario.setEmail(rs.getString("de_email"));
				usuario.setSenha(rs.getString("de_senha"));
				usuario.setNome(rs.getString("no_usuario"));
				usuario.setTelefone(rs.getString("de_telefone"));

				usuario.setFuncao(getFuncoesDAO().pesquisarFuncao(new FuncaoDTO(rs.getString("co_funcao"))));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return usuario;

	}

	public UsuarioDTO pesquisarUsuario(UsuarioDTO dto) throws Exception {

		UsuarioDTO usuario = new UsuarioDTO();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select tb002.*, tb004.co_grupo "); 
			sb.append("  from tb002_usuario tb002, "); 
			sb.append("       tb004_usuario_grupo tb004 "); 
			sb.append(" where tb002.co_usuario = tb004.co_usuario ");
			sb.append("   and tb002.co_usuario = ?");
			sb.append(" order by upper(tb002.no_usuario)");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				usuario = new UsuarioDTO();

				usuario.setCodigo(String.valueOf(rs.getInt("co_usuario")));
				usuario.setEmail(rs.getString("de_email"));
				usuario.setSenha(rs.getString("de_senha"));
				usuario.setNome(rs.getString("no_usuario"));
				usuario.setFuncao(getFuncoesDAO().pesquisarFuncao(new FuncaoDTO(rs.getString("co_funcao"))));
				usuario.setTelefone(rs.getString("de_telefone"));
				usuario.setPerfil(getPerfisDAO().pesquisarPerfil(new PerfilDTO(rs.getString("co_grupo"))));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return usuario;

	}

	public List<UsuarioDTO> pesquisarPorAtendimento(AtendimentoDTO dto) throws Exception {

		List<UsuarioDTO> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select tb002.* ");
			sb.append("  from tb008_atendimentos_usuarios tb008, ");
			sb.append("       tb002_usuario               tb002 ");
			sb.append(" where tb008.co_usuario = tb002.co_usuario ");
			sb.append("   and tb008.co_atendimento = ? ");
			sb.append("order by upper(tb002.no_usuario)");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			rs = pstmt.executeQuery();

			while (rs.next()) {
				UsuarioDTO usuario = new UsuarioDTO();

				usuario.setCodigo(String.valueOf(rs.getInt("co_usuario")));
				usuario.setEmail(rs.getString("de_email"));
				usuario.setSenha(rs.getString("de_senha"));
				usuario.setNome(rs.getString("no_usuario"));
				usuario.setFuncao(getFuncoesDAO().pesquisarFuncao(new FuncaoDTO(rs.getString("co_funcao"))));
				usuario.setTelefone(rs.getString("de_telefone"));

				result.add(usuario);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return result;

	}

	public List<UsuarioDTO> pesquisar(UsuarioDTO dto) throws Exception {

		List<UsuarioDTO> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * from tb002_usuario where co_usuario != 0 ");

			if (dto != null) {

				if (dto.getNome() != null) {
					sb.append("and upper(no_usuario) like ? ");
				}

				if (dto.getEmail() != null) {
					sb.append("and upper(de_email) like ? ");
				}

			}

			sb.append("order by upper(no_usuario)");

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
				UsuarioDTO usuario = new UsuarioDTO();

				usuario.setCodigo(String.valueOf(rs.getInt("co_usuario")));
				usuario.setEmail(rs.getString("de_email"));
				usuario.setSenha(rs.getString("de_senha"));
				usuario.setNome(rs.getString("no_usuario"));
				usuario.setFuncao(getFuncoesDAO().pesquisarFuncao(new FuncaoDTO(rs.getString("co_funcao"))));
				usuario.setTelefone(rs.getString("de_telefone"));

				result.add(usuario);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return result;

	}

	public List<UsuarioDTO> listar() throws Exception {

		return pesquisar(null);

	}

	public void salvar(UsuarioDTO dto) throws Exception {

		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionFactory.getConnection();

			sb.append("insert into tb002_usuario ( ");
			sb.append("    de_email    ,           ");
			sb.append("    de_senha    ,           ");
			sb.append("    no_usuario  ,           ");
			sb.append("    co_funcao   ,           ");
			sb.append("    de_telefone)            ");
			sb.append("values(                     ");
			sb.append("    ?,                      ");
			sb.append("    ?,                      ");
			sb.append("    ?,                      ");
			sb.append("    ?,                      ");
			sb.append("    ?)                      ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getSenha());
			pstmt.setString(3, dto.getNome());
			pstmt.setString(4, dto.getFuncao().getCodigo());
			pstmt.setString(5, dto.getTelefone());

			pstmt.executeUpdate();

			// recupera o codigo do usuario recem criado
			dto.setCodigo(String.valueOf(DatabaseUtils.getHighestID(conn)));

			sb = new StringBuilder();

			sb.append("insert into tb004_usuario_grupo ( ");
			sb.append("    co_grupo    ,           ");
			sb.append("    co_usuario)             ");
			sb.append("values(                     ");
			sb.append("    ?,                      ");
			sb.append("    ?)                      ");

			pstmt.close();

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getPerfil().getCodigo());
			pstmt.setInt(2, Integer.valueOf(dto.getCodigo()));

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, conn);
		}
	}

	public void atualizar(UsuarioDTO dto) throws Exception {

		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionFactory.getConnection();

			sb.append(" UPDATE tb002_usuario SET de_email=?,de_senha=?,no_usuario=?,co_funcao=?,de_telefone=?");
			sb.append(" WHERE co_usuario = ?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getEmail());
			pstmt.setString(2, dto.getSenha());
			pstmt.setString(3, dto.getNome());
			pstmt.setString(4, dto.getFuncao().getCodigo());
			pstmt.setString(5, dto.getTelefone());
			pstmt.setInt(6, Integer.valueOf(dto.getCodigo()));

			pstmt.executeUpdate();
			
			pstmt.close();
			
			sb.setLength(0);

			sb.append(" UPDATE tb004_usuario_grupo SET co_grupo=?");
			sb.append(" WHERE co_usuario = ?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, dto.getPerfil().getCodigo());
			pstmt.setInt(2, Integer.valueOf(dto.getCodigo()));

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, conn);
		}
	}

	public void excluir(UsuarioDTO dto) throws Exception {

		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ConnectionFactory.getConnection();

			sb.append(" delete from tb004_usuario_grupo");
			sb.append(" WHERE co_usuario = ?");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			pstmt.executeUpdate();

			pstmt.close();

			sb.setLength(0);
			sb.append(" delete from tb002_usuario");
			sb.append(" WHERE co_usuario = ?");

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

	private FuncoesDAO funcoesDAO;

	private FuncoesDAO getFuncoesDAO() {
		if (this.funcoesDAO == null) {
			this.funcoesDAO = new FuncoesDAO();
		}
		return this.funcoesDAO;
	}

	private PerfisDAO perfisDAO;

	private PerfisDAO getPerfisDAO() {
		if (this.perfisDAO == null) {
			this.perfisDAO = new PerfisDAO();
		}
		return this.perfisDAO;
	}

}
