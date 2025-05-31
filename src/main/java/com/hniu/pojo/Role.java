package com.hniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("roles")
@Schema(description = "用户权限")
public class Role {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer uid;
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
