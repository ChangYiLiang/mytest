package com.chyl.mytest.kafka.beans;

import lombok.Data;

import java.util.Date;

/**
 * @author chyl
 * @create 2018-06-06 下午6:55
 */
@Data
public class Message {
    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳

}
