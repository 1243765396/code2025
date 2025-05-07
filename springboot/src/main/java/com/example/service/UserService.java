package com.example.service;

import cn.hutool.core.util.StrUtil;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.exception.CustomerException;
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
public class UserService {

    @Resource
    UserMapper userMapper;

//    public String user(String name) {
//        if ("user".equals(name)) {
//            return "user";
//        }else {
//            throw new CustomerException("账号错误");
//        }
//    }

    public void add(User user) {
        // 根据新的账号查询是否存在
        User dbuser = userMapper.selectByUsername(user.getUsername());
        if (dbuser != null) {
            throw new CustomerException("账号重复");
        }
        // 默认密码
        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword("123");
        }
        if (StrUtil.isBlank(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setRole("USER");
        userMapper.insert(user);
    }

    public void update(User user) {
        userMapper.updateById(user);
    }

    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    public void deleteBatch(List<User> list) {
        for (User user : list) {
            this.deleteById(user.getId());
        }
    }

    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    public PageInfo<User> selectPage(Integer pageNum, Integer pageSize, User user) {
        // 在service里获取当前的登录用户信息
        // Account currentUser = TokenUtils.getCurrentUser();
        // 开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    public User login(Account account) {
        // 验证账号是否存在
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (dbUser == null) {
            throw new CustomerException("账号不存在");
        }
        // 验证密码是否正确
        if (!dbUser.getPassword().equals(account.getPassword())) {
            throw new CustomerException("账号或密码错误");
        }
        // 创建token并返回给前端
        String token = TokenUtils.createToken(dbUser.getId() + "-" + "USER", dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public void register(User user) {
        this.add(user);
    }

    public User selecById(String id) {
        return userMapper.selectById(id);
    }

    public void updatePassword(Account account) {
        if (!account.getNewPassword().equals(account.getNew2Password())) {
            throw new CustomerException("500", "两次输入的密码不一致");
        }
        Account currentUser = TokenUtils.getCurrentUser();
        if (!account.getPassword().equals(currentUser.getPassword())) {
            throw new CustomerException("500", "原密码输入错误");
        }
        User user = userMapper.selectById(currentUser.getId().toString());
        user.setPassword(account.getNewPassword());
        userMapper.updateById(user);
    }
}