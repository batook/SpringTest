package com.batook.media.data;

import com.batook.media.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
    public Item findById(String itemId);

    public List<Item> findAll();

}
