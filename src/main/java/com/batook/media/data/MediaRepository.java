package com.batook.media.data;

import com.batook.media.model.Barcode;
import com.batook.media.model.Goods;
import com.batook.media.model.Track;

import java.util.List;
import java.util.Map;

public interface MediaRepository {
    public List<Goods> getGoodsList();

    public List<Map<String, Object>> getList();

    public List<Barcode> getBarcodeListByItemId(String itemId);

    public List<String> getDiskListByItemId(String itemId);

    public List<Track> getTrackListByItemIdAndDiskNumber(String itemId, String diskNumber);
}
