package com.example.musiccatalog;

public class QueryConstants {
    public static final String PARAMETRIZED_FIND_NAME =
            "SELECT CONCAT (FIRST_NAME, ' ', LAST_NAME) FROM SINGER WHERE ID = ?";
}
