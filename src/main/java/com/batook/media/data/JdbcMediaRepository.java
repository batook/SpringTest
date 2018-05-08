package com.batook.media.data;

import com.batook.media.model.Barcode;
import com.batook.media.model.Goods;
import com.batook.media.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    public JdbcMediaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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

/*
    public List<Item> getItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select ITEMID,TITLE,COVER_PATH,DESCRIPTION,VIDEO_PATH,MEDIA_TYPE,GENRE,IS_HIT from GOODS");
        PreparedStatement ps1 = conn.prepareStatement("select distinct DISK from GOODS_DETAIL where ITEMID=?");
        PreparedStatement ps2 = conn.prepareStatement("select TRACK,NAME,PATH from GOODS_DETAIL where ITEMID=? and DISK=?");
        PreparedStatement ps3 = conn.prepareStatement("select BARCODE from GOODS_BARCODES where ITEMID=?");
        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getString(1));
            item.setTitle(rs.getString(2));
            item.setCoverPath(rs.getString(3));
            item.setDescription(rs.getString(4));
            item.setVideoPath(rs.getString(5));
            item.setType(rs.getString(6));
            item.setGenre(rs.getString(7));
            item.setHit(rs.getString(8));
            ps1.setString(1, rs.getString(1));
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                Disk disk = new Disk();
                List<Track> tracks = new ArrayList<>();
                disk.setNumber(rs1.getString(1));
                ps2.setString(1, rs.getString(1));
                ps2.setString(2, rs1.getString(1));
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    Track track = new Track();
                    track.setNumber(rs2.getString(1));
                    track.setName(rs2.getString(2));
                    track.setPath(rs2.getString(3));
                    tracks.add(track);
                }
                disk.setTracks(tracks);
                item.setDisk(disk);
            }
            List<Barcode> barcodes = new ArrayList<>();
            ps3.setString(1, rs.getString(1));
            ResultSet rs3 = ps3.executeQuery();
            while (rs3.next()) {
                Barcode barcode = new Barcode();
                barcode.setBarcode(rs3.getString(1));
                barcodes.add(barcode);
            }
            item.setBarcodes(barcodes);
            items.add(item);
        }
        ps3.close();
        ps2.close();
        ps1.close();
        st.close();
        return items;
    }
    */
}


