package com.batook.media.model;

public class Goods extends Item {

    public Goods(String itemid) {
        super(itemid);
    }

    public Goods(String itemid, String title, String cover_path, String description, String video_path, String media_type, String genre, String is_hit) {
        this(itemid);
        setTitle(title);
        setCoverPath(cover_path);
        setDescription(description);
        setVideoPath(video_path);
        setType(media_type);
        setGenre(genre);
        setHit(is_hit);
    }


    public static void main(String[] args) {
        Goods g = new Goods("ITEMID", "TITLE", "COVER_PATH", "DESCRIPTION", "VIDEO_PATH", "MEDIA_TYPE", "GENRE", "IS_HIT");
        System.out.println(g);
    }
}
