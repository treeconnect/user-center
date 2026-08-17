package com.lichenxi.springbootinit.service;

import javax.annotation.Resource;

import com.lichenxi.springbootinit.exception.BusinessException;
import com.lichenxi.springbootinit.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        String userAccount = "lichen";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String planetCode = "1";

        // 第一次：新账号，应注册成功，返回正数 id
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertTrue(result > 0);

        // 第二次：相同账号，应抛 BusinessException（账号重复）
        Assertions.assertThrows(BusinessException.class, () ->
                userService.userRegister(userAccount, userPassword, checkPassword, planetCode));
    }

    @Test
    void testAddUser(){
        User user =new User();
        user.setUserName("lichenxi");
        user.setUserAccount("123");
        user.setUserAvatar("https://img2.baidu.com/it/u=731676038,827449205&fm=253&app=138&f=JPEG?w=562&h=417");
        user.setGender(0);
        user.setUserPassword("×××");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }
}
