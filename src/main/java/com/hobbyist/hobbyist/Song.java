package com.hobbyist.hobbyist;

public class Song extends Hobby {
    private String artist;
    private String genre;

    public Song(String name) {
        super(name);
        this.artist = null;
        this.genre = null;
    }

    public void setTitle(String title) {
        super.setName(title);
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return super.getName();
    }

    public String getArtist() {
        return this.artist;
    }

    public String getGenre() {
        return this.genre;
    }
    
}