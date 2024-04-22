package com.gguilhermelopes.movieSphere.services;

import com.gguilhermelopes.movieSphere.domain.Genre;
import com.gguilhermelopes.movieSphere.domain.Movie;
import com.gguilhermelopes.movieSphere.dto.MovieDTO;
import com.gguilhermelopes.movieSphere.infra.exceptions.DataNotFoundException;
import com.gguilhermelopes.movieSphere.infra.exceptions.DatabaseException;
import com.gguilhermelopes.movieSphere.repositories.GenreRepository;
import com.gguilhermelopes.movieSphere.repositories.MovieRepository;
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
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public List<MovieDTO> findAll(){
        List<Movie> movieList = repository.findAll();
        return movieList.stream().map(MovieDTO::new).toList();
    }

    public Page<MovieDTO> findAllPaged(Pageable pageable) {
        Page<Movie> pagedMovieList = repository.findAll(pageable);
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
        dataToMovie(data, movie);
        movie = repository.save(movie);

        return new MovieDTO(movie);
    }


    @Transactional
    public MovieDTO update(UUID id, MovieDTO data) {
        try {
            Movie movie = repository.getReferenceById(id);
            dataToMovie(data, movie);
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

    private void dataToMovie(MovieDTO data, Movie movie) {
        movie.setName(data.name());
        movie.setReleaseYear(data.releaseYear());
        movie.setDirector(data.director());
        movie.setSynopsis(data.synopsis());
        movie.setImgUrl(data.imgUrl());
        movie.setRating(data.rating());


        movie.getGenres().clear();
        data.genres().forEach(genreDTO -> {
                Genre genre = genreRepository.getReferenceById(genreDTO.id());
                movie.getGenres().add(genre);
        });

    }



}
