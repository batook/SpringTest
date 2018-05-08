package com.batook.media.model;

import java.util.Objects;

public class GoodsDetail {
    private String id;
    private String disk;
    private String number;
    private String name;
    private String path;

    GoodsDetail(String id, String disk, String number) {
        this.id = id;
        this.disk = disk;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getDisk() {
        return disk;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsDetail that = (GoodsDetail) o;
        return Objects.equals(this.id, that.id) && this.disk.equals(that.disk) && this.number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return id.hashCode() ^ disk.hashCode() ^ number.hashCode();
    }

    @Override
    public String toString() {
        return id + "_" + disk + "_" + number;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
