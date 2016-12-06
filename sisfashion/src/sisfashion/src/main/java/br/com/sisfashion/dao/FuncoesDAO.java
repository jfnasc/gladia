package br.com.sisfashion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.sisfashion.dto.FuncaoDTO;
import br.com.sisfashion.utils.DatabaseUtils;

public class FuncoesDAO {

	public FuncaoDTO pesquisarFuncao(FuncaoDTO dto) throws Exception {

		FuncaoDTO servico = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * ");
			sb.append("  from tb009_funcoes ");
			sb.append(" where co_funcao = ?");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, Integer.valueOf(dto.getCodigo()));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				servico = new FuncaoDTO();

				servico.setCodigo(String.valueOf(rs.getInt("co_funcao")));
				servico.setNome(rs.getString("no_funcao"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			DatabaseUtils.close(pstmt, rs, conn);
		}

		return servico;

	}

	public List<FuncaoDTO> pesquisar(FuncaoDTO dto) throws Exception {

		List<FuncaoDTO> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ConnectionFactory.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select * from tb009_funcoes ");

			if (dto != null) {

				if (dto.getNome() != null) {
					sb.append("and upper(no_funcao) like ? ");
				}

			}

			sb.append("order by upper(no_funcao)");

			pstmt = conn.prepareStatement(sb.toString());

			if (dto != null) {

				int pos = 1;

				if (dto.getNome() != null) {
					pstmt.setString(pos++, "%" + dto.getNome() + "%");
				}

			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				FuncaoDTO servico = new FuncaoDTO();

				servico.setCodigo(String.valueOf(rs.getInt("co_funcao")));
				servico.setNome(rs.getString("no_funcao"));

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

	public List<FuncaoDTO> listar() throws Exception {

		return pesquisar(null);

	}
}
