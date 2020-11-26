package com.piano.repositories;

import com.piano.beans.db.DailyCheck;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect= Dialect.MYSQL)
public interface DailyCheckRepository extends CrudRepository<DailyCheck, Integer> {
    Optional<DailyCheck> findByUserIdAndCheckDateEquals(int id, LocalDateTime atStartOfDay);
    List<DailyCheck> findByUserIdAndCheckDateBetween(int id, LocalDateTime start,LocalDateTime end);

    @Query(value="select ifNull(sum(hours),0) from dailyCheck where userId=:id")
    Optional<BigDecimal> sumHoursByUserId(int id);

    Page<DailyCheck> findByUserIdOrderByCheckDateDesc(int id, Pageable from);

    List<DailyCheck> findByUserIdAndCheckDateBetweenOrderByCheckDateDesc(int id, LocalDateTime startTime, LocalDateTime endTime);

    Page<DailyCheck> findByUserIdAndCheckDateBetweenOrderByCheckDateDesc(int id, LocalDateTime startTime, LocalDateTime endTime, Pageable page);

}
