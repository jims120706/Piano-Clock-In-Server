package com.piano.repositories;

import com.piano.beans.db.DailyCheckLog;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

@JdbcRepository(dialect= Dialect.MYSQL)
public interface DailyCheckLogRepository extends CrudRepository<DailyCheckLog, Integer> {
    List<DailyCheckLog> findByUserIdAndCreateTimeBetween(int userId, LocalDateTime today, LocalDateTime plusDays);
}
