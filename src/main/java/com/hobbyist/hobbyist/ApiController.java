package com.hobbyist.hobbyist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @GetMapping(value = "/endpoint", produces = "text/plain")
    public String handleReq() {
        return "Response from java file";
    }

    @PostMapping("/add-hobby")
    public String addHobby(@RequestBody String hobbyToAdd) {

        String jsonSong = SimilarSongs.findSong(hobbyToAdd);
        System.out.println(jsonSong);
        return "Success";
    }
}
