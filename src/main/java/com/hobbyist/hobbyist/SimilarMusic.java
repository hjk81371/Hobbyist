package com.hobbyist.hobbyist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimilarMusic {
    
    public static String[] findSimilarMusic(HobbyistStorage library) {
        Hobby[] songs = library.getSongArray();
        Hobby song = songs[songs.length - 1];
        List<String> similarSongs = new ArrayList<String>();
        try {
            similarSongs = FmApiParser.searchSimilarMusic(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int listLength = similarSongs.size();
        String[] similarSongsArray = new String[listLength];
        for (int i = 0; i < listLength; i++) {
            similarSongsArray[i] = similarSongs.get(i);
        }

        System.out.println("Num of Similar Songs: " + similarSongsArray.length);
        return similarSongsArray;

    }

}
