package com.example.musiccatalog.plain;

import com.example.musiccatalog.plain.dao.pojos.PlainSingerDao;
import com.example.musiccatalog.plain.dao.pojos.SingerDao;
import com.example.musiccatalog.plain.pojos.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class PlainJdbcDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlainJdbcDemo.class);
    private static final SingerDao singerDao = new PlainSingerDao();
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex){
            LOGGER.info("Problem loading DB driver!", ex);
        }
    }

    public static void main(String[] args){

        LOGGER.info("Listing initial SINGER data:");
        listAllSingers();

        LOGGER.info("-------------");
        LOGGER.info("Insert a new Singer");

        Singer newSinger = new Singer();
        newSinger.setFirstName("Ed");
        newSinger.setLastName("Sheeran");
        newSinger.setBirthDate(LocalDate.of(1991, 2, 17));

        singerDao.insert(newSinger);

        LOGGER.info("The newSinger has Id now:{}", newSinger.getId());
        LOGGER.info("-------------");

        LOGGER.info("Listing SINGER data after newSinger was created:");
        listAllSingers();

        LOGGER.info("-------------");
        LOGGER.info("Deleting the newSinger from SINGER");
        singerDao.delete(newSinger.getId());

        LOGGER.info("listing SINGER data after deleting newSinger:");
        listAllSingers();
    }

    public static void listAllSingers(){
        var singers = singerDao.findAll();
        for(Singer singer: singers)
            LOGGER.info(singer.toString());
    }
}
