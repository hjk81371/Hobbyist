package com.hobbyist.hobbyist;

import java.util.ArrayList;
import java.util.List;


public class HobbyistStorage {
    Song lastSong;

    private List<Hobby> hobbies;

    public HobbyistStorage() {
        hobbies = new ArrayList<>();
    }

    public void addHobby(Hobby hobby, char identifier) {

        hobbies.add(hobby);
    }

    public List<Hobby> searchByName(String name) {
        List<Hobby> results = new ArrayList<>();
        for (Hobby hobby : hobbies) {
            if (hobby.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(hobby);
            }
        }
        return results;
    }

    public Song getLastSong() {
        return this.lastSong;
    }

    public void printHobbies() {
        for (Hobby hobby : hobbies) {
            System.out.println(hobby.getName());
        }
    }

    public String getAtIndex(int index) {
        return this.hobbies.get(index).getName();
    }


    public Hobby[] getSongArray() {
        int length = hobbies.size();
        Hobby[] songsArray = new Hobby[length];
        for (int i = 0; i < length; i++) {
            Hobby song = hobbies.get(i);
            songsArray[i] = song;
        }
        return songsArray;
    }
}