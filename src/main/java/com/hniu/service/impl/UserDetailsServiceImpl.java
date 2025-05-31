package com.hniu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hniu.mapper.RoleMapper;
import com.hniu.mapper.UserMapper;
import com.hniu.pojo.Role;
import com.hniu.pojo.User;
import com.hniu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 除了手动调用之外
 * 该类注册成bean，使登录时，Authentication的UsernamePasswordAuthenticationFilter
 * 会自动从容器中取bean调用
 * 从数据库中查取数据，登录时用作比较
 */
@Service
public class UserDetailsServiceImpl extends ServiceImpl<UserMapper, User> implements UserDetailsService, UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 必须重写的方法，登录认证的时候底层会调用
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMapper.findByUsername(username);
    }

    @Transactional
    public void createUserWithRoles(User user) {
        // 1. 先插入用户记录
        userMapper.insertUserWithRoles(user);

        // 2. 设置角色中的uid为用户ID
        if (user.getRole() != null && !user.getRole().isEmpty()) {
            for (Role role : user.getRole()) {
                role.setUid(user.getId());
            }

            // 3. 批量插入角色
            roleMapper.insertRole(user.getRole());
        }
    }

    public User getUserWithRoles(String username) {
        return userMapper.getUserWithRolesByUsername(username);
    }

    public User getUserWithRolesById(Integer id) {
        return userMapper.getUserWithRolesById(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
