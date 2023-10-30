package com.hobbyist.hobbyist;

public class Song extends Hobby {
    private String genre;
    String mbid;

    public Song(String name, String artist) {
        super(name, artist);
        this.genre = null;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public void setTitle(String title) {
        super.setName(title);
    }

    public void setArtist(String artist) {
        super.setArtist(artist);
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMid() {
        return this.mbid;
    }

    public String getTitle() {
        return super.getName();
    }

    public String getArtist() {
        return super.getArtist();
    }

    public String getGenre() {
        return this.genre;
    }
    
}