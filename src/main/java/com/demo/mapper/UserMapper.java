package com.demo.mapper;

import com.demo.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Insert("INSERT INTO user(username, password, nickname, email, status) " +
            "VALUES(#{username}, #{password}, #{nickname}, #{email}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE user SET username=#{username}, password=#{password}, " +
            "nickname=#{nickname}, email=#{email}, status=#{status} WHERE id=#{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
}
