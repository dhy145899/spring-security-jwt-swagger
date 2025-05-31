package com.hniu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "注册请求")
public class RegisterRequest {

    @Size(min = 4, max = 10, message = "用户名长度需在4-10位之间")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "用户名只能包含字母和数字")
    @NotBlank
    @Schema(description = "注册的用户名", example = "user1")
    private String username;

    @Size(min = 6, max = 20, message = "密码长度需在6-20位之间")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).+$",
            message = "密码必须包含字母和数字"
    )
    @NotBlank
    @Schema(description = "注册的密码", example = "user123456")
    private String password;
}
