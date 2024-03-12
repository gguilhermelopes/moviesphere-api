package com.gguilhermelopes.movieSphere.services;

import com.gguilhermelopes.movieSphere.domain.Genre;
import com.gguilhermelopes.movieSphere.dto.GenreDTO;
import com.gguilhermelopes.movieSphere.infra.exceptions.EntityNotFoundException;
import com.gguilhermelopes.movieSphere.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GenreService {

    @Autowired
    private GenreRepository repository;

    @Transactional(readOnly = true)
    public List<GenreDTO> findAll(){
        List<Genre> genreList = repository.findAll();
        return genreList.stream().map(GenreDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public GenreDTO findById(UUID id) {
        Optional<Genre> obj = repository.findById(id);
        Genre genre = obj.orElseThrow(() -> new EntityNotFoundException("Genre not found."));

        return new GenreDTO(genre);
    }

    @Transactional
    public GenreDTO insert(GenreDTO data) {
        Genre genre = new Genre();
        genre.setName(data.name());
        genre = repository.save(genre);

        return new GenreDTO(genre);
    }
}
