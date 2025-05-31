package com.hniu.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.jdbc.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Generic API response wrapper for all endpoints")
public class ResponseResult<T> {

    @Schema(description = "Response status code, e.g., 200 for success, 400 for error",
            example = "200")
    private Integer code;

    @Schema(description = "Response message describing the result",
            example = "Operation successful")
    private String message;

    @Schema(description = "Response data, can be any type or null")
    private T data;

    /**
     *
     * @param code
     * @param message
     * @param data
     * @return 三个参数的成功响应
     */
    public static <E> ResponseResult<E> success(Integer code, String message, E data) {
        return new ResponseResult<>(code, message, data);
    }

    /**
     *
     * @param data
     * @return data参数的快速成功响应
     */
    public static <E> ResponseResult<E> success(E data) {
        return success(200, "操作成功", data);
    }

    /**
     * message参数的快速成功响应
     * @param message
     * @return
     * @param <E>
     */
    public static <E> ResponseResult<E> success(String message) {
        return success(200, message, null);
    }

    /**
     *
     * @param code
     * @param message
     * @return 自定义code,message错误响应
     */
    public static ResponseResult<Null> error(Integer code, String message) {
        return new ResponseResult<>(code, message, null);
    }

    /**
     *
     * @param message
     * @return message参数的快速失败响应
     */
    public static ResponseResult<Null> error(String message) {
        return error(400, message);
    }
}
