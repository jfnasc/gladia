package org.avalon.hunterz.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.avalon.hunterz.SeriesDTO;
import org.avalon.hunterz.dao.TorrentsDAO;
import org.avalon.hunterz.model.Serie;
import org.avalon.hunterz.model.TorrentInfo;
import org.avalon.hunterz.utils.DbUtils;

public class TorrentsDAOImpl extends BaseDAO implements TorrentsDAO {

    @Override
    public void salvar(SeriesDTO dto) throws Exception {

        StringBuilder sb = new StringBuilder();

        sb.append("insert into hunterz.tb02_torrents( ");
        sb.append("    id_torrent, ");
        sb.append("    id_serie, ");
        sb.append("    de_title, ");
        sb.append("    de_link, ");
        sb.append("    nu_size, ");
        sb.append("    dt_released, ");
        sb.append("    qt_seeds, ");
        sb.append("    qt_leechers) ");
        sb.append("values ( ");
        sb.append("    nextval('hunterz.sq02_torrents'),?,?,?,?,?,?,? ");
        sb.append("    ) ");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getDataSource().getConnection();

            pstmt = conn.prepareStatement(sb.toString());

            for (TorrentInfo torrent : dto.listarTorrents()) {

                // se nao existe, inclui
                if (torrent.getMagnetLink() != null && findByUrl(torrent.getMagnetLink()) == null) {

                    pstmt.setInt(1, dto.getSerie().getCodigo());
                    pstmt.setString(2, torrent.getTitle());
                    pstmt.setString(3, torrent.getMagnetLink());
                    pstmt.setString(4, torrent.getSize());
                    pstmt.setString(5, torrent.getReleased());
                    pstmt.setString(6, torrent.getSeeds());
                    pstmt.setString(7, torrent.getLeechers());

                    pstmt.addBatch();
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

        sb.append("select id_torrent, id_serie, de_title, de_link, nu_size, ");
        sb.append("       dt_released, qt_seeds, qt_leechers ");
        sb.append("  from hunterz.tb02_torrents ");
        sb.append(" where de_link = ? ");

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

        TorrentInfo result = new TorrentInfo();

        result.setCodigo(rs.getInt("id_torrent"));
        result.setCodigo(rs.getInt("id_serie"));
        result.setTitle(rs.getString("de_title"));
        result.setMagnetLink(rs.getString("de_link"));
        result.setSize(rs.getString("nu_size"));
        result.setReleased(rs.getString("dt_released"));
        result.setSeeds(rs.getString("qt_seeds"));
        result.setLeechers(rs.getString("qt_leechers"));

        return result;
    }
}
