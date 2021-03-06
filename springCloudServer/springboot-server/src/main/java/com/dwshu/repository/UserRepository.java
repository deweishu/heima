package com.dwshu.repository;

import com.dwshu.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author dwshu
 * @create 2019/11/27
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    //登陆
    User findByUsernameAndPassword(String username,String password);

    //查询所有用户信息
    List<User> findAllByOrderById();



}
