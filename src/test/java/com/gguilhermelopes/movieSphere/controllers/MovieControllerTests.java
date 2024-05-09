package com.gguilhermelopes.movieSphere.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gguilhermelopes.movieSphere.dto.MovieDTO;
import com.gguilhermelopes.movieSphere.infra.exceptions.DataNotFoundException;
import com.gguilhermelopes.movieSphere.infra.exceptions.DatabaseException;
import com.gguilhermelopes.movieSphere.services.MovieService;
import com.gguilhermelopes.movieSphere.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MovieService service;
    private UUID existingId;
    private UUID nonExistingId;
    private UUID dependentId;
    private MovieDTO movieDTO;
    private PageImpl<MovieDTO> page;

    @BeforeEach
    public void setUp() throws Exception{
        movieDTO = Factory.createMovieDTO();
        page = new PageImpl<>(List.of(movieDTO));
        existingId = UUID.fromString("91c01ffe-28e0-4c3d-a904-b9c9d5b55877");
        nonExistingId = UUID.fromString("91c01ffe-28e0-4c3d-a904-b9c9d5b55866");
        dependentId = UUID.fromString("be09bfe2-05fe-45a9-9dc2-436fe8b4343e");

        when(service.findAll()).thenReturn(List.of(movieDTO));
        when(service.findAllPaged(any(Pageable.class))).thenReturn(page);

        when(service.findById(existingId)).thenReturn(movieDTO);
        when(service.findById(nonExistingId)).thenThrow(DataNotFoundException.class);

        when(service.update(eq(existingId), any(movieDTO.getClass()))).thenReturn(movieDTO);
        when(service.update(eq(nonExistingId), any(movieDTO.getClass()))).thenThrow(DataNotFoundException.class);

        when(service.insert(any(MovieDTO.class))).thenReturn(movieDTO);

        doNothing().when(service).delete(existingId);
        doThrow(DataNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);
    }

    @Test
    public void findAllPagedShouldReturnPage() throws Exception{
        ResultActions getResult = mockMvc.perform(get("/movies/paged")
                .accept(MediaType.APPLICATION_JSON));

        getResult.andExpect(status().isOk());
    }

    @Test
    public void findAllShouldReturnMovieList() throws Exception{
        ResultActions getResult = mockMvc.perform(get("/movies")
                .accept(MediaType.APPLICATION_JSON));

        getResult.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnMovieWhenIdExists() throws Exception{
        ResultActions getResult = mockMvc.perform(get("/movies/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        getResult.andExpect(status().isOk());
        getResult.andExpect(jsonPath("$.id").exists());
        getResult.andExpect(jsonPath("$.director").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception{
        ResultActions getResult = mockMvc.perform(get("/movies/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        getResult.andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnMovieWhenIdExists() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(movieDTO);

        ResultActions patchResult = mockMvc.perform(patch("/movies/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        patchResult.andExpect(status().isOk());
        patchResult.andExpect(jsonPath("$.id").exists());
        patchResult.andExpect(jsonPath("$.director").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(movieDTO);

        ResultActions patchResult = mockMvc.perform(patch("/movies/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        patchResult.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnMovie() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(movieDTO);

        ResultActions postResult = mockMvc.perform(post("/movies")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        postResult.andExpect(status().isCreated());
        postResult.andExpect(jsonPath("$.id").exists());
        postResult.andExpect(jsonPath("$.director").exists());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception{
        ResultActions deleteResult = mockMvc.perform(delete("/movies/{id}", existingId));

        deleteResult.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception{
        ResultActions deleteResult = mockMvc.perform(delete("/movies/{id}", nonExistingId));

        deleteResult.andExpect(status().isNotFound());
    }
}
