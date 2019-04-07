package com.chyl.mytest.export.mo;

import com.chyl.mytest.export.annotation.ExcelAttribute;
import lombok.Data;

/**
 * @author chyl
 * @create 2018-08-28 下午3:34
 */
@Data
public class BookListVO {

    @ExcelAttribute(title = "联系人电话",column = 4)
    private String phoneNumberList;
}
