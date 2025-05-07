package com.example.mapper;

import com.example.entity.Apply;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * @Auther: youMeng
 * @Date: 2025/3/7 - 03 - 07 - 16:52
 * @Description: com.example.mapper
 * @version: 1.0
 */
public interface ApplyMapper {

    List<Apply> selectAll(Apply apply);

    void insert(Apply apply);

    void updateById(Apply apply);

    @Delete("delete from `apply` where id = #{id}")
    void deleteById(Integer id);
}
