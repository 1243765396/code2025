package com.example.common;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.exception.CustomerException;
import com.example.service.AdminService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Auther: youMeng
 * @Date: 2025/4/9 - 04 - 09 - 20:15
 * @Description: com.example.common
 * @version: 1.0
 */
@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.从http请求的header中获取token
        String token = request.getHeader("token");
        if (ObjectUtil.isEmpty(token)) {
            // 如果没拿到,从参数里再拿一次
            token = request.getParameter("token");
        }
        // 2.开始执行认证
        if (StrUtil.isBlank(token)) {
            throw new CustomerException("401", "您无权限操作");
        }

        Account account = null;

        try {
            // 拿到token的载荷数据
            String audience = JWT.decode(token).getAudience().get(0);
            String[] split = audience.split("-");
            String userId = split[0];
            String role = split[1];
            if ("ADMIN".equals(role)) {
                account = adminService.selecById(userId);
            } else if ("USER".equals(role)) {
                account = userService.selecById(userId);
            }
        } catch (Exception e) {
            throw new CustomerException("401", "您无权限操作");
        }
        if (account == null) {
            throw new CustomerException("401", "您无权限操作");
        }

        try {
            // 验证签名
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new CustomerException("401", "您无权限操作");
        }
        return true;
    }
}
