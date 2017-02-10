package org.avalon.hunterz.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.avalon.hunterz.SeriesDTO;
import org.avalon.hunterz.dao.TorrentsDAO;
import org.avalon.hunterz.model.TorrentInfo;
import org.avalon.hunterz.utils.DbUtils;

public class TorrentsDAOImpl extends BaseDAO implements TorrentsDAO {

    @Override
    public void salvar(SeriesDTO dto) throws Exception {

        StringBuilder sb = new StringBuilder();

        sb.append("insert into hunterz.tb02_torrents( ");
        sb.append("    id_torrent, ");
        sb.append("    co_search_engine, ");
        sb.append("    id_serie, ");
        sb.append("    de_title, ");
        sb.append("    de_magnet_link, ");
        sb.append("    nu_size, ");
        sb.append("    dt_released, ");
        sb.append("    qt_seeds, ");
        sb.append("    qt_leechers) ");
        sb.append("values ( ");
        sb.append("    nextval('hunterz.sq02_torrents'),?,?,?,?,?,?,?,? ");
        sb.append("    ) ");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());

            int count = 0;

            for (TorrentInfo torrent : dto.listarTorrents()) {

                // se nao existe, inclui
                if (torrent.getMagnetLink() != null && findByUrl(torrent.getMagnetLink()) == null) {

                    int pos = 1;

                    pstmt.setString(pos++, torrent.getSearchEngine());
                    pstmt.setInt(pos++, dto.getSerie().getCodigo());
                    pstmt.setString(pos++, torrent.getTitle());
                    pstmt.setString(pos++, torrent.getMagnetLink());
                    pstmt.setString(pos++, torrent.getSize());
                    pstmt.setString(pos++, torrent.getReleased());
                    pstmt.setString(pos++, torrent.getSeeds());
                    pstmt.setString(pos++, torrent.getLeechers());

                    count++;

                    pstmt.addBatch();
                    
                    if (count % 10 == 0){
                        pstmt.executeBatch();
                    }
                }

            }

            pstmt.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(null, null, conn);
        }

    }

    @Override
    public TorrentInfo findByUrl(String magnetLink) throws Exception {

        TorrentInfo result = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        StringBuilder sb = new StringBuilder();

        sb.append("select id_torrent, id_serie, de_title, de_magnet_link, nu_size, ");
        sb.append("       dt_released, qt_seeds, qt_leechers, co_search_engine ");
        sb.append("  from hunterz.tb02_torrents ");
        sb.append(" where de_magnet_link = ? ");

        try {
            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());

            pstmt.setString(1, magnetLink);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(null, null, conn);
        }

        return result;
    }

    private TorrentInfo map(ResultSet rs) throws SQLException {

        TorrentInfo result = new TorrentInfo(rs.getString("co_search_engine"));

        result.setCodigo(rs.getInt("id_torrent"));
        result.setCodigo(rs.getInt("id_serie"));
        result.setTitle(rs.getString("de_title"));
        result.setMagnetLink(rs.getString("de_magnet_link"));
        result.setSize(rs.getString("nu_size"));
        result.setReleased(rs.getString("dt_released"));
        result.setSeeds(rs.getString("qt_seeds"));
        result.setLeechers(rs.getString("qt_leechers"));

        return result;
    }
}
