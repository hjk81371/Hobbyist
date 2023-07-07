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
        if (identifier == 's') {
            this.lastSong = (Song)hobby;
        }

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
}