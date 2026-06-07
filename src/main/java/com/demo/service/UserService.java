package com.demo.service;

import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String USER_CACHE_PREFIX = "user:";
    private static final long CACHE_EXPIRE_MINUTES = 30;

    public User getUserById(Long id) {
        return userMapper.findById(id);
    }

    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    public User addUser(User user) {
        userMapper.insert(user);
        return user;
    }

    public User updateUser(User user) {
        userMapper.update(user);
        return user;
    }

    public boolean deleteUser(Long id) {
        userMapper.deleteById(id);
        return true;
    }

    /**
     * 从MySQL读取数据并写入Redis
     */
    public User getUserByIdFromMysqlAndWriteToRedis(Long id) {
        User user = userMapper.findById(id);
        if (user != null) {
            cacheUserToRedis(user);
        }
        return user;
    }

    /**
     * 从Redis读取数据
     */
    public User getUserByIdFromRedis(Long id) {
        String key = USER_CACHE_PREFIX + id;
        Object cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                return mapper.convertValue(cached, User.class);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * 将用户数据写入Redis缓存
     */
    private void cacheUserToRedis(User user) {
        String key = USER_CACHE_PREFIX + user.getId();
        redisTemplate.opsForValue().set(key, user, CACHE_EXPIRE_MINUTES, TimeUnit.MINUTES);
    }

    /**
     * 删除Redis缓存
     */
    public void deleteUserCache(Long id) {
        String key = USER_CACHE_PREFIX + id;
        redisTemplate.delete(key);
    }
}
