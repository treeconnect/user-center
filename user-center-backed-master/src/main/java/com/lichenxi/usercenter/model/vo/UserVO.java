package com.lichenxi.usercenter.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户视图（脱敏）
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class UserVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 用户昵称
     */
    @JsonProperty("username")
    private String userName;

    /**
     * 用户头像
     */
    @JsonProperty("avatarUrl")
    private String userAvatar;

    /**
     * 用户角色：0-普通用户 1-管理员 2-封号
     */
    private Integer userRole;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}