package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HtmlUtil;
import com.example.entity.Account;
import com.example.entity.Category;
import com.example.entity.Introduction;
import com.example.entity.User;
import com.example.mapper.CategoryMapper;
import com.example.mapper.IntroductionMapper;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
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
public class IntroductionService {

    @Resource
    IntroductionMapper introductionMapper;
    @Resource
    CategoryMapper categoryMapper;
    @Resource
    UserMapper userMapper;

//    public String introduction(String name) {
//        if ("introduction".equals(name)) {
//            return "introduction";
//        }else {
//            throw new CustomerException("账号错误");
//        }
//    }

    public void add(Introduction introduction) {
        Account currentUser = TokenUtils.getCurrentUser();
        introduction.setUserId(currentUser.getId());
        introduction.setTime(DateUtil.now());
        introductionMapper.insert(introduction);
    }

    public void update(Introduction introduction) {
        introductionMapper.updateById(introduction);
    }

    public void deleteById(Integer id) {
        introductionMapper.deleteById(id);
    }

    public List<Introduction> selectAll(Introduction introduction) {
        List<Introduction> list = introductionMapper.selectAll(introduction);
        // list存储了原始数据只有categoryId
        for (Introduction dbIntroduction : list) {
            // 先拿到categoryId
            Integer categoryId = dbIntroduction.getCategoryId();
            Integer userId = dbIntroduction.getUserId();
            // 通过categoryId从category表里通过主键查询出分类数据
            Category category = categoryMapper.selectById(categoryId);
            User user = userMapper.selectById(userId.toString());
            if (ObjectUtil.isNotEmpty(category)) {
                // 把分类的title赋值给categoryTitle
                dbIntroduction.setCategoryTitle(category.getTitle());
            }
            if (ObjectUtil.isNotEmpty(user)) {
                // 把分类的name赋值给userName
                dbIntroduction.setUserName(user.getName());
            }
            if (ObjectUtil.isNotEmpty(dbIntroduction.getContent())){
                dbIntroduction.setDescription(HtmlUtil.cleanHtmlTag(dbIntroduction.getContent()));
            }
        }
        return list;
    }

    public PageInfo<Introduction> selectPage(Integer pageNum, Integer pageSize, Introduction introduction) {
        // 在service里获取当前的登录用户信息
        Account currentUser = TokenUtils.getCurrentUser();
        if ("USER".equals(currentUser.getRole())) {
             introduction.setUserId(currentUser.getId());
        }
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Introduction> list = introductionMapper.selectAll(introduction);
        // list存储了原始数据只有categoryId
        for (Introduction dbIntroduction : list) {
            // 先拿到categoryId
            Integer categoryId = dbIntroduction.getCategoryId();
            Integer userId = dbIntroduction.getUserId();
            // 通过categoryId从category表里通过主键查询出分类数据
            Category category = categoryMapper.selectById(categoryId);
            User user = userMapper.selectById(userId.toString());
            if (ObjectUtil.isNotEmpty(category)) {
                // 把分类的title赋值给categoryTitle
                dbIntroduction.setCategoryTitle(category.getTitle());
            }
            if (ObjectUtil.isNotEmpty(user)) {
                // 把分类的name赋值给userName
                dbIntroduction.setUserName(user.getName());
            }
        }
        return PageInfo.of(list);
    }

    public Introduction selectById(Integer id) {
        Introduction dbIntroduction = introductionMapper.selectById(id);
        // 先拿到categoryId
        Integer categoryId = dbIntroduction.getCategoryId();
        Integer userId = dbIntroduction.getUserId();
        // 通过categoryId从category表里通过主键查询出分类数据
        Category category = categoryMapper.selectById(categoryId);
        User user = userMapper.selectById(userId.toString());
        if (ObjectUtil.isNotEmpty(category)) {
            // 把分类的title赋值给categoryTitle
            dbIntroduction.setCategoryTitle(category.getTitle());
        }
        if (ObjectUtil.isNotEmpty(user)) {
            // 把分类的name赋值给userName
            dbIntroduction.setUserName(user.getName());
            dbIntroduction.setUserAvatar(user.getAvatar());
        }
        return dbIntroduction;
    }
}