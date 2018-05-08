package com.batook.media.service;

import com.batook.media.data.MediaRepository;
import com.batook.media.model.Barcode;
import com.batook.media.model.Disk;
import com.batook.media.model.Item;
import com.batook.media.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemListService {
    @Autowired
    MediaRepository repository;

    public ItemListService() {
    }

    public ItemListService(MediaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public List<Item> getItemList() {
        List<Item> items = new ArrayList<>();
        SqlRowSet rs = repository.getGoodsResultSet();
        while (rs.next()) {
            Item item = new Item();
            String itemid = rs.getString(1);
            item.setId(itemid);
            item.setTitle(rs.getString(2));
            item.setCoverPath(rs.getString(3));
            item.setDescription(rs.getString(4));
            item.setVideoPath(rs.getString(5));
            item.setType(rs.getString(6));
            item.setGenre(rs.getString(7));
            item.setHit(rs.getString(8));
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
