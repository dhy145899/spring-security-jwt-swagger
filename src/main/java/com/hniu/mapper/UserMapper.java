package com.hniu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hniu.pojo.Role;
import com.hniu.pojo.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    User findByUsername(String username);

    // 插入用户及其角色
    int insertUserWithRoles(User user);
    // 根据用户名查询用户及其角色（用于验证插入结果）
    User getUserWithRolesByUsername(String username);
    User getUserWithRolesById(Integer id);

    void updateUser(User user);
}
