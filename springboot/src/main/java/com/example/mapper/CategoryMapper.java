package com.example.mapper;

import com.example.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: youMeng
 * @Date: 2025/3/7 - 03 - 07 - 16:52
 * @Description: com.example.mapper
 * @version: 1.0
 */
public interface CategoryMapper {

    List<Category> selectAll(Category category);

    void insert(Category category);

    void updateById(Category category);

    @Delete("delete from `category` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `category` where id = #{id}")
    Category selectById(Integer id);
}
