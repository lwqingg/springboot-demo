package com.demo.controller;

import com.demo.entity.User;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ==================== GET 接口 ====================

    /**
     * GET接口：根据ID查询用户
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.ok(ApiResult.error("用户不存在"));
        }
        return ResponseEntity.ok(ApiResult.success(user));
    }

    /**
     * GET接口：查询所有用户
     */
    @GetMapping("/list")
    public ResponseEntity<ApiResult<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResult.success(users));
    }

    // ==================== POST 接口 ====================

    /**
     * POST接口：新增用户
     */
    @PostMapping
    public ResponseEntity<ApiResult<User>> addUser(@RequestBody User user) {
        User result = userService.addUser(user);
        return ResponseEntity.ok(ApiResult.success("添加成功", result));
    }

    /**
     * POST接口：用户登录
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResult<User>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        User user = userService.getUserByIdFromMysqlAndWriteToRedis(
            userService.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .map(u -> u.getId())
                .orElse(null)
        );

        if (user != null) {
            return ResponseEntity.ok(ApiResult.success("登录成功", user));
        }
        return ResponseEntity.ok(ApiResult.error("用户名或密码错误"));
    }

    // ==================== PUT 接口（修改） ====================

    /**
     * PUT接口：修改用户
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateUser(user);
        userService.deleteUserCache(id);
        return ResponseEntity.ok(ApiResult.success("修改成功", user));
    }

    // ==================== DELETE 接口（删除） ====================

    /**
     * DELETE接口：删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        userService.deleteUserCache(id);
        return ResponseEntity.ok(ApiResult.success("删除成功", null));
    }

    // ==================== Redis 接口 ====================

    /**
     * GET接口：从MySQL读取数据并写入Redis
     */
    @GetMapping("/{id}/cache")
    public ResponseEntity<ApiResult<User>> cacheUserToRedis(@PathVariable Long id) {
        User user = userService.getUserByIdFromMysqlAndWriteToRedis(id);
        if (user == null) {
            return ResponseEntity.ok(ApiResult.error("用户不存在"));
        }
        return ResponseEntity.ok(ApiResult.success("已从MySQL读取并写入Redis", user));
    }

    /**
     * GET接口：从Redis读取数据
     */
    @GetMapping("/{id}/redis")
    public ResponseEntity<ApiResult<User>> getUserFromRedis(@PathVariable Long id) {
        User user = userService.getUserByIdFromRedis(id);
        if (user == null) {
            return ResponseEntity.ok(ApiResult.error("Redis缓存中不存在该用户，请先访问 /api/users/{id}/cache"));
        }
        return ResponseEntity.ok(ApiResult.success("从Redis读取成功", user));
    }

    // ==================== 统一响应格式 ====================

    public static class ApiResult<T> {
        private int code;
        private String message;
        private T data;

        public ApiResult() {}

        public ApiResult(int code, String message, T data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public static <T> ApiResult<T> success(T data) {
            return new ApiResult<>(200, "success", data);
        }

        public static <T> ApiResult<T> success(String message, T data) {
            return new ApiResult<>(200, message, data);
        }

        public static <T> ApiResult<T> error(String message) {
            return new ApiResult<>(500, message, null);
        }

        public int getCode() { return code; }
        public void setCode(int code) { this.code = code; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
    }
}
