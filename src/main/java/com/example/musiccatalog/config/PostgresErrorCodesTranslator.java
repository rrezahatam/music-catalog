package com.example.musiccatalog.config;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import java.sql.SQLException;

public class PostgresErrorCodesTranslator extends SQLErrorCodeSQLExceptionTranslator {

    @Override
    protected DataAccessException customTranslate(
            String task, String sql, SQLException sqelx) {
        if (sqelx.getErrorCode() == -12345)
            return new DeadlockLoserDataAccessException(task, sqelx);
        return null;
    }

}

