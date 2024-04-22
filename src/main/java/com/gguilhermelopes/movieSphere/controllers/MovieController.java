package com.gguilhermelopes.movieSphere.controllers;


import com.gguilhermelopes.movieSphere.dto.MovieDTO;
import com.gguilhermelopes.movieSphere.services.MovieService;
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
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> findAll(){

        List<MovieDTO> movieList = service.findAll();
        return ResponseEntity.ok(movieList);
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<MovieDTO>> findAllPaged(Pageable pageable){




        Page<MovieDTO> movieList = service.findAllPaged(pageable);
        return ResponseEntity.ok(movieList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable UUID id){

      MovieDTO movie = service.findById(id);
        return ResponseEntity.ok(movie);
    }
    @PostMapping
    public ResponseEntity<MovieDTO> insert(@RequestBody MovieDTO data){
        data = service.insert(data);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(data.id()).toUri();


        return ResponseEntity.created(uri).body(data);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable UUID id, @RequestBody MovieDTO data){
        data = service.update(id, data);

        return ResponseEntity.ok(data);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }


}
