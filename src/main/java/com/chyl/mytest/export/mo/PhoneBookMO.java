package com.chyl.mytest.export.mo;

import com.chyl.mytest.export.annotation.ExcelAttribute;
import com.chyl.mytest.export.annotation.ExcelElement;
import lombok.Data;

import java.util.List;

/**
 * @author chyl
 * @create 2018-08-28 上午10:53
 */
@Data
public class PhoneBookMO {

    @ExcelAttribute(title = "用户名",column = 0)
    private String userName;

    @ExcelAttribute(title = "身份证号码",column = 1)
    private String cardId;

    @ExcelAttribute(title = "电话号码",column = 2)
    private String mobile;

    @ExcelElement
    private List<PhoneBookListVO> phoneBook;
}
