package com.hobbyist.hobbyist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.*;

public class SimilarMusic {
    
    public static String[] findSimilarMusic(HobbyistStorage library, String similarityWeightParam, String songWeightsParam) {
        String[] songWeightsStringArray = songWeightsParam.split(",");
        double[] weights = new double[library.getLength()];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Double.parseDouble(songWeightsStringArray[i])/100;
        }

        for (int i = 0; i < weights.length; i++) {
            System.out.println("BACKEND WEIGHTS: " + weights[i]);
        }

        double similarityWeight = Double.parseDouble(similarityWeightParam);
        similarityWeight = similarityWeight/100.0;

        List<Song> similars = new ArrayList<>();
        Hobby[] songs = library.getSongArray();
        
        // add all tags from all songs in library to tags list
        for (Hobby mySong : songs) {
            // add similar songs for each song in library to similars list
            try {
                List<Song> currSimilars = FmApiParser.searchSimilarMusic(mySong, 0.001);
                if (currSimilars != null) {
                    similars.addAll(currSimilars);
                } // if
            } catch (IOException e) {
                System.out.println("No similar songs for current song");
            } // try
        } // for

        List<Song> similarMusic = new ArrayList<>();
        System.out.println("SIMILARS SIZE = " + similars.size());

        Map<Integer, Song> similarsIdMap = new HashMap<>();
        for (Song song : similars) {
            similarsIdMap.put(song.getId(), song);
        }
        
        int k = 0;
        Map<Integer, Double> similarsFreq = new HashMap<>();
        for (Song song : similars) {
            if (weights[k] != 1) {
                // weighted song
                similarsFreq.put(song.getId(), 2*weights[k]);
            }
        } // for
        for (Song song : similars) {
            if (similarsFreq.containsKey(song.getId())) {
                double temp = similarsFreq.get(song.getId());
                similarsFreq.replace(song.getId(), temp + 1);
            } else {
                similarsFreq.put(song.getId(), 1.0);
            } // if
        }
        double similarsMean = 0.0;
        double similarsLength = 0;
        for (Map.Entry<Integer, Double> entry : similarsFreq.entrySet()) {
            similarsLength++;
            similarsMean += entry.getValue();
        }
        similarsMean = similarsMean/similarsLength;
        int tempCounter = 0;
        for (Map.Entry<Integer, Double> entry : similarsFreq.entrySet()) {
            tempCounter++;
            if (entry.getValue() + 0.08 >= similarsMean + similarityWeight) {
                similarMusic.add(similarsIdMap.get(entry.getKey()));
            }
        }
        System.out.println("SIMILARS MEAN = " + similarsMean);
        System.out.println("TOTAL COUNT BEFORE FILTER = " + tempCounter);

        System.out.println("SIMILAR MUSIC LENGTH: " + similarMusic.size());
        Song[] similarSongs = similarMusic.toArray(new Song[0]);

        String[] similarSongsJson = new String[similarSongs.length];

        Gson gson = new Gson();
        for (int j = 0; j < similarSongs.length; j++) {
            Song newSong = similarSongs[j];
            String jsonSong = gson.toJson(newSong);
            similarSongsJson[j] = jsonSong;
        }

        return similarSongsJson;
    }

}
