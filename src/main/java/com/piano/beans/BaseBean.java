package com.piano.beans;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BaseBean {
    @GeneratedValue
    @Id
    private int id;
    @DateCreated
    private LocalDateTime createTime;
}
