package com.chyl.mytest.export.mo;

import com.chyl.mytest.export.annotation.ExcelAttribute;
import com.chyl.mytest.export.annotation.ExcelElement;
import lombok.Data;

import java.util.List;

/**
 * @author chyl
 * @create 2018-08-28 上午10:58
 */
@Data
public class PhoneBookListVO {

    @ExcelAttribute(title = "联系人姓名",column = 3)
    private String name;

    @ExcelAttribute(title = "通话次数",column = 5)
    private String timesContacted;

    @ExcelAttribute(title = "联系人电话",column = 4)
    private String phoneNumberList;

    @ExcelAttribute(title = "最后通话时间",column = 6)
    private Long lastTimeContacted;

}
