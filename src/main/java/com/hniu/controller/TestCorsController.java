package com.hniu.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个测试的CORS控制器
 * 项目里并没有写测试的前端接口，要想测试需要自己写
 */
@RestController
public class TestCorsController {

    @GetMapping("/testCors")
    public Map<String, String> testCorsGet() {
        Map<String, String> map = new HashMap<>();
        map.put("form", "get");
        return map;
    }

    @PostMapping("/testCors")
    public Map<String, String> testCorsPost() {
        Map<String, String> map = new HashMap<>();
        map.put("form", "post");
        return map;
    }

    @PutMapping("/testCors")
    public Map<String, String> testCorsPut() {
        Map<String, String> map = new HashMap<>();
        map.put("form", "put");
        return map;
    }

    @DeleteMapping("/testCors")
    public Map<String, String> testCorsDelete() {
        Map<String, String> map = new HashMap<>();
        map.put("form", "delete");
        return map;
    }
}
