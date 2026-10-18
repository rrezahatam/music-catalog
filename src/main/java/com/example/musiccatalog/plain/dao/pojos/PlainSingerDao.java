package com.example.musiccatalog.plain.dao.pojos;

import com.example.musiccatalog.plain.pojos.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PlainSingerDao implements SingerDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(PlainSingerDao.class);

    @Override
    public Set<Singer> findAll() {
        Set<Singer> result = new HashSet<>();
        try (var connection = getConnection();
             var statement = connection.prepareStatement("SELECT * FROM SINGER");
             var resultSet = statement.executeQuery()) {

            while(resultSet.next()){
                var singer = new Singer();
                singer.setId(resultSet.getLong("ID"));
                singer.setFirstName(resultSet.getString("FIRST_NAME"));
                singer.setLastName(resultSet.getString("LAST_NAME"));
                singer.setBirthDate(resultSet.getDate("BIRTH_DATE")
                        .toLocalDate());
                result.add(singer);
            }

        } catch (SQLException ex) {
            LOGGER.error("Problem when executing SELECT * FROM SINGER", ex);
        }
        return result;
    }

    @Override
    public Set<Singer> findByFirstName(String firstName) {
        return Set.of();
    }

    @Override
    public Optional<String> findNameById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<String> findFirstNameById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<String> findLastNameById(Long id) {
        return Optional.empty();
    }

    @Override
    public Singer insert(Singer singer) {
        try(var connection = getConnection();
            var statement = connection.prepareStatement("INSERT INTO SINGER " +
                    "(FIRST_NAME, LAST_NAME, BIRTH_DATE) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, singer.getFirstName());
            statement.setString(2, singer.getLastName());
            statement.setDate(3, java.sql.Date.valueOf(singer.getBirthDate()));
            statement.execute();

            var generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                singer.setId(generatedKeys.getLong(1));
            }
            return singer;
        }catch (SQLException ex){
            LOGGER.error("Problem when executing INSERT INTO SINGER", ex);
        }
        return null;
    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long singerId) {
        try(var connection = getConnection();
            var statement = connection.prepareStatement("DELETE FROM SINGER WHERE ID=?")){

            statement.setLong(1, singerId);
            statement.execute();

        }catch (SQLException ex){
            LOGGER.error("Problem when executing DELETE FROM SINGER", ex);
        }
    }

    @Override
    public Set<Singer> findAllByAlbums() {
        return Set.of();
    }

    @Override
    public void insertWithAlbums(Singer singer) {

    }
}
