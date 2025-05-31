package com.hniu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录响应dto")
public class LoginResponse {
    @Schema(description = "token令牌" ,example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyNCIsImlhdCI6MTc0ODUyOTA0NywiZXhwIjoxNzQ4NjE1NDQ3fQ.aCEl-yd2gjRuTrJyC66aCb12Og35Kbvz7q8pRMoSlb-gZQo9iHB-tmkobt-8VyfuGsFPNbqwJ5buzoLZKZkrjg")
    private String token;
    @Schema(description = "类型")
    private String type = "Bearer";
    @Schema(description = "登录用户的id", example = "1")
    private Integer id;
    @Schema(description = "登录用户的username", example = "user1")
    private String username;
    @Schema(description = "用户权限")
    private List<String> role;

    public LoginResponse(String token, Integer id, String username, List<String> role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
