package com.batook.media.model;

import java.util.List;

public class Disk {
    private List<Track> tracks;
    private String number;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disk that = (Disk) o;
        return this.number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return "Disk=" + number;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
