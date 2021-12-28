package com.example.aptalbumservice.controller;

import com.example.aptalbumservice.model.Album;

import com.example.aptalbumservice.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AlbumRestController {

    @Autowired
    private AlbumRepository albumRepository;

    @PostConstruct
    public void fillDB(){
        if(albumRepository.count()==0){
            //nog de juiste mbid voor vinden OF barcode gebruiken ipv imdb OF ISNI?
            //Moeten de albums overeenkomen met de artiesten? (min. 1 album dat bij een artiest hoort?)
            albumRepository.save(new Album(1, 1, 1, "077774626729","A Kind of Magic"));
            albumRepository.save(new Album(2, 2, 1, "8809269505910","TestAlbum"));
            albumRepository.save(new Album(3, 3, 1, "075678200823","Gutter Ballet"));
        }

        System.out.println("Albums test: " + albumRepository.findAlbumByAlbumId(2).getTitle());
    }

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


/*
    //GET list albums
    @GetMapping("/albums/{title}")
    public List<Album> findAlbumByTitle(@PathVariable String title) {
        return albumRepository.findAlbumsByTitleContaining(title);
    }
*/

    //Add album
    @PostMapping("/albums")
    public Album addAlbum(@RequestBody Album album){
        return albumRepository.save(album);
    }


    //Update
    @PutMapping("/albums/{albumId}")
    public Album updateAlbum(@RequestBody Album updatedAlbum, @PathVariable int albumId){
        Album retrievedAlbum = albumRepository.findAlbumByAlbumId(updatedAlbum.getAlbumId());

        //retrievedAlbum.setId(updatedAlbum.getId());
        //retrievedAlbum.setAlbumId(updatedAlbum.getAlbumId());
        retrievedAlbum.setMbid(updatedAlbum.getMbid());
        retrievedAlbum.setTitle(updatedAlbum.getTitle());

        return  albumRepository.save(retrievedAlbum);
    }


    //Delete
    @DeleteMapping("/albums/{albumId}")
    public void deleteAlbum(@PathVariable int albumId){
        //Album album = albumRepository.findAlbumById(id);
        Album album = albumRepository.findAlbumByAlbumId(albumId);
        albumRepository.delete(album);
    }

}
