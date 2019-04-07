package com.chyl.mytest.export.repo.DO;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Tomdy
 * @create 2017-12-12 11:27
 */
@Data
@Document(collection = "apply")
public class ApplyDO {

    /***信用认证状态*/
    public static final String AUTHORIZE_SUCCESS = "success";
    public static final String AUTHORIZE_FAIL = "fail";

    /***申请状态:0-申请；1-审核中；2-申请通过；3-拒绝；4-取消*/
    public static final Integer UN_APPLIED = 0;
    public static final Integer APPLYING = 1;
    public static final Integer APPLY_SUCCESS = 2;
    public static final Integer APPLY_FAIL = 3;
    public static final Integer APPLY_CANCEL = 4;

    /***进件申请步骤：<=10未实名、11银行卡、12个人资料、13已提交*/
    public static final Integer STEP_REAL_NAME = 10;
    public static final Integer STEP_BANK_CARD = 11;
    public static final Integer STEP_USER_INFO = 12;
    public static final Integer STEP_APPLY_SUBMIT = 13;

    /**
     * 用户ID
     */
    @Indexed
    private String userId = "";
    /**
     * 申请状态:0：申请；1：审核中；2：申请通过；3：拒绝；4.取消
     */
    @Indexed
    private Integer status = 0;
    /***
     * 提交状态：0-未提交 1-提交中 2-提交成功
     */
    @Indexed
    private Integer submitStatus;
    /**
     * 婚姻状况：1：未婚；2：已婚；3：离异
     */
    private Integer married;
    /**
     * 学历
     */
    private String degree = "";
    /**
     * 定位居住地
     */
    private String address1 = "";
    /**
     * 手写详细地址
     */
    private String address2 = "";
    /**
     * 地址编码：00省000市0000区
     */
    private String addressCode = "";
    /**
     * 公司名称
     */
    private String company = "";
    /**
     * 公司类型
     */
    private String companyType = "";
    /**
     * 公司所属行业
     */
    private String industry = "";
    /**
     * 行业编码
     */
    private String industryCode = "";
    /**
     * 公司电话
     */
    private String companyTelephone = "";
    /**
     * 公司地址
     */
    private String companyAddress1 = "";
    /**
     * 公司地址详细
     */
    private String companyAddress2 = "";
    /**
     * 部门
     */
    private String department = "";
    /**
     * 职位
     */
    private String position = "";
    /**
     * 职位编码
     */
    private String positionCode = "";
    /**
     * 入职时间
     */
    private Long entryDate;
    /**
     * 每月收入
     */
    private Double monthIncome;
    /**
     * 联系人姓名1
     */
    private String contactName1 = "";
    /**
     * 联系人手机号1
     */
    private String mobile1;
    /**
     * 与联系人关系1
     */
    private String relation1;
    /**
     * 联系人姓名2
     */
    private String contactName2 = "";
    /**
     * 联系人手机号2
     */
    private String mobile2;
    /**
     * 与联系人关系2
     */
    private String relation2;
    /**
     * 银行卡
     */
    private String bankCard = "";
    /**
     * 银行卡代码
     */
    private String bankCode = "";
    /**
     * 银行卡预留手机号
     */
    private String bankPhone = "";
    /**
     * 身份证正面
     */
    private String cardIdImg1 = "";
    /**
     * 身份证反面
     */
    private String cardIdImg2 = "";
    /**
     * 工作证明1
     */
    private String workImg1 = "";
    /**
     * 工作证明2
     */
    private String workImg2 = "";
    /**
     * 银行卡正面
     */
    private String incomeImg1 = "";
    /**
     * 消金合同号
     */
    private String contractNo = "";
    /**
     * 消金申请编号
     */
    private String applicationNo = "";
    /**
     * 消金备注
     */
    private Long applyMemo;
    /**
     * 进件申请步骤：<=10未实名、11银行卡、12个人资料、13已提交
     */
    private Integer step;
    /**
     * 发薪日
     */
    private String salaryDay = "";
    /**
     * 工作类型
     */
    private String workType = "";
    /**
     * 上一次提交的申请号,没有则为空
     */
    private String flag = "";
    /***
     * 同盾指纹设备字符串
     */
    private String blackBox;
    /***
     * 我来贷设备指纹
     */
    private String deviceId;
    /***
     * 来源
     */
    private String source;
    /****
     * 信用认证状态
     */
    private String authorizeStatus;
    /****
     * 风控key
     */
    private String riskControlKey;
    /***
     * IP信息
     */
    private String ip;
    /***
     * GPS信息 格式：维度，精度
     */
    private String gps;

    @Id
    protected String id;

    private String createdBy;
    private long createdTime;
    private String updatedBy;
    private long updatedTime;

    @Version
    private Integer version;
}
