package com.chyl.mytest.export.repo.DO;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Tomdy
 * @create 2017-12-11 10:05
 */
@Data
@Document(collection = "user")
public class UserDO {

    /***是否是员工: 1-是 0-否*/
    public static final Integer IS_FOXCONN_TRUE = 1;
    public static final Integer IS_FOXCONN_FALSE = 0;

    /***注销状态: 1-已注销 2-注销中*/
    public static final Integer DELETE_STATUS_DELETED = 1;
    public static final Integer DELETE_STATUS_DELETING = 2;

    /**
     * 用户id
     */
    @Indexed(unique = true)
    private String userId;
    /**
     * 手机号码
     */
    @Indexed(unique = true)
    private String mobile;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 支付密码
     */
    private String payPassword;
    /**
     * 是否为富士康员工
     */
    private Integer isFoxconn;

    /***
     * 员工工号
     */
    @Indexed
    private String employeeNo;

    /***
     * 资位
     */
    private String jobClass;

    /***
     * 事业群
     */
    private String bgName;

    /***
     * 次集团
     */
    private String sjtName;

    /***
     * 厂区
     */
    private String facName;

    /***
     * 用户类型 : 0普通用户 1荣超用户 2非富转富用户 3公租房白名单用户
     */
    private Integer type = 0;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 拼音名字
     */
    private String pname;
    /**
     * 身份证号
     */
    @Indexed
    private String cardId;
    /**
     * 性别 : 默认 0未知 1男 2女；
     */
    private Integer sex = 0;
    /***
     * 生日
     */
    private Long birthday;
    /**
     * 证件类型: 0:身份证 1:户口簿 2:护照 3:军官证 4:士兵证 5:港澳居民来往内地通行证 6:台湾同胞来往内地通行证 7:临时身份证 8:外国人居留证 9:警官证 X:其他证件
     */
    private String certifTp;
    /**
     * 证件号码
     */
    private String certifId;
    /**
     * 注册来源
     */
    private String target;
    /**
     * 是否锁定
     */
    private boolean lock;
    /**
     * 已经登陆错误次数
     */
    private Long loginTimes = 0L;
    /**
     * 锁定时间
     */
    private Long lockTime = 0L;

    /**
     * 时时薪推送状态：0-未推送 1-推送中 2-推送成功
     */
    private Integer pushStatus = 0;
    /**
     * 活体认证状态: 0:未活体认证,1:已活体认证
     */
    private Integer faceVerifyStatus;
    /**
     * 注册渠道
     */
    private String registChannel;
    /**
     * 申请富宝袋渠道
     */
    private String applyChannel;
    /***
     * 注销状态： 1-已注销 2-注销中
     */
    private Integer deleteStatus;
}
