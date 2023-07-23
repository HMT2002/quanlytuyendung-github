package com.java08.quanlituyendung.repository;

import com.java08.quanlituyendung.entity.UserAccountEntity;
import com.java08.quanlituyendung.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity,Long> {
    UserInfoEntity findUserInfoEntityByUserAccountInfo(UserAccountEntity userAccount);
    UserInfoEntity getAllById(Long id);
}
