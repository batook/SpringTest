package com.batook.media;

import com.batook.media.data.*;
import com.batook.media.model.Barcode;
import com.batook.media.model.Goods;
import com.batook.media.model.Item;
import com.batook.media.model.Track;
import com.batook.media.service.ItemListService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.mongodb.util.JSON;
import org.bson.BsonBinaryWriter;
import org.bson.Document;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;
import org.bson.io.BasicOutputBuffer;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;
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
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

    private static byte[] mongoDocumentToByteArray(Document mongoDocument) {
        BasicOutputBuffer outputBuffer = new BasicOutputBuffer();
        BsonBinaryWriter writer = new BsonBinaryWriter(outputBuffer);
        new DocumentCodec().encode(writer, mongoDocument, EncoderContext.builder()
                                                                        .isEncodingCollectibleDocument(true)
                                                                        .build());
        return outputBuffer.toByteArray();
    }

    private static void printJson(Document doc) {
        JsonWriter writer = new JsonWriter(new StringWriter(), new JsonWriterSettings(JsonMode.SHELL, false));
        new DocumentCodec().encode(writer, doc, EncoderContext.builder()
                                                              .isEncodingCollectibleDocument(true)
                                                              .build());
        System.out.println(writer.getWriter());
        writer.flush();
    }

    @Before
    public void drop() {
        mongoOps = new MongoTemplate(mongo, "MediaDB");
    }

    @Ignore
    @Test
    public void testMongo() {
        assertNotNull(mongo);
        assertNotNull(mongoOps);
        mongoOps.dropCollection("item");
        itemRepository.save(new Item("12345"));
        assertEquals(1, mongoOps.getCollection("item")
                                .count());
        List<DBObject> myList = mongoOps.getCollection("item")
                                        .find()
                                        .toArray();
        System.out.println(myList);
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
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test(timeout = 1000L)
    public void testJson() throws IOException {
        List<DBObject> myList = mongoOps.getCollection("item")
                                        .find()
                                        .toArray();
        //System.out.println(myList);
        //String json = JSON.serialize(myList);
        String json = JSON.serialize(myList.get(1));
        //String json = new Gson().toJson(myList);
        //System.out.println(json);
        MongoCollection<Document> collection = mongo.getDatabase("MediaDB")
                                                    .getCollection("item");
        Bson projection = Projections.excludeId();
        collection.find()
                  .projection(projection)
                  .limit(10)
                  .into(new ArrayList<Document>())
                  .forEach(MediaTest::printJson);

    }

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
