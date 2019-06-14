package com.chyl.mytest.builder.model;

import lombok.Data;

/**
 * @Author: chyl
 * @Date: 2019/6/5 15:30
 */
@Data
public class MessageTemplateBO {
    /***
     * openId
     */
    private String touser = "123";

    /***
     * 模板id
     */
    private String template_id;

    /**
     * 跳转链接
     */
    private String url;

    /**
     * 消息数据
     */
    private MessageDataBean data;
}
