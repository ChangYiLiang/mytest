package com.chyl.mytest.export.entity;

import com.alibaba.fastjson.annotation.JSONField;

import com.chyl.mytest.export.entity.vo.PhoneBookVO;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author chyl
 * @create 2018-06-07 下午3:40
 */
@Data
@Document(collection = "phoneBook")
public class PhoneBookDO {
    /***
     * 用户名
     */
    private String userName;
    /***
     * 身份证号码
     */
    private String cardId;
    /***
     * 电话号码
     */
    private String mobile;
    /***
     * 手机类型
     */
    private String phoneType;

    private List<PhoneBookVO> phoneBook;
}
