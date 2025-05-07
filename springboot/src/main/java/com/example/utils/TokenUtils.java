package com.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * @Auther: youMeng
 * @Date: 2025/4/9 - 04 - 09 - 19:49
 * @Description: com.example.utils
 * @version: 1.0
 */
@Component
public class TokenUtils {

    @Resource
    AdminService adminService;
    @Resource
    UserService userService;

    static AdminService staticAdminService;
    static UserService staticUserService;

    // springboot工程启动后会加载这段代码
    @PostConstruct
    public void init() {
        staticAdminService = adminService;
        staticUserService = userService;
    }


    /**
     * 生成token
     */
    public static String createToken(String data, String sign) {
        return JWT.create().withAudience(data) // 将 user-role 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetDay(new Date(), 1)) // 1天后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥,HMAC256算法加密
    }

    /**
     * 获取当前登录的用户信息
     */
    // 在service里获取当前的登录用户信息
    // Account currentUser = TokenUtils.getCurrentUser();
    public static Account getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 拿到token的载荷数据
        String audience = JWT.decode(token).getAudience().get(0);
        String[] split = audience.split("-");
        String userId = split[0];
        String role = split[1];
        if ("ADMIN".equals(role)) {
            return  staticAdminService.selecById(userId);
        } else if ("USER".equals(role)) {
            return  staticUserService.selecById(userId);
        }
        return null;
    }
}
