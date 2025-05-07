package com.example.mapper;

import com.example.entity.Record;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * @Auther: youMeng
 * @Date: 2025/3/7 - 03 - 07 - 16:52
 * @Description: com.example.mapper
 * @version: 1.0
 */
public interface RecordMapper {

    List<Record> selectAll(Record record);

    void insert(Record record);

    void updateById(Record record);

    @Delete("delete from `record` where id = #{id}")
    void deleteById(Integer id);
}
