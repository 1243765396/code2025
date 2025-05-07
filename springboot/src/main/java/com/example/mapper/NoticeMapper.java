package com.example.mapper;

import com.example.entity.Notice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: youMeng
 * @Date: 2025/3/7 - 03 - 07 - 16:52
 * @Description: com.example.mapper
 * @version: 1.0
 */
public interface NoticeMapper {

    List<Notice> selectAll(Notice notice);

    void insert(Notice notice);

    void updateById(Notice notice);

    @Delete("delete from `notice` where id = #{id}")
    void deleteById(Integer id);
}
