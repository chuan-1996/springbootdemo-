package com.example.bootdemo.dao;

import com.example.bootdemo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * @author qq491
 */
public interface UserDao extends JpaRepository<UserEntity, Integer> {

    @Query(nativeQuery = true, value = "select * from user.user")
    Page<UserEntity> find(Pageable pageable);

    @Query(nativeQuery = true, value = "select * from user.user where username like %?1")
    List<UserEntity> findTest(String name);
}
