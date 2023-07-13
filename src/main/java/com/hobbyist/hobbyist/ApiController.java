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
    public void addNewHobby(@RequestParam("param1") String hobbyName, @RequestParam("param2") String hobbyArtist) {
 


        Hobby newHobby = new Hobby(hobbyName, hobbyArtist);

        library.addHobby(newHobby, 's');
    }

    @GetMapping("/find-similar")
    public String[] findSimilar() {
        return SimilarMusic.findSimilarMusic(library);
    }



}
