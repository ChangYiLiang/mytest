package com.chyl.mytest.builder;

import com.chyl.mytest.builder.model.MessageDataBean;
import lombok.*;

/**
 * @Author: chyl
 * @Date: 2019/6/13 10:09
 */
@Builder
@Getter
@Setter
@ToString
public class MessageBuilderLombok {

    /***
     * openId
     */
    @Builder.Default
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

    MessageBuilderLombok(){}

    MessageBuilderLombok(String touser, String template_id, String url, MessageDataBean data) {
        this.touser = touser;
        this.template_id = template_id;
        this.url = url;
        this.data = data;
    }

    public static void main(String[] args) {
        MessageBuilderLombok build = MessageBuilderLombok.builder().template_id("123").url("456").build();
        System.out.println(build.toString());
        MessageBuilderLombok messageBuilderLombok = new MessageBuilderLombok();
        messageBuilderLombok.setUrl("456");
        messageBuilderLombok.setTemplate_id("123");
        System.out.println(messageBuilderLombok.toString());
    }
}
