package com.hobbyist.hobbyist;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;


public class SimilarSongs {
    private static final String API_KEY = "c3825fc64587f9cce3b58912b58f25f8"; // Replace with your actual API key

    private static String fetchData(String url) throws IOException {
        Scanner scanner = new Scanner(new URL(url).openStream(), "UTF-8");
        String data = scanner.useDelimiter("\\A").next();
        scanner.close();
        return data;
    }

    public static List<String> searchSimilarMusic(Song song) throws IOException {
        String artist = song.getArtist();
        artist = artist.replace(" ", "+");
        String genre = song.getGenre();
        genre = genre.replace(" ", "+");

        String url = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=" + artist +
                "&api_key=" + API_KEY + "&format=json";
        String data = fetchData(url);

        List<String> similarMusic = new ArrayList<>();

        JsonObject json = com.google.gson.JsonParser.parseString(data).getAsJsonObject();
        if (json.has("similarartists")) {
            JsonObject similarArtists = json.getAsJsonObject("similarartists");
            JsonArray artistArray = similarArtists.getAsJsonArray("artist");
            for (JsonElement element : artistArray) {
                JsonObject artistObject = element.getAsJsonObject();
                String name = artistObject.get("name").getAsString();
                String match = artistObject.get("match").getAsString();

                // Compare attributes to determine similarity
                if (!name.equalsIgnoreCase(song.getArtist()) && Double.parseDouble(match) >= 0.5) {
                    similarMusic.add(name);
                }
            }
        }

        return similarMusic;
    }
/*
    public static void main(String[] args) {
        // Assuming you have a HobbyLibrary instance called library
        HobbyLibrary library = new HobbyLibrary();

        Song song1 = new Song("Song 1", "Artist 1", "Pop");
        library.addHobby(song1);

        try {
            Song song = (Song) library.getHobbies().get(0); // Assuming the first hobby is a song
            List<String> similarMusic = searchSimilarMusic(song);
            for (String artist : similarMusic) {
                System.out.println(artist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
