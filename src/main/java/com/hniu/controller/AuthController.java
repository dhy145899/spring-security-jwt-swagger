package com.hniu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hniu.dto.LoginRequest;
import com.hniu.dto.LoginResponse;
import com.hniu.dto.RegisterRequest;
import com.hniu.dto.ResponseResult;
import com.hniu.pojo.Role;
import com.hniu.pojo.User;
import com.hniu.service.UserService;
import com.hniu.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(
        //name分组名称（必填） description分组描述（选填）
        name = "auth API", description = "登录、注册接口"
)
@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 登录请求 post
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    @Operation(
            //接口的简短描述
            summary = "登录",
            //详细说明
            description = "根据用户名和密码登录",
            //分组（设置会覆盖类级别的tag）
            tags = {"auth API"},
            //标记接口是否已经弃用
            deprecated = false
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "登录成功",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "登录失败",
                    content = @Content
            )
    })
    public ResponseResult<LoginResponse> login(@Validated @RequestBody LoginRequest loginRequest) {
        //UsernamePasswordAuthenticationToken未认证令牌
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        //调用方法认证获取认证对象 认证失败会抛出异常
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //把认证对象存入security上下文
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        //通过JwtUtil根据认证对象生成token
        String token = jwtUtil.generateToken(authenticate);
        //获取用户详细数据（底层已经查询数据库并且比较过用户名密码是否匹配）
        User user = (User) authenticate.getPrincipal();

        return ResponseResult.success(new LoginResponse(
                //token令牌
                token,
                //用户id
                user.getId(),
                //用户名
                user.getUsername(),
                //用户权限
                user.getAuth()
        ));
    }

    /**
     * 注册用户 post
     * @param registerRequest
     * @return
     */
    @PostMapping("/register")
    @Operation(
            summary = "注册",
            description = "根据用户名和密码注册"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "注册成功",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "注册失败",
                    content = @Content
            )
    })
    public ResponseResult<?> register(@Validated @RequestBody RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        //查询username是否已经存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        if (userService.exists(queryWrapper)) {
            return ResponseResult.error("用户名已被占用！");
        }
        String encode = passwordEncoder.encode(registerRequest.getPassword());

        User user = new User(username, encode, List.of(new Role("USER")));
        userService.createUserWithRoles(user);
        return ResponseResult.success("注册成功！");
    }
}
