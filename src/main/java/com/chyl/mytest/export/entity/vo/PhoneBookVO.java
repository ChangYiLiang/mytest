package com.chyl.mytest.export.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author chyl
 * @create 2018-06-07 下午3:52
 */
@Data
public class PhoneBookVO {
    /***
     * 姓名
     */
    private String name;

    /***
     *  电话号码
     */
    private List<String> phoneNumberList;

    /***
     * 最后通话时间
     */
    private Long lastTimeContacted;

    /***
     * 通话次数
     */
    private String timesContacted;

    /***
     * 首次记录时间
     */
    private String firstRecordTime;

    /***
     * 最后记录修改时间
     */
    private String lastRecordTime;
}
