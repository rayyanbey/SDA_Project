package com.sda.Restaurant.services.implementation;

import com.sda.Restaurant.models.UserInfo;
import com.sda.Restaurant.repos.UserInfoRepo;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserInfoServImpl {

    private UserInfoRepo repo;

    public UserInfoServImpl(UserInfoRepo repo)
    {
        this.repo = repo;
    }

    public Optional<UserInfo> findByName(String name) {
        return repo.findByFullname(name);
    }

    public Optional<UserInfo> findById(int Id) {
        return repo.findById(Id);
    }
    public Optional<UserInfo> findbyEmail(String email) {
        return repo.findByEmail(email);
    }


}
