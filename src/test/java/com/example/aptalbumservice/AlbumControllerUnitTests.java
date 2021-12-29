package com.example.aptalbumservice;

import com.example.aptalbumservice.model.Album;
import com.example.aptalbumservice.repository.AlbumRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AlbumControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlbumRepository albumRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void givenAlbum_whenGetAlbumByAlbumId_thenReturnJsonAlbum() throws Exception {
        Album album1artist1 = new Album(21, 21, 1, "123654789","album1");

        given(albumRepository.findAlbumByAlbumId(21)).willReturn(album1artist1);

        mockMvc.perform(get("/api/albums/{albumId}",21))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("album1")))
                .andExpect(jsonPath("$.artistId",is(1)));
    }

    @Test
    void givenAlbum_whenGetAlbumsByArtistId_thenReturnJsonAlbums() throws Exception {
        Album album1artist1 = new Album(21, 21, 1, "123654789","album1");
        Album album2artist1 = new Album(22, 22, 1, "9876546321","album2");

        List<Album> albumList = new ArrayList<>();
        albumList.add(album1artist1);
        albumList.add(album2artist1);

        given(albumRepository.findAlbumsByArtistId(1)).willReturn(albumList);

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
    void givenAlbum_whenGetAlbums_thenReturnJsonAlbums() throws Exception {
        Album album1artist1 = new Album(21, 21, 1, "123654789","album1");
        Album album2artist1 = new Album(22, 22, 1, "9876546321","album2");

        List<Album> albumList = new ArrayList<>();
        albumList.add(album1artist1);
        albumList.add(album2artist1);

        given(albumRepository.findAll()).willReturn(albumList);

        mockMvc.perform(get("/api/albums"))
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
    void whenPostAlbum_thenReturnJsonAlbum() throws Exception{
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
    void givenAlbum_whenPutAlbum_thenReturnJsonAlbum() throws Exception{
        Album album1artist1 = new Album(21, 21, 1, "123654789","album1");

        given(albumRepository.findAlbumByAlbumId(21)).willReturn(album1artist1);

        Album updatedAlbum = new Album(21, 21, 1, "123654789","album1put");

        mockMvc.perform(put("/api/albums/{albumId}", 21)
                .content(mapper.writeValueAsString(updatedAlbum))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title",is("album1put")))
                .andExpect(jsonPath("$.albumId",is(21)))
                .andExpect(jsonPath("$.artistId",is(1)));
    }

    @Test
    void givenAlbum_whenDeleteAlbum_thenStatusOk() throws Exception{
        Album albumToBeDeleted = new Album(99, 99, 9, "999999999","album9");

        given(albumRepository.findAlbumByAlbumId(99)).willReturn(albumToBeDeleted);

        mockMvc.perform(delete("/api/albums/{albumId}",99)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenNoAlbum_whenDeleteAlbum_thenStatusNotFound() throws Exception{
        given(albumRepository.findAlbumByAlbumId(88)).willReturn(null);

        mockMvc.perform(delete("/api/albums/{albumId}",88)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
