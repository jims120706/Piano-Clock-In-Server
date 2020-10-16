package com.piano.services;

import com.piano.beans.db.UserInfo;
import com.piano.repositories.UserInfoRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class UserInfoService {
    @Inject
    UserInfoRepository repository;

    public UserInfo create(UserInfo userInfo) {
        return repository.save(userInfo);
    }

    public Optional<UserInfo> findByOpenId(String openId) {
        return repository.findByOpenId(openId);
    }

    public Optional<UserInfo> findById(int id) {
        return repository.findById(id);
    }
}
