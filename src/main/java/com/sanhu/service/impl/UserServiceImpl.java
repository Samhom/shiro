package com.sanhu.service.impl;

import com.sanhu.dao.UserDao;
import com.sanhu.entity.User;
import com.sanhu.service.UserService;
import com.sanhu.utils.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by zhangboxun on 2017/3/31.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 创建用户
     * @param user
     */
    @Override
    public User createUser(User user) {
        // 加密密码
        passwordHelper.encryptPassword(user);
        userDao.createUser(user);
        return user;
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    @Override
    public void changePassword(Long userId, String newPassword) {
        User user =userDao.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateUser(user);
    }

    /**
     * 添加用户-角色关系
     * @param userId
     * @param roleIds
     */
    @Override
    public void correlationRoles(Long userId, Long... roleIds) {
        userDao.correlationRoles(userId, roleIds);
    }


    /**
     * 移除用户-角色关系
     * @param userId
     * @param roleIds
     */
    @Override
    public void uncorrelationRoles(Long userId, Long... roleIds) {
        userDao.uncorrelationRoles(userId, roleIds);
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    @Override
    public Set<String> findRoles(String username) {
        return userDao.findRoles(username);
    }

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    @Override
    public Set<String> findPermissions(String username) {
        return userDao.findPermissions(username);
    }

    /**
     * 查找所有用户
     * @return
     */
	@Override
	public List<User> findUserList(String start, String end) {
		int offset = Integer.valueOf(start);
		int limit = Integer.valueOf(end);
		return userDao.findAllUsers(offset, limit);
	}

	@Override
	public int getUserCount() {
		return userDao.getAllCount();
	}

}
