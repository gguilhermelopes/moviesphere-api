package com.gguilhermelopes.movieSphere.services;

import com.gguilhermelopes.movieSphere.domain.Genre;
import com.gguilhermelopes.movieSphere.dto.GenreDTO;
import com.gguilhermelopes.movieSphere.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository repository;

    @Transactional(readOnly = true)
    public List<GenreDTO> findAll(){
        List<Genre> genreList = repository.findAll();
        return genreList.stream().map(GenreDTO::new).toList();
    }
}
