package com.example.musiccatalog;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildSmokeTest {

    @Test
    void shouldParseIsoDate(){
        LocalDate date = LocalDate.parse("2000-05-20");
        assertEquals(LocalDate.of(2000, 5, 20), date);
    }
}
