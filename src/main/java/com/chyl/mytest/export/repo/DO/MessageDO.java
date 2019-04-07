package com.chyl.mytest.export.repo.DO;

import lombok.Data;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Tomdy
 * @create 2017-12-12 15:43
 */
@Data
@Document(collection = "message")
@CompoundIndexes({
        @CompoundIndex(name = "user_status_code_idx", def = "{'userId': 1, 'status':1, 'messageCode':1}"),
        @CompoundIndex(name = "user_status_type_code_idx", def = "{'userId': 1, 'status':1,'type': 1, 'messageCode':1}")
})
public class MessageDO {

    public static final Integer STATUS_UNREADED = 0;
    public static final Integer STATUS_READED = 1;
    public static final Integer STATUS_DELETE = 2;
    public static final Integer STATUS_SEND_SUCCESS = 3;
    public static final Integer STATUS_SEND_FAIL = 4;

    public static final Integer TYPE_NOTIFY = 1;
    public static final Integer TYPE_ACTIVITY = 2;
    public static final Integer TYPE_SMS = 3;
    public static final Integer TYPE_WECHAT = 4;

    /**
     * 收件人ID
     */
    @Indexed
    private String userId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 发送人ID。默认为0：系统
     */
    private String senderId;
    /**
     * 站内信编码
     */
    private String messageCode;
    /**
     * 类型: 1-通知消息,2-活动消息,3-短信,4微信
     */
    private Integer type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 内容类型,0:html链接,1:富文本,2:文本
     */
    private Integer contentType;
    /**
     * 状态。默认为0:未读,1:已读,2:删除,3:发送成功,4:发送失败
     */
    private Integer status;
    /**
     * 是否推送
     */
    private Boolean push;
    /**
     * 标签
     */
    private Integer label;
    /**
     * 描述
     */
    private String desc;
    /**
     * 唯一短信标识,由智语平台生成(32位)
     */
    private String smsId;

    private String createdBy;
    private long createdTime;
    private String updatedBy;
    private long updatedTime;
    @Version
    private Integer version;
}
