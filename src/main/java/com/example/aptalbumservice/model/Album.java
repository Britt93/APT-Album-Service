package com.example.aptalbumservice.model;

import javax.persistence.*;


@Entity
@Table(name="album")
public class Album {

    //Database ID
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int albumId;

    //Foreign key (1 artist heeft meer albums, 1 album hoort bij 1 artist)
    private int artistId;

    @Column(unique = true)
    private String mbid;
    private String title;
    private int numberStreams;

    public Album() {
    }

    public Album(int id, int albumId, int artistId, String mbid, String title) {
        this.id = id;
        this.albumId = albumId;
        this.artistId = artistId;
        this.mbid = mbid;
        this.title = title;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() { return albumId; }

    public void setAlbumId(int albumId) { this.albumId = albumId; }

    public int getArtistId() { return artistId; }

    public void setArtistId(int artistId) { this.artistId = artistId; }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int getNumberStreams() { return numberStreams; }

    public void setNumberStreams(int numberStreams) { this.numberStreams = numberStreams; }
}