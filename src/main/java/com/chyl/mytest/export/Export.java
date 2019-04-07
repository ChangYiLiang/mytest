package com.chyl.mytest.export;

import com.chyl.mytest.export.mo.PhoneBookMO;
import com.chyl.mytest.util.DateUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chyl
 * @create 2018-08-28 上午11:17
 */
public class Export {

    public void export(List<PhoneBookMO> phoneBookMOList, String path, String fileName) {
        FileOutputStream fileOutputStream;
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        String filePath = path + fileName + ".xlsx";
        try {
            fileOutputStream = new FileOutputStream(filePath);
            new ExportMergeExcel(fileName, PhoneBookMO.class).setDataList(phoneBookMOList).write(fileOutputStream).dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
