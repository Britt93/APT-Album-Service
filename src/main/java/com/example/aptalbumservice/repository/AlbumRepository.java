package com.example.aptalbumservice.repository;
import com.example.aptalbumservice.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findAlbumByMbid(String mbid);
    List<Album> findAlbumsByTitleContaining(String title);
    Album findAlbumByAlbumId(int albumId);
    List<Album> findAlbumsByArtistId(int artistId);



}
