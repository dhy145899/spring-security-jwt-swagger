package com.hniu.controller;

import com.hniu.dto.LoginRequest;
import com.hniu.dto.ResponseResult;
import com.hniu.dto.UserDto;
import com.hniu.pojo.User;
import com.hniu.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user API", description = "用户请求接口")
// 所有方法使用JWT认证 name认证方案的名称，必须与@SecurityScheme中定义的name一致
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    @Operation(
            summary = "查询用户信息",
            description = "根据用户id查询用户详细信息"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "获取用户成功",
                    content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "用户获取失败",
                    content = @Content
            )
    })
    public ResponseResult<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserWithRolesById(id);
        return ResponseResult.success(user);
    }

    //@GetMapping("/{username}")
    /*@Operation(
            summary = "查询用户信息",
            description = "根据用户名username查询用户详细信息"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "获取用户成功",
                    content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "用户获取失败",
                    content = @Content
            )
    })*/
    public ResponseResult<User> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.getUserWithRoles(username);
        return ResponseResult.success(user);
    }

    //根据id修改User对象
    @PutMapping
    @Operation(
            summary = "修改用户信息",
            description = "修改用户信息"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "修改用户信息成功",
                    content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "修改用户信息失败",
                    content = @Content
            )
    })
    public ResponseResult<?> updateUserById(@Validated @RequestBody UserDto user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User u = new User(user.getId(), user.getUsername(), encodedPassword, null);
        userService.updateUser(u);
        User selectUser = userService.getUserWithRolesById(user.getId());
        if(selectUser.getUsername().equals(user.getUsername()) && selectUser.getPassword().equals(encodedPassword)) {
            return ResponseResult.success(selectUser);
        }
        return ResponseResult.error("修改用户信息失败！");
    }
}
