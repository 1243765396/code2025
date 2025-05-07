package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: youMeng
 * @Date: 2025/3/7 - 03 - 07 - 16:52
 * @Description: com.example.mapper
 * @version: 1.0
 */
public interface AdminMapper {

    List<Admin> selectAll(Admin admin);

    void insert(Admin admin);

    @Select("select * from `admin` where username = #{username}")
    Admin selectByUsername(String username);

    void updateById(Admin admin);

    @Delete("delete from `admin` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `admin` where id = #{id}")
    Admin selectById(String id);
}
