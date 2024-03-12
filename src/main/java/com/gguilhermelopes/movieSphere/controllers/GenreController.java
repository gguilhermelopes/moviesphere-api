package com.gguilhermelopes.movieSphere.controllers;


import com.gguilhermelopes.movieSphere.dto.GenreDTO;
import com.gguilhermelopes.movieSphere.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/genres")
public class GenreController {

    @Autowired
    private GenreService service;

    @GetMapping
    public ResponseEntity<List<GenreDTO>> findAll(){

        List<GenreDTO> genreList = service.findAll();
        return ResponseEntity.ok(genreList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GenreDTO> findById(@PathVariable UUID id){

      GenreDTO genre = service.findById(id);
        return ResponseEntity.ok(genre);
    }
}
