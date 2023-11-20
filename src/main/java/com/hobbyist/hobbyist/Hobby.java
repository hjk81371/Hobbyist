package com.hobbyist.hobbyist;

import java.util.Objects;

public class Hobby {
    private String name;
    private String artist;
    private int id;

    public Hobby(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.id = generateId(name, artist);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public int getId() {
        return this.id;
    }

    public static int generateId(String n, String a) {
        return Objects.hash(n, a);
    }


}