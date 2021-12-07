package com.example.aptalbumservice.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;


@Entity
@Table(name="Album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int albumId;

    @Column(unique = true)
    private String mbid;
    private String title;

    public Album() {
    }

    public Album(int id, int albumId, String mbid, String title) {
        this.id = id;
        this.albumId = albumId;
        this.mbid = mbid;
        this.title = title;
    }

    @javax.persistence.Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() { return albumId; }

    public void setAlbumId(int albumId) { this.albumId = albumId; }

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
}