package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Notice;
import com.example.exception.CustomerException;
import com.example.mapper.NoticeMapper;
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
public class NoticeService {

    @Resource
    NoticeMapper noticeMapper;

//    public String notice(String name) {
//        if ("notice".equals(name)) {
//            return "notice";
//        }else {
//            throw new CustomerException("账号错误");
//        }
//    }

    public void add(Notice notice) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser != null && "ADMIN".equals(currentUser.getRole())) {
            // 设置公告时间
            notice.setTime(DateUtil.now());
            noticeMapper.insert(notice);
        }
        else {
            throw new CustomerException("500", "您的角色暂无权限执行该操作");
        }
    }

    public void update(Notice notice) {
        noticeMapper.updateById(notice);
    }

    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }

    public List<Notice> selectAll(Notice notice) {
        return noticeMapper.selectAll(notice);
    }

    public PageInfo<Notice> selectPage(Integer pageNum, Integer pageSize, Notice notice) {
        // 在service里获取当前的登录用户信息
        // Account currentUser = TokenUtils.getCurrentUser();
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = noticeMapper.selectAll(notice);
        return PageInfo.of(list);
    }
}