package com.batook.media;

import com.batook.media.data.*;
import com.batook.media.model.Barcode;
import com.batook.media.model.Goods;
import com.batook.media.model.Item;
import com.batook.media.model.Track;
import com.batook.media.service.ItemListService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OraConfig.class, MongoConfig.class})
public class MediaTest {
    @Autowired
    JdbcRepository repository;

    @Autowired
    ItemListService itemListService;

    MongoOperations mongoOps;

    @Autowired
    MongoClient mongo;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    BarcodeRepository barcodeRepository;


    @Before
    public void drop() {
        mongoOps = new MongoTemplate(mongo, "MediaDB");
    }

    @Test
    public void testMongo() {
        assertNotNull(mongo);
        assertNotNull(mongoOps);
        mongoOps.dropCollection("item");
        itemRepository.save(new Item("12345"));
        assertEquals(1, mongoOps.getCollection("item")
                                .count());
        assertNotNull(itemRepository.findById("12345"));
        long startTime = System.nanoTime();
        List<Item> itemList = itemListService.getItemList();
        double estimatedTime = (double) (System.nanoTime() - startTime) / 1_000_000_000;
        System.out.println("getItemList :" + new DecimalFormat("#.##########").format(estimatedTime));
        startTime = System.nanoTime();
        itemRepository.save(itemList);
        estimatedTime = (double) (System.nanoTime() - startTime) / 1_000_000_000;
        System.out.println("save ItemList :" + new DecimalFormat("#.##########").format(estimatedTime));
        System.out.println(mongoOps.getCollection("item")
                                   .count());
    }

    @Test
    public void testCollection() {
        System.out.println(mongoOps.getCollection("item")
                                   .count());
        assertEquals(3122, itemRepository.findAll()
                                         .size());
        assertNotNull(itemListService);
        long startTime = System.nanoTime();
        List<Goods> goodsList = repository.getGoodsList();
        double estimatedTime = (double) (System.nanoTime() - startTime) / 1_000_000_000;
        System.out.println("getGoodsList :" + new DecimalFormat("#.##########").format(estimatedTime));
        startTime = System.nanoTime();
        List<Item> itemList = itemListService.getItemList();
        estimatedTime = (double) (System.nanoTime() - startTime) / 1_000_000_000;
        System.out.println("getItemList :" + new DecimalFormat("#.##########").format(estimatedTime));
        assertEquals(3121, itemList.size());
        Gson gson = new GsonBuilder().setPrettyPrinting()
                                     .create();
        try (FileWriter writer = new FileWriter("ItemList.json");) {
            gson.toJson(itemList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJson() throws IOException {
        // mongoOps.dropCollection("barcode");
        // List<Barcode> list = repository.getBarcodeList();
        // System.out.println(list.size());
        // barcodeRepository.save(list);
        List<Barcode> list2 = barcodeRepository.findAll();
        System.out.println(list2.size());
        String jsonStr = new Gson().toJson(list2);
        System.out.println(jsonStr);
    }

    @Ignore
    @Test
    @Transactional
    public void testOrcl() {
        assertNotNull(repository);
        assertTrue(repository.getGoodsList()
                             .contains(new Goods("1211093321")));
        assertTrue(repository.getBarcodeListByItemId("1211093321")
                             .contains(new Barcode("4601546042422")));
        System.out.println(repository.getDiskListByItemId("1571091370"));
        assertEquals(12, repository.getTrackListByItemIdAndDiskNumber("1571091370", "5")
                                   .size());
    }

    @Ignore
    @Test
    @Transactional
    public void checkItems() {
        assertNotNull(itemListService);
        List<Item> itemList = itemListService.getItemList();
        itemList.sort((o1, o2) -> {
            int result;
            result = o1.getType()
                       .compareTo(o2.getType());
            if (result != 0) return result;
            result = o1.getTitle()
                       .compareTo(o2.getTitle());
            return result;
        });
        for (Item item : itemList) {
            System.out.println(item);
            System.out.println("\t" + item.getBarcodes());
            System.out.println("\t" + item.getTitle());
            System.out.println("\t" + item.getCoverPath());
            System.out.println("\t" + item.getVideoPath());
            System.out.println("\t" + item.getDescription());
            System.out.println("\t" + item.getType());
            System.out.println("\t" + item.getGenre());
            System.out.println("\t" + item.getHit());
            if (item.getDisk() != null) {
                System.out.println("\t" + item.getDisk());
                for (Track track : item.getDisk()
                                       .getTracks()) {
                    System.out.println("\t\t" + track);
                    System.out.println("\t\t" + track.getName());
                    System.out.println("\t\t" + track.getPath());
                }
            }
        }
        System.out.println(itemList.size() + " itemList");
    }
}
