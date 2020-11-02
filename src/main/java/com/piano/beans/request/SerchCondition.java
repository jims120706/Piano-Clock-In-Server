package com.piano.beans.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SerchCondition {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int index;
    private int size;
}
