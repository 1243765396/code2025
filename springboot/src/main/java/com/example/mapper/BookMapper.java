package com.example.mapper;

import com.example.entity.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: youMeng
 * @Date: 2025/3/7 - 03 - 07 - 16:52
 * @Description: com.example.mapper
 * @version: 1.0
 */
public interface BookMapper {

    List<Book> selectAll(Book book);

    void insert(Book book);

    void updateById(Book book);

    @Delete("delete from `book` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `book` where id = #{id}")
    Book selectById(Integer id);
}
