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



    public static List<String> getTagsList(Hobby song) {
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

        String url = "http://ws.audioscrobbler.com/2.0/?method=track.getTopTags&api_key=" + API_KEY + "&track=" + track 
        + "&artist=" + artist + "&user=hkirstein" + "&format=json";
        
        JsonObject JsonObj = null;
        try {
            JsonObj = getJsonObjectFromUrl(url, 15, "toptags");
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return null;
        } catch (IOException io) {
            io.printStackTrace();
            return null;
        }

        JsonArray tagArray = JsonObj.getAsJsonArray("tag");
        List<String> tagList = new ArrayList<>();
        for (JsonElement element : tagArray) {
            JsonObject tagObject = element.getAsJsonObject();
            String name = tagObject.get("name").getAsString();
            tagList.add(name);
        } // for
        return tagList;
    } // getTags


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




    private static String fetchData(String url) throws IOException, NullPointerException {
        Scanner scanner = new Scanner(new URL(url).openStream(), "UTF-8");
        String data = scanner.useDelimiter("\\A").next();
        scanner.close();
        return data;
    }

    private static JsonObject getJsonObjectFromUrl(String templateUrl, int sizeOfReturnList, String trait) throws IOException {
        System.out.println("URL = " + templateUrl);
        String data = fetchData(templateUrl);

        JsonObject json = com.google.gson.JsonParser.parseString(data).getAsJsonObject();
        JsonObject returnObj = json.getAsJsonObject(trait);
        if (returnObj == null) {
            throw new NullPointerException("No information for the trait: " + trait + "and url: " + templateUrl);
        }
        return returnObj;
    } // getSongListFronUrl


    //              // Compare attributes to determine similarity
    //              if (!name.equalsIgnoreCase(song.getName()) && Double.parseDouble(match) >= 0.001) {
    //                  similarMusic[index] = newSong;
    //                  index++;
    //                  if (index >= sizeOfReturnList) return similarMusic;
    //                  // similarMusic.add(name + " - " + artistName);
    //              } // if

    //          } // for
    //      } // if
    //      int i = 0;
    //      while (similarMusic[i] != null) {
    //          i++;
    //      }
    //      Song[] filledArray = new Song[i];
    //      for (int j = 0; j < i; j++) {
    //          filledArray[j] = similarMusic[j];
    //      }
    //      return filledArray;

    //     return similarMusic;
    // }  searchSimilarMusic

    public static List<Song> searchSimilarMusic(Hobby song, double minMatchValue) throws IOException{
        
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

        String url = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&track=" + track + "&artist=" + artist +"&api_key=" + API_KEY + "&format=json";
        
        String trait = "similartracks";
        JsonObject similarTracksJson = null;
        try {
            similarTracksJson = getJsonObjectFromUrl(url, 10, trait);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return null;
        } // try

        JsonArray trackArray = similarTracksJson.getAsJsonArray("track");
        List<Song> resultSongList = new ArrayList<>();
        int index = 0;
        for (JsonElement element : trackArray) {
            JsonObject trackObject = element.getAsJsonObject();
            String name = trackObject.get("name").getAsString();
            JsonObject artistObject = trackObject.getAsJsonObject("artist");
            String artistName = artistObject.get("name").toString();
            String matchStr = trackObject.get("match").getAsString();

            double match = Double.parseDouble(matchStr);

            Song newSong = new Song(name, artistName);
                if (!name.equalsIgnoreCase(song.getName()) && match >= minMatchValue) {
                    resultSongList.add(newSong);
                    index++;
                    if (index >= 100) break;
                } // if
        } // for

        return resultSongList;

    } // searchSimilarMusic

    public static String[] getSongListJson(HobbyistStorage library) {
        Hobby[] songs = library.getSongArray();
        String[] songListJson = new String[songs.length];
        Gson gson = new Gson();
        for (int i = 0; i < songs.length; i++) {
            songListJson[i] = gson.toJson(songs[i]);
        }
        return songListJson;
    }

/*
    public static Song[] searchSimilarArtists(Hobby song) throws IOException {

        String artist = song.getArtist();
        artist = artist.trim();
        artist = artist.replace(" ", "+");

        String url = "http://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=" + artist + "&api_key=" + API_KEY + "&format=json";

        String trait = "similarartists";

        Song[] resultSongList = getSongListFromUrl(url, 10, trait);

        return resultSongList;

    } // searchSimilarArtists
*/




}
