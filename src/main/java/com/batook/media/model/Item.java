package com.batook.media.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Document
public class Item implements Comparable<Item> {
    @Id
    private String id;
    private List<Barcode> barcodes;
    private Disk disk;
    private String title;
    private String coverPath;
    private String videoPath;
    private String description;
    private String type;
    private String genre;
    private String isHit;

    public Item() {
    }

    public Item(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item that = (Item) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id;
    }

    public List<Barcode> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(List<Barcode> barcodes) {
        this.barcodes = barcodes;
    }

    public Disk getDisk() {
        return disk;
    }

    public void setDisk(Disk disk) {
        this.disk = disk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getHit() {
        return isHit;
    }

    public void setHit(String hit) {
        isHit = hit;
    }

    @Override
    public int compareTo(Item o) {
        return id.compareTo(o.getId());
    }
}
