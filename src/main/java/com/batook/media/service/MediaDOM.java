package com.batook.media.service;

import com.batook.media.model.Barcode;
import com.batook.media.model.Item;
import com.batook.media.model.Track;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MediaDOM {
    public void createXML(List<Item> itemList, String xmlFile) {
        Document doc = getDocument();
        Element root = doc.createElement("REPORT");
        root.setAttribute("MediaStation", LocalDate.now()
                                                   .format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        doc.appendChild(root);
        Element items = doc.createElement("ITEMS");
        root.appendChild(items);
        for (Item i : itemList) {
            Element item = doc.createElement("ITEM");
            item.setAttribute("ID", i.getId());
            items.appendChild(item);
            Element barcodes = doc.createElement("BARCODES");
            item.appendChild(barcodes);
            for (Barcode b : i.getBarcodes()) {
                Element barcode = doc.createElement("BARCODE");
                barcode.setTextContent(b.getBarcode());
                barcodes.appendChild(barcode);
            }
            Element title = doc.createElement("TITLE");
            title.setTextContent(i.getTitle());
            item.appendChild(title);
            Element cover_path = doc.createElement("COVER_PATH");
            cover_path.setTextContent(i.getCoverPath());
            item.appendChild(cover_path);
            Element video_path = doc.createElement("VIDEO_PATH");
            video_path.setTextContent(i.getVideoPath());
            item.appendChild(video_path);
            Element description = doc.createElement("DESCRIPTION");
            description.setTextContent(i.getDescription());
            item.appendChild(description);
            Element type = doc.createElement("TYPE");
            type.setTextContent(i.getType());
            item.appendChild(type);
            Element genre = doc.createElement("GENRE");
            genre.setTextContent(i.getGenre());
            item.appendChild(genre);
            Element hit = doc.createElement("IS_HIT");
            hit.setTextContent(i.getHit());
            item.appendChild(hit);
            if (i.getDisk() != null) {
                Element disk = doc.createElement("DISK");
                disk.setAttribute("NUMBER", i.getDisk()
                                             .getNumber());
                item.appendChild(disk);
                for (Track t : i.getDisk()
                                .getTracks()) {
                    Element track = doc.createElement("TRACK");
                    track.setAttribute("NUMBER", t.getNumber());
                    disk.appendChild(track);
                    Element name = doc.createElement("NAME");
                    name.setTextContent(t.getName());
                    track.appendChild(name);
                    Element path = doc.createElement("PATH");
                    path.setTextContent(t.getPath());
                    track.appendChild(path);
                }
            }
        }
        writeDocument(doc, xmlFile);
    }

    Document getDocument() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
                                                                    .newDocumentBuilder();
            Document doc = documentBuilder.newDocument();
            System.out.printf("Version = %s%n", doc.getXmlVersion());
            System.out.printf("Encoding = %s%n", doc.getXmlEncoding());
            System.out.printf("Standalone = %b%n%n", doc.getXmlStandalone());
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    void writeDocument(Document doc, String xmlFile) {
        try {
            Transformer tr = TransformerFactory.newInstance()
                                               .newTransformer();
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(xmlFile));
            tr.transform(source, result);
        } catch (FileNotFoundException | TransformerException e) {
            e.printStackTrace();
        }
    }
}

