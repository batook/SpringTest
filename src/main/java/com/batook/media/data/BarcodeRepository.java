package com.batook.media.data;

import com.batook.media.model.Barcode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BarcodeRepository extends MongoRepository<Barcode, String> {
    public Barcode findByBarcode(String barcode);

    public List<Barcode> findAll();
}
