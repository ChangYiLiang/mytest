package com.chyl.mytest.builder.model;

import lombok.Data;

/**
 * @Author: chyl
 * @Date: 2019/6/5 15:34
 */
@Data
public class MessageDataBean {
    /**
     * 消息头
     */
    private Item first;

    /**
     * 关键字
     */
    private Item keyword1;

    /**
     * 关键字
     */
    private Item keyword2;

    /**
     * 关键字
     */
    private Item keyword3;

    /**
     * 关键字
     */
    private Item keyword4;

    /**
     * 关键字
     */
    private Item keyword5;

    /**
     * 备注
     */
    private Item remark;
}
