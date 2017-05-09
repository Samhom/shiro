package com.sanhu.dao;

import com.sanhu.entity.User;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

/**
 * Created by zhangboxun on 2017/3/31.
 */
public interface UserDao {

    void createUser(User user);
    void updateUser(User user);
    void deleteUser(Long userId);

    void correlationRoles(Long userId, Long... roleIds);
    
    void uncorrelationRoles(Long userId, Long... roleIds);

    User findOne(Long userId);

    User findByUsername(@Param("name")String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
    
	List<User> findAllUsers(@Param("offset")int offset, @Param("limit")int limit);
	
	int getAllCount();
}
