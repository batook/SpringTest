package com.batook.media.data;

import com.batook.media.model.Barcode;
import com.batook.media.model.Goods;
import com.batook.media.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcMediaRepository implements MediaRepository {
    private static final String GOODS_COLUMNS = "ITEMID,TITLE,COVER_PATH,DESCRIPTION,VIDEO_PATH,MEDIA_TYPE,GENRE,IS_HIT";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public SqlRowSet getGoodsResultSet() {
        return this.jdbcTemplate.queryForRowSet("select " + GOODS_COLUMNS + " from GOODS");
    }

    public List<Goods> getGoodsList() {
        return this.jdbcTemplate.query("select " + GOODS_COLUMNS + " from GOODS", new GoodsRowMapper());
    }

    public List<Map<String, Object>> getList() {
        return this.jdbcTemplate.queryForList("select " + GOODS_COLUMNS + " from GOODS");
    }

    public List<Barcode> getBarcodeListByItemId(String itemId) {
        String sql = "select BARCODE from GOODS_BARCODES where ITEMID = :Id";
        Map<String, String> namedParameters = Collections.singletonMap("Id", itemId);
        return this.namedParameterJdbcTemplate.query(sql, namedParameters, new BarcodeRowMapper());
    }

    public List<String> getDiskListByItemId(String itemId) {
        String sql = "select distinct DISK from GOODS_DETAIL where ITEMID = :Id";
        Map<String, String> namedParameters = Collections.singletonMap("Id", itemId);
        return this.namedParameterJdbcTemplate.queryForList(sql, namedParameters, String.class);
    }

    public List<Track> getTrackListByItemIdAndDiskNumber(String itemId, String diskNumber) {
        String sql = "select TRACK,NAME,PATH from GOODS_DETAIL where ITEMID = :Id and DISK = :disk";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("Id", itemId);
        parameters.addValue("disk", diskNumber);
        return this.namedParameterJdbcTemplate.query(sql, parameters, new TrackRowMapper());
    }

    private static final class GoodsRowMapper implements RowMapper<Goods> {
        @Override
        public Goods mapRow(ResultSet rs, int rowNum) throws SQLException {
            String id = rs.getString("itemid");
            String title = rs.getString("title");
            String coverPath = rs.getString("cover_path");
            String videoPath = rs.getString("video_path");
            String description = rs.getString("description");
            String type = rs.getString("media_type");
            String genre = rs.getString("genre");
            String isHit = rs.getString("is_hit");
            return new Goods(id, title, coverPath, description, videoPath, type, genre, isHit);
        }
    }

    private static final class BarcodeRowMapper implements RowMapper<Barcode> {
        @Override
        public Barcode mapRow(ResultSet rs, int rowNum) throws SQLException {
            Barcode barcode = new Barcode();
            barcode.setBarcode(rs.getString("barcode"));
            return barcode;
        }
    }

    private static final class TrackRowMapper implements RowMapper<Track> {
        @Override
        public Track mapRow(ResultSet rs, int rowNum) throws SQLException {
            Track track = new Track();
            track.setNumber(rs.getString("track"));
            track.setName(rs.getString("name"));
            track.setPath(rs.getString("path"));
            return track;
        }
    }
}


