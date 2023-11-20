package com.hobbyist.hobbyist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final HobbyistStorage library = new HobbyistStorage();

    @PostMapping("/find-hobby")
    public String findHobby(@RequestBody String hobbyToAdd) {

        return FmApiParser.findSong(hobbyToAdd);
    }

    @PostMapping("/add-hobby")
    public boolean addNewHobby(@RequestParam("param1") String hobbyName, @RequestParam("param2") String hobbyArtist) {

        Hobby newHobby = new Hobby(hobbyName, hobbyArtist);

        return library.addHobby(newHobby, 's');
    }

    @GetMapping("/find-similar")
    public String[] findSimilar(@RequestParam("param1") String similarityWeight, @RequestParam("param2") String songWeights) {
        return SimilarMusic.findSimilarMusic(library, similarityWeight, songWeights);
    }

    @GetMapping("/get-music-list")
    public String[] getSongList() {
        return FmApiParser.getSongListJson(library);
    }

    @PostMapping("/delete-hobby")
    public boolean deleteSong(@RequestParam("param1") String title, @RequestParam("param2") String artist) {
        return library.deleteSong(title, artist);
    }




}
