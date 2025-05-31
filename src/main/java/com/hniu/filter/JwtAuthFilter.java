package com.hniu.filter;

import com.hniu.pojo.User;
import com.hniu.service.UserService;
import com.hniu.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 这是一个filter
 * 会在SecurityConfig中被手动注册到所有过滤器之前
 * 对所有的请求进行过滤，拿到请求携带的token，检查是否合法，
 * 合法就把详细数据根据token里拿到的username查出来，
 * 然后存到security上下文
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    /**
     * 从request中获取token
     * @param request
     * @return
     */
    private String parseJwt(HttpServletRequest request) {
        //从请求头中获取token令牌
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    /**
     * 过滤，获取用户数据进行存储到security上下文，没有则不存
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtil.validateToken(jwt)) {
                //jwt有效
                //获取username
                String username = jwtUtil.getUsername(jwt);
                //根据username查询详细数据
                User userDetails = (User) userService.loadUserByUsername(username);
                //封存用户数据 用户权限
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                //设置request补充细节
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //存储到security上下文
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        //不管有无token，是否有效，都要继续执行
        filterChain.doFilter(request, response);
    }
}
