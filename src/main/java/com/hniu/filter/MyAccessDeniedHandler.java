package com.hniu.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hniu.dto.ResponseResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyAuthEntryPoint.class);
    //是Jackson库的核心类，用于JSON与Java对象的相互转换
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //设置响应状态码
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        //设置响应头的Content-Type为application/json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //设置响应体的字符编码为 UTF-8
        response.setCharacterEncoding("UTF-8");

        ResponseResult<Null> error = ResponseResult.error(403, "权限不足," + accessDeniedException.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
