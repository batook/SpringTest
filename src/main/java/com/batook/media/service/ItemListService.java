package com.batook.media.service;

import com.batook.media.data.JdbcRepository;
import com.batook.media.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemListService {
    @Autowired
    JdbcRepository repository;

    @Transactional
    public List<Item> getItemList() {
        List<Item> items = new ArrayList<>();
        //SqlRowSet rs = repository.getGoodsResultSet();
        List<Goods> goodsList=repository.getGoodsList();
        //while (rs.next()) {
        for (Goods goods: goodsList){
            Item item = new Item();
            String itemid = goods.getId();
            item.setId(itemid);
            item.setTitle(goods.getTitle());
            item.setCoverPath(goods.getCoverPath());
            item.setDescription(goods.getDescription());
            item.setVideoPath(goods.getVideoPath());
            item.setType(goods.getType());
            item.setGenre(goods.getGenre());
            item.setHit(goods.getHit());
            for (String diskNumber : repository.getDiskListByItemId(itemid)) {
                Disk disk = new Disk();
                List<Track> tracks = new ArrayList<>();
                disk.setNumber(diskNumber);
                for (Track trk : repository.getTrackListByItemIdAndDiskNumber(itemid, diskNumber)) {
                    Track track = new Track();
                    track.setNumber(trk.getNumber());
                    track.setName(trk.getName());
                    track.setPath(trk.getPath());
                    tracks.add(track);
                }
                disk.setTracks(tracks);
                item.setDisk(disk);
            }
            List<Barcode> barcodes = new ArrayList<>();
            for (Barcode bc : repository.getBarcodeListByItemId(itemid)) {
                Barcode barcode = new Barcode();
                barcode.setBarcode(bc.getBarcode());
                barcodes.add(barcode);
            }
            item.setBarcodes(barcodes);
            items.add(item);
        }
        return items;
    }
}
