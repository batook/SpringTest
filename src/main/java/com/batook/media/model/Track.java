package com.batook.media.model;

import java.util.Objects;

public class Track {
    private String number;
    private String name;
    private String path;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track that = (Track) o;
        return Objects.equals(this.number, that.number) && this.name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return number.hashCode() ^ name.hashCode();
    }

    @Override
    public String toString() {
        return "Track=" + number + "_" + name;

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
