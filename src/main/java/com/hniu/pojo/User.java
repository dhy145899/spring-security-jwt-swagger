package com.hniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("users")
@Schema(description = "用户数据")
public class User implements UserDetails {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;

    @TableField(exist = false)
    private List<Role> role = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = getAuth();
        if (roles.isEmpty()) {
            return Collections.emptyList(); // 返回空权限列表，防止 NullPointerException
        }
        // 将每个角色转换为 GrantedAuthority，添加 ROLE_ 前缀
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // 添加 ROLE_ 前缀
                .collect(Collectors.toList());
    }

    public List<String> getAuth() {
        List<String> roles = new ArrayList<>();
        for (Role r : role) {
            roles.add(r.getRole());
        }
        return roles;
    }

    public User(String username, String password, List<Role> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
