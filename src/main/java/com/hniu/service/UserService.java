package com.hniu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hniu.pojo.Role;
import com.hniu.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService extends IService<User> {
    public UserDetails loadUserByUsername(String username);
    public void createUserWithRoles(User user);
    public User getUserWithRoles(String username);
    User getUserWithRolesById(Integer id);

    void updateUser(User user);
}
