package com.gguilhermelopes.movieSphere.services;


import com.gguilhermelopes.movieSphere.domain.Genre;
import com.gguilhermelopes.movieSphere.domain.Movie;
import com.gguilhermelopes.movieSphere.dto.MovieDTO;
import com.gguilhermelopes.movieSphere.infra.exceptions.DataNotFoundException;
import com.gguilhermelopes.movieSphere.infra.exceptions.DatabaseException;
import com.gguilhermelopes.movieSphere.repositories.GenreRepository;
import com.gguilhermelopes.movieSphere.repositories.MovieRepository;
import com.gguilhermelopes.movieSphere.tests.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {
    @InjectMocks
    private MovieService service;

    @Mock
    private MovieRepository repository;

    @Mock
    private GenreRepository genreRepository;
    private UUID existingId;
    private UUID existingGenreId;
    private UUID nonExistingId;
    private UUID dependentId;
    private PageImpl<Movie> page;
    private Movie movie;

    private MovieDTO updateData;
    private Genre genre;

    @BeforeEach
    void setUp() throws Exception {
        existingId = UUID.fromString("91c01ffe-28e0-4c3d-a904-b9c9d5b55877");
        existingGenreId = UUID.fromString("3d18d8ea-4dc8-417d-822c-0ebb25d4f13d");
        nonExistingId = UUID.fromString("91c01ffe-28e0-4c3d-a904-b9c9d5b55866");
        dependentId = UUID.fromString("be09bfe2-05fe-45a9-9dc2-436fe8b4343e");
        movie = Factory.createMovie();
        updateData = Factory.createMovieDTO();
        genre = movie.getGenres().stream().findFirst().get();
        page = new PageImpl<>(List.of(movie));

        when(repository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);
        when(repository.findAll()).thenReturn(List.of(movie));

        when(repository.save(ArgumentMatchers.any(Movie.class))).thenReturn(movie);

        when(repository.findById(existingId)).thenReturn(Optional.of(movie));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        when(repository.getReferenceById(existingId)).thenReturn(movie);
        doThrow(EntityNotFoundException.class).when(repository).getReferenceById(nonExistingId);

        when(repository.save(movie)).thenReturn(movie);

        doNothing().when(repository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        when(repository.existsById(existingId)).thenReturn(true);
        when(repository.existsById(nonExistingId)).thenReturn(false);
        when(repository.existsById(dependentId)).thenReturn(true);

        when(genreRepository.getReferenceById(existingGenreId)).thenReturn(genre);

    }

    @Test
    public void findAllPagedShouldReturnPage(){
        Pageable pageable = PageRequest.of(0, 10);

        Page<MovieDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);
        verify(repository).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnMovieDTOWhenIdExists(){
        MovieDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);

        verify(repository).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowDataNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(DataNotFoundException.class, () -> {
           service.findById(nonExistingId);
        });

        verify(repository).findById(nonExistingId);
    }

    @Test
    public void updateShouldReturnMovieDTOWhenIdExists(){
        MovieDTO result = service.update(existingId, updateData);

        Assertions.assertNotNull(result);

        verify(repository).getReferenceById(existingId);
        verify(repository).save(movie);
    }

    @Test
    public void updateShouldThrowDataNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            service.update(nonExistingId, updateData);
        });

        verify(repository).getReferenceById(nonExistingId);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        verify(repository).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowDataNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenDependentId(){
        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });
    }

}
