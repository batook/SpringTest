package com.batook.media.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Barcode {
    @Id
    private String barcode;

    public Barcode() {
    }

    public Barcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barcode that = (Barcode) o;
        return Objects.equals(this.barcode, that.barcode);
    }

    @Override
    public int hashCode() {
        return barcode.hashCode();
    }

    @Override
    public String toString() {
        return "Barcode=" + barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
