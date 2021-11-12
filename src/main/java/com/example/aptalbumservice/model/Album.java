package com.example.aptalbumservice.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;


@Entity
//@Table(name="album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String mbid;
    private String title;

    public Album() {
    }

    public Album(long id, String mbid, String title) {
        this.id = id;
        this.mbid = mbid;
        this.title = title;
    }

    @javax.persistence.Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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