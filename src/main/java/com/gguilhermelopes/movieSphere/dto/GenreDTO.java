package com.gguilhermelopes.movieSphere.dto;

import com.gguilhermelopes.movieSphere.domain.Genre;

import java.util.UUID;

public record GenreDTO(UUID id, String name) {
    public GenreDTO(Genre entity) {
        this(entity.getId(), entity.getName());
    }
}
