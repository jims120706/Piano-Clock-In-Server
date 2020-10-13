package com.piano.repositories;

import com.piano.beans.db.UserInfo;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
@JdbcRepository(dialect= Dialect.MYSQL)
public interface UserInfoRepository extends CrudRepository<UserInfo, Integer> {
    Optional<UserInfo> findByOpenId(String openId);
}
