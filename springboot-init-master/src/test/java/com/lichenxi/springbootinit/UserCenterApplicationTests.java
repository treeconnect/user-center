package com.lichenxi.springbootinit;

import cn.hutool.core.lang.Assert;
import com.lichenxi.springbootinit.mapper.UserMapper;
import com.lichenxi.springbootinit.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@SpringBootTest
class UserCenterApplicationTests {
    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(0==userList.size(),"");
        userList.forEach(System.out::println);
    }

    @Test
    void testDigest(){
        String newPassword = DigestUtils.md5DigestAsHex(("lichenxi"+"mypassword").getBytes());
        System.out.println(newPassword);
    }
}
