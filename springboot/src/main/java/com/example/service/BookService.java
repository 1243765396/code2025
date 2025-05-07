package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Book;
import com.example.exception.CustomerException;
import com.example.mapper.BookMapper;
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
public class BookService {

    @Resource
    BookMapper bookMapper;

//    public String book(String name) {
//        if ("book".equals(name)) {
//            return "book";
//        }else {
//            throw new CustomerException("账号错误");
//        }
//    }

    public void add(Book book) {
        bookMapper.insert(book);
    }

    public void update(Book book) {
        bookMapper.updateById(book);
    }

    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

    public List<Book> selectAll(Book book) {
        return bookMapper.selectAll(book);
    }

    public PageInfo<Book> selectPage(Integer pageNum, Integer pageSize, Book book) {
        // 在service里获取当前的登录用户信息
        // Account currentUser = TokenUtils.getCurrentUser();
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Book> list = bookMapper.selectAll(book);
        return PageInfo.of(list);
    }
}