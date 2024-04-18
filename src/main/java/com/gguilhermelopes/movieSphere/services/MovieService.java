package com.gguilhermelopes.movieSphere.services;

import com.gguilhermelopes.movieSphere.domain.Movie;
import com.gguilhermelopes.movieSphere.dto.MovieDTO;
import com.gguilhermelopes.movieSphere.infra.exceptions.DataNotFoundException;
import com.gguilhermelopes.movieSphere.infra.exceptions.DatabaseException;
import com.gguilhermelopes.movieSphere.repositories.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional(readOnly = true)
    public List<MovieDTO> findAll(){
        List<Movie> movieList = repository.findAll();
        return movieList.stream().map(MovieDTO::new).toList();
    }

    public Page<MovieDTO> findAllPaged(PageRequest pageRequest) {
        Page<Movie> pagedMovieList = repository.findAll(pageRequest);
        return pagedMovieList.map(MovieDTO::new);
    }

    @Transactional(readOnly = true)
    public MovieDTO findById(UUID id) {
        Optional<Movie> obj = repository.findById(id);
        Movie movie = obj.orElseThrow(() -> new DataNotFoundException("Filme não encontrado para o ID informado."));

        return new MovieDTO(movie, movie.getGenres());
    }

    @Transactional
    public MovieDTO insert(MovieDTO data) {
        Movie movie = new Movie();
        //genre.setName(data.name());
        movie = repository.save(movie);

        return new MovieDTO(movie);
    }

    @Transactional
    public MovieDTO update(UUID id, MovieDTO data) {
        try {
            Movie movie = repository.getReferenceById(id);
           //genre.setName(data.name());
            movie = repository.save(movie);
            return new MovieDTO(movie);
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
