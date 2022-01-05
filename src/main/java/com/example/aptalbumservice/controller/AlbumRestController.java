package com.example.aptalbumservice.controller;

import com.example.aptalbumservice.model.Album;

import com.example.aptalbumservice.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AlbumRestController {

    @Autowired
    private AlbumRepository albumRepository;

    //GET list of all albums
    @GetMapping("/albums")
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    //GET all albums by artist
    @GetMapping("/albums/artist/{artistId}")
    public List<Album> getAlbumsByArtist(@PathVariable int artistId){
        return albumRepository.findAlbumsByArtistId(artistId);
    }

    //GET one album
    @GetMapping("/albums/{albumId}")
    public Album findAlbumByAlbumId(@PathVariable int albumId) {
        return albumRepository.findAlbumByAlbumId(albumId);
    }


    //Add album
    @PostMapping("/albums")
    public Album addAlbum(@RequestBody Album album){
        albumRepository.save(album);
        return album;
    }


    //Update
    @PutMapping("/albums/{albumId}")
    public Album updateAlbum(@RequestBody Album updatedAlbum, @PathVariable int albumId){
        Album retrievedAlbum = albumRepository.findAlbumByAlbumId(albumId);

        retrievedAlbum.setMbid(updatedAlbum.getMbid());
        retrievedAlbum.setTitle(updatedAlbum.getTitle());
        retrievedAlbum.setNumberStreams(updatedAlbum.getNumberStreams());


        albumRepository.save(retrievedAlbum);
        return retrievedAlbum;
    }


    //Delete
    @DeleteMapping("/albums/{albumId}")
    public ResponseEntity deleteAlbum(@PathVariable int albumId){
        Album album = albumRepository.findAlbumByAlbumId(albumId);
        //albumRepository.delete(album);

        if(album!=null){
            albumRepository.delete(album);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

}
