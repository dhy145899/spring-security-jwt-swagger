package com.hniu.mapper;

import com.hniu.pojo.Role;

import java.util.List;

public interface RoleMapper {

    public List<Role> findByUid(Integer uid);

    void insertRole(List<Role> role);
}
