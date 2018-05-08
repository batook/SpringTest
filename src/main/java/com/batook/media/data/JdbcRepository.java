package com.batook.media.data;

import com.batook.media.model.Barcode;
import com.batook.media.model.Goods;
import com.batook.media.model.Track;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
import java.util.Map;

public interface JdbcRepository {
    public SqlRowSet getGoodsResultSet();

    public List<Goods> getGoodsList();

    public List<Map<String, Object>> getList();

    public List<Barcode> getBarcodeListByItemId(String itemId);

    public List<String> getDiskListByItemId(String itemId);

    public List<Track> getTrackListByItemIdAndDiskNumber(String itemId, String diskNumber);
}
