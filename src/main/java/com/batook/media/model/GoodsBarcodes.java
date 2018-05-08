package com.batook.media.model;

import java.util.Objects;

public class GoodsBarcodes {
    private String id;
    private String barcode;

    public GoodsBarcodes(String id, String barcode) {
        this.id = id;
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsBarcodes that = (GoodsBarcodes) o;
        return Objects.equals(this.id, that.id) && this.barcode.equals(that.barcode);
    }

    @Override
    public int hashCode() {
        return id.hashCode() ^ barcode.hashCode();
    }

    @Override
    public String toString() {
        return id + "_" + barcode;

    }
}
