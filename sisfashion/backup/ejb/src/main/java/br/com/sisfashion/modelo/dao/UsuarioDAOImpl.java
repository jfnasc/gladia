/**
 * 
 */
package br.com.sisfashion.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import br.com.sisfashion.modelo.dto.UsuarioDTO;
import br.com.sisfashion.utils.DatabaseUtils;

/**
 * @author josen
 *
 */
@Stateless
public class UsuarioDAOImpl implements UsuarioDAO {

	@Resource(mappedName = "java:jboss/datasources/SisfashionDS")
	private DataSource dataSource;

	@Override
	public void salvarUsuario(UsuarioDTO usuario) throws Exception {
		StringBuilder sb = new StringBuilder();

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();

			sb.append("insert into tb002_usuario ( ");
			sb.append("    de_email    ,           ");
			sb.append("    de_senha    ,           ");
			sb.append("    no_usuario  ,           ");
			sb.append("    de_funcao   ,           ");
			sb.append("    de_telefone)            ");
			sb.append("values(                     ");
			sb.append("    ?,                      ");
			sb.append("    ?,                      ");
			sb.append("    ?,                      ");
			sb.append("    ?,                      ");
			sb.append("    ?)                      ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, usuario.getDeEmail());
			pstmt.setString(2, usuario.getDeSenha());
			pstmt.setString(3, usuario.getNoUsuario());
			pstmt.setString(4, usuario.getDeFuncao());
			pstmt.setString(5, usuario.getDeTelefone());

			pstmt.executeUpdate();

			// recupera o codigo do usuario recem criado
			usuario.setNuUsuario(DatabaseUtils.getHighestID(conn));

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, conn);
		}
	}

	@Override
	public Collection<UsuarioDTO> pesquisarUsuarios(UsuarioDTO param) throws Exception {
		List<UsuarioDTO> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dataSource.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * from tb002_usuario where co_usuario != 0 ");

			if (param != null) {

				if (param.getNoUsuario() != null) {
					sb.append("and upper(no_usuario) like ? ");
				}

				if (param.getDeEmail() != null) {
					sb.append("and upper(de_email) like ? ");
				}

			}

			sb.append("order by upper(no_usuario)");

			pstmt = conn.prepareStatement(sb.toString());

			if (param != null) {

				int pos = 1;

				if (param.getNoUsuario() != null) {
					pstmt.setString(pos++, "%" + param.getNoUsuario() + "%");
				}

				if (param.getDeEmail() != null) {
					pstmt.setString(pos++, "%" + param.getDeEmail() + "%");
				}

			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				UsuarioDTO usuario = new UsuarioDTO();

				usuario.setNuUsuario(rs.getInt("co_usuario"));
				usuario.setDeEmail(rs.getString("de_email"));
				usuario.setDeSenha(rs.getString("de_senha"));
				usuario.setNoUsuario(rs.getString("no_usuario"));
				usuario.setDeFuncao(rs.getString("de_funcao"));
				usuario.setDeTelefone(rs.getString("de_telefone"));

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

	@Override
	public Collection<UsuarioDTO> pesquisarUsuarios() throws Exception {
		return pesquisarUsuarios(null);
	}

}
