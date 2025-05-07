package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: youMeng
 * @Date: 2025/3/7 - 03 - 07 - 16:52
 * @Description: com.example.mapper
 * @version: 1.0
 */
public interface UserMapper {

    List<User> selectAll(User user);

    void insert(User user);

    @Select("select * from `user` where username = #{username}")
    User selectByUsername(String username);

    void updateById(User user);

    @Delete("delete from `user` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `user` where id = #{id}")
    User selectById(String id);
}
