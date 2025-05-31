package com.hniu.config;

import com.hniu.filter.JwtAuthFilter;
import com.hniu.filter.MyAccessDeniedHandler;
import com.hniu.filter.MyAuthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig01 {

    private final JwtAuthFilter authFilter;

    private final MyAccessDeniedHandler accessDeniedHandler;

    private final MyAuthEntryPoint authEntryPoint;

    public SecurityConfig01(JwtAuthFilter authFilter, MyAccessDeniedHandler accessDeniedHandler, MyAuthEntryPoint authEntryPoint) {
        this.authFilter = authFilter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * 提供一个加密器的bean
     * 1.在登陆的时候AuthenticationManager会自动处理，
     *   在UsernamePasswordAuthenticationFilter中会自动注入该加密器，
     *   然后把用户输入的密码加密，并且和数据库查询出的密码进行比较
     * 2.在注册的时候手动使用加密器对注册用户的密码进行加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/public/**",
                                "/test/**",
                                "/error/**",
                                "/login",
                                "/register",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**"
                        ).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                //设置自定义的异常（认证失败、权限不够）处理
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                //设置禁用 CSRF
                .csrf(csrf -> csrf.disable());

        //把自定义的token检查过滤器添加到最前面
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
