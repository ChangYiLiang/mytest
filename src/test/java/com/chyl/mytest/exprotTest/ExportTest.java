package com.chyl.mytest.exprotTest;

import com.chyl.mytest.BaseTest;
import com.chyl.mytest.export.Export;
import com.chyl.mytest.export.entity.PhoneBookDO;
import com.chyl.mytest.export.entity.vo.PhoneBookVO;
import com.chyl.mytest.export.mo.PhoneBookListVO;
import com.chyl.mytest.export.mo.PhoneBookMO;
import com.chyl.mytest.export.repo.PhoneBookAutoRepo;
import com.chyl.mytest.util.DateUtil;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chyl
 * @create 2018-08-28 下午1:37
 */
public class ExportTest extends BaseTest {

    private static String FILE_PATH = "./cardId.txt";

    @Autowired
    private PhoneBookAutoRepo phoneBookAutoRepo;

    @Test
    public void export() {
        List<PhoneBookDO> phoneBookDOList = phoneBookAutoRepo.findByCardIdIsIn(loadCardIds());
        List<PhoneBookMO> phoneBookMOList = new ArrayList<>();

        phoneBookDOList.forEach(phoneBookDO -> {
            PhoneBookMO phoneBookMO = new PhoneBookMO();
            BeanUtils.copyProperties(phoneBookDO, phoneBookMO, "phoneBook");
            List<PhoneBookVO> phoneBook = phoneBookDO.getPhoneBook();
            List<PhoneBookListVO> phoneBooks = new ArrayList<>();
            phoneBook.forEach(phoneBookVO -> {
                PhoneBookListVO phoneBookListVO = new PhoneBookListVO();
                BeanUtils.copyProperties(phoneBookVO, phoneBookListVO);
                if (!CollectionUtils.isEmpty(phoneBookVO.getPhoneNumberList())) {
                    phoneBookListVO.setPhoneNumberList(phoneBookVO.getPhoneNumberList().toString());
                }
                phoneBooks.add(phoneBookListVO);
            });
            phoneBookMO.setPhoneBook(phoneBooks);
            phoneBookMOList.add(phoneBookMO);
        });
//        for (PhoneBookDO phoneBookDO : phoneBookDOList) {
//            PhoneBookMO phoneBookMO = new PhoneBookMO();
//            BeanUtils.copyProperties(phoneBookDO, phoneBookMO, "phoneBook");
//            List<PhoneBookListVO> phoneBooks = new ArrayList<>();
//            for (PhoneBookVO phoneBookVO : phoneBookDO.getPhoneBook()) {
//                PhoneBookListVO phoneBookListVO = new PhoneBookListVO();
//                BeanUtils.copyProperties(phoneBookVO, phoneBookListVO);
//                phoneBookListVO.setPhoneNumberList(phoneBookVO.getPhoneNumberList().toString());
//                phoneBooks.add(phoneBookListVO);
//            }
//            phoneBookMO.setPhoneBook(phoneBooks);
//            phoneBookMOList.add(phoneBookMO);
//        }
        Export export = new Export();
        String fileName = DateUtil.getCurrentDate("yyyy-MM-dd") + "催收名单";
        String path = "/data/phoneBook/";
        export.export(phoneBookMOList, path, fileName);
    }


    private List<String> loadCardIds() {
        List<String> cds = new ArrayList<>();
        File source = new File(FILE_PATH);
        String id;
        try {
            FileReader fileReader = new FileReader(source);
            BufferedReader br = new BufferedReader(fileReader);
            while (StringUtils.hasText(id = br.readLine())) {
                cds.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cds;
    }
}
