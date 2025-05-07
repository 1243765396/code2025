package com.example.service;

import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: youMeng
 * @Date: 2025/3/6 - 03 - 06 - 21:41
 * @Description: com.example.service
 * @version: 1.0
 */

@Service
public class CategoryService {

    @Resource
    CategoryMapper categoryMapper;

//    public String category(String name) {
//        if ("category".equals(name)) {
//            return "category";
//        }else {
//            throw new CustomerException("账号错误");
//        }
//    }

    public void add(Category category) {
        categoryMapper.insert(category);
    }

    public void update(Category category) {
        categoryMapper.updateById(category);
    }

    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    public List<Category> selectAll(Category category) {
        return categoryMapper.selectAll(category);
    }

    public PageInfo<Category> selectPage(Integer pageNum, Integer pageSize, Category category) {
        // 在service里获取当前的登录用户信息
        // Account currentUser = TokenUtils.getCurrentUser();
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }
}