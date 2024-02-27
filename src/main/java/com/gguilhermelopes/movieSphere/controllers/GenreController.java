package com.gguilhermelopes.movieSphere.controllers;

import com.gguilhermelopes.movieSphere.domain.Genre;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/genres")
public class GenreController {

    @GetMapping
    public ResponseEntity<List<Genre>> findAll(){
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre("1", "Terror"));
        genreList.add(new Genre("2", "Sci-Fi"));
        genreList.add(new Genre("3", "Drama"));

        return ResponseEntity.ok(genreList);
    }
}
