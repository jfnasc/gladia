/**
 * 
 */
package org.avalon.hunterz.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.avalon.hunterz.dao.SeriesDAO;
import org.avalon.hunterz.model.Serie;

/**
 * @author josen
 * 
 */
public class SeriesDAOImpl extends BaseDAO implements SeriesDAO {

    /*
     * (non-Javadoc)
     * 
     * @see org.avalon.hunterz.dao.SeriesDAO#listar()
     */
    @Override
    public List<Serie> listar() throws Exception {

        List<Serie> result = new ArrayList<Serie>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        StringBuilder sb = new StringBuilder();

        sb.append("select id_serie, no_serie, co_serie ");
        sb.append("  from hunterz.tb01_series ");
        sb.append(" order by upper(no_serie) ");

        try {
            conn = getDataSource().getConnection();

            stmt = conn.createStatement();

            rs = stmt.executeQuery(sb.toString());

            while (rs.next()) {
                result.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private Serie map(ResultSet rs) throws SQLException {
        Serie serie = new Serie();

        serie.setCodigo(rs.getInt("id_serie"));
        serie.setNome(rs.getString("no_serie"));
        serie.setCodigoBusca(rs.getString("co_serie"));
        
        return serie;
    }
}
