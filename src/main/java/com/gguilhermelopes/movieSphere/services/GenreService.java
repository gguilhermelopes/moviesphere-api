package com.gguilhermelopes.movieSphere.services;

import com.gguilhermelopes.movieSphere.domain.Genre;
import com.gguilhermelopes.movieSphere.dto.GenreDTO;
import com.gguilhermelopes.movieSphere.infra.exceptions.DataNotFoundException;
import com.gguilhermelopes.movieSphere.infra.exceptions.DatabaseException;
import com.gguilhermelopes.movieSphere.repositories.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    public Page<GenreDTO> findAllPaged(Pageable pageable) {
        Page<Genre> pagedGenreList = repository.findAll(pageable);
        return pagedGenreList.map(GenreDTO::new);
    }

    @Transactional(readOnly = true)
    public GenreDTO findById(UUID id) {
        Optional<Genre> obj = repository.findById(id);
        Genre genre = obj.orElseThrow(() -> new DataNotFoundException("Gênero não encontrado para o ID informado."));

        return new GenreDTO(genre);
    }

    @Transactional
    public GenreDTO insert(GenreDTO data) {
        Genre genre = new Genre();
        genre.setName(data.name());
        genre = repository.save(genre);

        return new GenreDTO(genre);
    }

    @Transactional
    public GenreDTO update(UUID id, GenreDTO data) {
        try {
            Genre genre = repository.getReferenceById(id);
            genre.setName(data.name());
            genre = repository.save(genre);
            return new GenreDTO(genre);
        }
        catch (EntityNotFoundException exception) {
            throw new DataNotFoundException("Gênero não encontrado para o ID informado.");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(UUID id) {
        if(!repository.existsById(id)){
            throw new DataNotFoundException("Gênero não encontrado para o ID informado.");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException exception){
            throw new DatabaseException("Falha de integridade referencial");
        }

    }


}
