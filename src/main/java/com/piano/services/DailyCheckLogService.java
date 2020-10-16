package com.piano.services;

import com.piano.repositories.DailyCheckLogRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DailyCheckLogService {
    @Inject
    DailyCheckLogRepository dailyCheckLogRepository;

}
