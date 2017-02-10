package br.com.sisfashion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.sisfashion.dto.PerfilDTO;
import br.com.sisfashion.utils.DatabaseUtils;

public class PerfisDAO {

	public PerfilDTO pesquisarPerfil(PerfilDTO dto) throws Exception {

		PerfilDTO perfil = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * ");
			sb.append("  from tb003_grupo ");
			sb.append(" where co_grupo = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				perfil = new PerfilDTO();

				perfil.setCodigo(String.valueOf(rs.getInt("co_grupo")));
				perfil.setNome(rs.getString("no_grupo"));
				perfil.setDescricao(rs.getString("de_grupo"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return perfil;

	}

	public List<PerfilDTO> pesquisar(PerfilDTO dto) throws Exception {

		List<PerfilDTO> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * from tb003_grupo ");

			if (dto != null) {

				if (dto.getNome() != null) {
					sb.append("and upper(no_grupo) like ? ");
				}

			}

			sb.append("order by upper(no_grupo)");

			pstmt = conn.prepareStatement(sb.toString());

			if (dto != null) {

				int pos = 1;

				if (dto.getNome() != null) {
					pstmt.setString(pos++, "%" + dto.getNome() + "%");
				}

			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				PerfilDTO perfil = new PerfilDTO();

				perfil.setCodigo(String.valueOf(rs.getInt("co_grupo")));
				perfil.setNome(rs.getString("no_grupo"));
				perfil.setDescricao(rs.getString("de_grupo"));

				result.add(perfil);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return result;

	}

	public List<PerfilDTO> listar() throws Exception {

		return pesquisar(null);

	}
}
