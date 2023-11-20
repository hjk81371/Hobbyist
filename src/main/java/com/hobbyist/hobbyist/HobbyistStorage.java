package com.hobbyist.hobbyist;

import java.util.Map;
import java.util.HashMap;


public class HobbyistStorage {
    Song lastSong;

    private Map<Integer, Hobby> hobbies;

    public HobbyistStorage() {
        hobbies = new HashMap<>();
    }

    public int getLength() {
        return hobbies.values().size();
    } // getLength

    public Boolean addHobby(Hobby hobby, char identifier) {
        if (!this.hobbies.containsKey(hobby.getId())) {
            hobbies.put(hobby.getId(), hobby);
            return true;
        } else {
            System.out.println("CONTAINS HOBBY");
            return false;
        } // if
    }


    public Song getLastSong() {
        return this.lastSong;
    }


    public String getAtIndex(int index) {
        return this.hobbies.get(index).getName();
    }



    public Hobby[] getSongArray() {
        Hobby[] arr = new Hobby[this.getLength()];
        arr = this.hobbies.values().toArray(arr);
        return arr;
    }

    public Hobby getById(int id) {
        if (hobbies.containsKey(id)) {
            return hobbies.get(id);
        } else {
            System.out.println("TRIED GETBYID BUT FOUND NO SONG");
            return null;
        }
    }

    public void printSongs() {
        Hobby[] arr = this.getSongArray();
        for (Hobby a : arr) {
            System.out.println(a.getName() + " by " + a.getArtist());
        }
    }
    
    public boolean deleteSong(String title, String artist) {
        int hobbyId = Hobby.generateId(title, artist);
        if (this.hobbies.remove(hobbyId) == null) {
            return false;
        } else {
            return true;
        } // if
    }
}