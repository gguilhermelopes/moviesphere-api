package com.gguilhermelopes.movieSphere.tests;

import com.gguilhermelopes.movieSphere.domain.Genre;
import com.gguilhermelopes.movieSphere.domain.Movie;
import com.gguilhermelopes.movieSphere.dto.MovieDTO;

import java.time.Instant;
import java.util.UUID;

public class Factory {

    public static Movie createMovie(){
        UUID movieId = UUID.fromString("deacc015-05d5-499a-96ca-7626609ef4f3");
        UUID genreId = UUID.fromString("ec075f4a-75bd-4ef4-bb7e-3f80d10a9c11");
        Movie movie = new Movie();

        movie.setId(movieId);
        movie.setName("Drive My Car");
        movie.setReleaseYear(2021);
        movie.setDirector("Ryusuke Hamaguchi");
        movie.setSynopsis("Yusuke Kafuku, a stage actor and director, still unable, after two years, to cope with the loss of his beloved wife, accepts to direct Uncle Vanja at a theater festival in Hiroshima. There he meets Misaki, an introverted young woman, appointed to drive his car. In between rides, secrets from the past and heartfelt confessions will be unveiled.");
        movie.setImgUrl("https://a.ltrbxd.com/resized/film-poster/6/7/9/2/9/1/679291-drive-my-car-0-1000-0-1500-crop.jpg?v=9e1f7c9f35");
        movie.setRating(4.2);

        movie.getGenres().add(new Genre(genreId, "Drama", Instant.now(), Instant.now()));

        return movie;
    }

    public static MovieDTO createMovieDTO(){
        Movie movie = createMovie();
        return new MovieDTO(movie, movie.getGenres());
    }
}
