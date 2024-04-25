package com.sda.Restaurant.repos;

import com.sda.Restaurant.models.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserInfoRepo extends JpaRepository<UserInfo,Long> {

    Optional<UserInfo> findByFullname(String fullname);
    Optional<UserInfo> findById(int id);
    Optional<UserInfo> findByEmail(String email);

}
