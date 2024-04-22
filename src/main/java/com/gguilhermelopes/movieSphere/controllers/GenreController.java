package com.gguilhermelopes.movieSphere.controllers;


import com.gguilhermelopes.movieSphere.dto.GenreDTO;
import com.gguilhermelopes.movieSphere.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<GenreDTO>> findAllPaged(Pageable pageable){
        Page<GenreDTO> genreList = service.findAllPaged(pageable);
        return ResponseEntity.ok(genreList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GenreDTO> findById(@PathVariable UUID id){

      GenreDTO genre = service.findById(id);
        return ResponseEntity.ok(genre);
    }
    @PostMapping
    public ResponseEntity<GenreDTO> insert(@RequestBody GenreDTO data){
        data = service.insert(data);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(data.id()).toUri();


        return ResponseEntity.created(uri).body(data);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<GenreDTO> update(@PathVariable UUID id, @RequestBody GenreDTO data){
        data = service.update(id, data);

        return ResponseEntity.ok(data);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }


}
