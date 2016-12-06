package br.com.sisfashion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.sisfashion.dto.ServicoDTO;
import br.com.sisfashion.utils.DatabaseUtils;

public class ServicosDAO {

	public ServicoDTO pesquisarServico(ServicoDTO dto) throws Exception {

		ServicoDTO servico = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * ");
			sb.append("  from tb006_servicos ");
			sb.append(" where co_servico = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				servico = new ServicoDTO();

				servico.setCodigo(String.valueOf(rs.getInt("co_servico")));
				servico.setNome(rs.getString("no_servico"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return servico;

	}

	public List<ServicoDTO> pesquisar(ServicoDTO dto) throws Exception {

		List<ServicoDTO> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * from tb006_servicos ");

			if (dto != null) {

				if (dto.getNome() != null) {
					sb.append("and upper(no_servico) like ? ");
				}

			}

			sb.append("order by upper(no_servico)");

			pstmt = conn.prepareStatement(sb.toString());

			if (dto != null) {

				int pos = 1;

				if (dto.getNome() != null) {
					pstmt.setString(pos++, "%" + dto.getNome() + "%");
				}

			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ServicoDTO servico = new ServicoDTO();

				servico.setCodigo(String.valueOf(rs.getInt("co_servico")));
				servico.setNome(rs.getString("no_servico"));

				result.add(servico);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return result;

	}

	public List<ServicoDTO> listar() throws Exception {

		return pesquisar(null);

	}
}
