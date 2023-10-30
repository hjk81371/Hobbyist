package com.hobbyist.hobbyist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

public class SimilarMusic {
    
    public static String[] findSimilarMusic(HobbyistStorage library) {
        Hobby[] songs = library.getSongArray();
        Hobby song = songs[songs.length - 1];
        // List<String> similarSongs = new ArrayList<String>();
        Song[] similarSongs = null;
        try {
            similarSongs = FmApiParser.searchSimilarMusic(song);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String[] similarSongsJson = new String[similarSongs.length];
        // int listLength = similarSongs.size();
        // String[] similarSongsArray = new String[listLength];
        // for (int i = 0; i < listLength; i++) {
        //     similarSongsArray[i] = similarSongs.get(i);
        // }

        // System.out.println("Num of Similar Songs: " + similarSongsArray.length);

        // Song testSong = new Song("All Falls Down", "Kanye West");
        // Gson gson = new Gson();
        // String testJson = gson.toJson(testSong);
        // return testJson;
        Gson gson = new Gson();
        for (int i = 0; i < similarSongs.length; i++) {
            Song newSong = similarSongs[i];
            String jsonSong = gson.toJson(newSong);
            similarSongsJson[i] = jsonSong;
        }

        return similarSongsJson;

        // return similarSongsArray;

    }

}
