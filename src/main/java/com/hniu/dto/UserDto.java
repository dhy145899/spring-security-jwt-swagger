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
@Schema(description = "用户数据请求dto")
public class UserDto {

    @Schema(description = "用户id", example = "1")
    @NotBlank
    @Size(min = 1, max = 10, message = "用户id长度需在1-10位之间")
    @Pattern(regexp = "^[0-9]+$", message = "用户id只能包含数字")
    private Integer id;

    @Schema(description = "用户名", example = "user1")
    @NotBlank
    @Size(min = 4, max = 10, message = "用户名长度需在4-10位之间")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "用户名只能包含字母和数字")
    private String username;

    @Schema(description = "密码", example = "user123456")
    @NotBlank
    @Size(min = 6, max = 20, message = "密码长度需在6-20位之间")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).+$",
            message = "密码必须包含字母和数字"
    )
    private String password;
}
