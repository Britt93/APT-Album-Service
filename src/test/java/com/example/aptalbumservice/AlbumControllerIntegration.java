package com.example.aptalbumservice;

import com.example.aptalbumservice.model.Album;
import com.example.aptalbumservice.repository.AlbumRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class AlbumControllerIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlbumRepository albumRepository;

    private Album album1artist1 = new Album(21, 21, 1, "123654789","album1");
    private Album album2artist1 = new Album(22, 22, 1, "9876546321","album2");
    private Album album3artist2 = new Album(23, 23, 2, "654987321","album3");
    private Album albumToBeDeleted = new Album(99, 99, 9, "999999999","album9");

    @BeforeEach
    void beforeAllTests() {
        albumRepository.deleteAll();
        albumRepository.save(album1artist1);
        albumRepository.save(album2artist1);
        albumRepository.save(album3artist2);
        albumRepository.save(albumToBeDeleted);
    }

    @AfterEach
    void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        albumRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void givenAlbum_whenGetAlbumByAlbumId_thenReturnJsonAlbum() throws Exception {

        mockMvc.perform(get("/api/albums/{albumId}",21))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("album1")))
                .andExpect(jsonPath("$.artistId",is(1)));
    }

    @Test
    void givenAlbums_whenGetAlbumsByArtist_thenReturnJsonAlbums() throws Exception {

        List<Album> albumList = new ArrayList<>();
        albumList.add(album1artist1);
        albumList.add(album2artist1);

        mockMvc.perform(get("/api/albums/artist/{artistId}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title",is("album1")))
                .andExpect(jsonPath("$[0].albumId",is(21)))
                .andExpect(jsonPath("$[0].artistId",is(1)))
                .andExpect(jsonPath("$[1].title",is("album2")))
                .andExpect(jsonPath("$[1].albumId",is(22)))
                .andExpect(jsonPath("$[1].artistId",is(1)));
    }

    @Test
    void whenPostAlbum_thenReturnJsonAlbum() throws Exception {
        Album album4artist2 = new Album(24, 24, 2, "159487263","album4");

        mockMvc.perform(post("/api/albums")
                .content(mapper.writeValueAsString(album4artist2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("album4")))
                .andExpect(jsonPath("$.albumId",is(24)))
                .andExpect(jsonPath("$.artistId",is(2)));
    }

    @Test
    void givenAlbum_whenPutAlbum_thenReturnJsonAlbum() throws Exception {

        Album updatedAlbum = new Album(21, 21, 1, "123","album1");

        mockMvc.perform(put("/api/albums/{albumId}", updatedAlbum.getAlbumId())
                .content(mapper.writeValueAsString(updatedAlbum))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("album1")))
                .andExpect(jsonPath("$.albumId",is(21)))
                .andExpect(jsonPath("$.mbid",is("123")))
                .andExpect(jsonPath("$.artistId",is(1)));
    }

    @Test
    void givenAlbum_whenDeleteAlbum_thenStatusOk() throws Exception {

        mockMvc.perform(delete("/api/albums/{albumId}", 99)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenNoAlbum_whenDeleteAlbum_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/api/albums/{albumId}", 88)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
