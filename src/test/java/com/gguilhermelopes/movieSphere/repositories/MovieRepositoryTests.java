package com.gguilhermelopes.movieSphere.repositories;


import com.gguilhermelopes.movieSphere.domain.Movie;
import com.gguilhermelopes.movieSphere.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class MovieRepositoryTests {
    @Autowired
    private MovieRepository repository;
    private UUID existingId;
    private UUID nonExistingId;

    private Movie movie;

    @BeforeEach
    void setUp() throws Exception {
        existingId = UUID.fromString("91c01ffe-28e0-4c3d-a904-b9c9d5b55877");
        nonExistingId = UUID.fromString("91c01ffe-28e0-4c3d-a904-b9c9d5b55866");
        movie = Factory.createMovie();
    }

    @Test
    public void saveShouldPersistWithUUIDWhenIdIsNull(){
        movie.setId(null);

        movie = repository.save(movie);

        Assertions.assertNotNull(movie.getId());
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists(){
        Optional<Movie> result = repository.findById(existingId);

        Assertions.assertTrue(result.isPresent());
    };

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist(){
        Optional<Movie> result = repository.findById(nonExistingId);

        Assertions.assertTrue(result.isEmpty());

    };

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(existingId);

        Optional<Movie> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }



}
