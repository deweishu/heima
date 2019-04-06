package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    User findByMobile(String mobile);

    @Modifying
    @Query("update User u set u.fanscount=u.fanscount+?2 where u.id = ?1")
    void updateFans(String userId, int count);


    @Modifying
    @Query("update User u set u.followcount=u.followcount+?2 where u.id = ?1")
    void updateFollowcount(String userId, int count);
}
