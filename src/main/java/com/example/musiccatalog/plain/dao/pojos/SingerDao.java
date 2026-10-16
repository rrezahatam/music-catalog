package com.example.musiccatalog.plain.dao.pojos;

import com.example.musiccatalog.plain.dao.CoreDao;
import com.example.musiccatalog.plain.pojos.Singer;

import java.util.Optional;
import java.util.Set;

public interface SingerDao extends CoreDao {
    Set<Singer> findAll();
    Set<Singer> findByFirstName(String firstName);
    Optional<String> findNameById(Long id);
    Optional<String> findFirstNameById(Long id);
    Optional<String> findLastNameById(Long id);
    Singer insert(Singer singer);
    void update(Singer singer);
    void delete(Long singerId);
    Set<Singer> findAllByAlbums();
    void insertWithAlbums(Singer singer);
}
