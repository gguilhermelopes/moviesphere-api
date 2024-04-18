package com.gguilhermelopes.movieSphere.dto;

import com.gguilhermelopes.movieSphere.domain.Genre;
import com.gguilhermelopes.movieSphere.domain.Movie;


import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record MovieDTO(
        UUID id,
        String name,
        Integer releaseYear,
        String director,
        String synopsis,
        String imgUrl,
        Double rating,
        List<GenreDTO> genres
        )
{
    public MovieDTO(Movie entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getReleaseYear(),
                entity.getDirector(),
                entity.getSynopsis(),
                entity.getImgUrl(),
                entity.getRating(),
                List.of()
        );
    }

    public MovieDTO(Movie entity, Set<Genre> genres){
        this(
                entity.getId(),
                entity.getName(),
                entity.getReleaseYear(),
                entity.getDirector(),
                entity.getSynopsis(),
                entity.getImgUrl(),
                entity.getRating(),
                genres.stream().map(GenreDTO::new).collect(Collectors.toList())
        );


    }
}

