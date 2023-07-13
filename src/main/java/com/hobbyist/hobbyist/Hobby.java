package com.hobbyist.hobbyist;

public class Hobby {
    private String name;
    private String artist;

    public Hobby(String name, String artist) {
        this.name = name;
        this.artist = artist;
        System.out.println("Hobby Name: " + name);
        System.out.println("Hobby Artist: " + artist);

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
}