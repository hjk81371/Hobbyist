package com.hobbyist.hobbyist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;


public class FmApiParser {
    private static final String API_KEY = "c3825fc64587f9cce3b58912b58f25f8"; // Replace with your actual API key


    public static String findSong(String songTitle) {

        String urlString = "http://ws.audioscrobbler.com/2.0/?method=track.search&api_key=" + API_KEY
                + "&track=" + songTitle + "&format=json";

        try {
            // Create URL object
            URL url = new URL(urlString);

            // Create HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            // Close the connection
            connection.disconnect();


            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed";
    } // findSong




    private static String fetchData(String url) throws IOException {
        Scanner scanner = new Scanner(new URL(url).openStream(), "UTF-8");
        String data = scanner.useDelimiter("\\A").next();
        scanner.close();
        return data;
    }

    public static Song[] searchSimilarMusic(Hobby song) throws IOException {
        String track = song.getName();
        String artist = song.getArtist();

        for (int i = 0; i < track.length(); i++) {
            if (track.charAt(i) == '(') {
                track = track.substring(0, i);
                break;
            }
        } // for
        track = track.trim();
        track = track.replace(" ", "+");
        artist = artist.replace(" ", "+");

        System.out.println("search track: " + track);
        System.out.println("search artist: " + artist);


        String url = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&track=" + track + "&artist=" + artist +
                "&api_key=" + API_KEY + "&format=json";
        String data = fetchData(url);

        Song[] similarMusic = new Song[10];

        JsonObject json = com.google.gson.JsonParser.parseString(data).getAsJsonObject();
        if (json.has("similartracks")) {
            System.out.println("HAS SIMILAR TRACKS");
            JsonObject similarTracks = json.getAsJsonObject("similartracks");
            System.out.println("SimilarTracks JsonObj: " + similarTracks.toString());
            JsonArray trackArray = similarTracks.getAsJsonArray("track");
            int index = 0;
            for (JsonElement element : trackArray) {
                System.out.println("Element: " + element.toString());
                JsonObject trackObject = element.getAsJsonObject();
                System.out.println("Track Obj: " + trackObject.toString());
                String name = trackObject.get("name").getAsString();
                System.out.println("NAME: " + name);
                JsonObject artistObject = trackObject.getAsJsonObject("artist");
                String artistName = artistObject.get("name").toString();
                System.out.println("ARTIST NAME: " + artistName);
                String match = trackObject.get("match").getAsString();
                System.out.println("MATCH: " + match);


                Song newSong = new Song(name, artistName);

                // Compare attributes to determine similarity
                if (!name.equalsIgnoreCase(song.getName()) && Double.parseDouble(match) >= 0.001) {
                    similarMusic[index] = newSong;
                    index++;
                    if (index > 9) return similarMusic;
                    // similarMusic.add(name + " - " + artistName);
                }
            }
        }
        int i = 0;
        while (similarMusic[i] != null) {
            i++;
        }
        Song[] filledArray = new Song[i];
        for (int j = 0; j < i; j++) {
            filledArray[j] = similarMusic[j];
        }
        return filledArray;

        //return similarMusic;
    } // searchSimilarMusic

    public static String getSimilarArtists(Song song) {
        return "";
    }




}
