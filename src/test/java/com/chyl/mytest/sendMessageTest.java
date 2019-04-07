package com.chyl.mytest;

import com.chyl.mytest.export.repo.DO.MessageDO;
import com.chyl.mytest.export.repo.DO.UserDO;
import com.chyl.mytest.export.repo.MessageAutoRepo;
import com.chyl.mytest.export.repo.UserRepo;
import com.chyl.mytest.util.DateUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chyl
 * @create 2019-01-29 6:02 PM
 */
public class sendMessageTest extends BaseTest{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageAutoRepo messageAutoRepo;

    @Test
    public void sendMessage() {
        List<UserDO> list = userRepo.findByCardId(loadCardIds());
        List<MessageDO> messageDOS = new ArrayList<>();
        long time = DateUtil.getUnixTime();
        list.forEach(userDO -> addMessage2(messageDOS, userDO, time));
        messageAutoRepo.saveAll(messageDOS);
    }

    private void addMessage(List<MessageDO> messageDOS, UserDO userDO, long time) {
        MessageDO messageDO = new MessageDO();
        messageDO.setContentType(2);
        messageDO.setStatus(MessageDO.STATUS_UNREADED);
        messageDO.setContent("尊敬的富宝袋用户：您好！配合集团春节放假安排，富宝袋将在发薪日对2月还款进行代扣（若已还款请忽略）。为您送上一张优惠券，请至【我的优惠券】领取。给您带来不便，敬请谅解。如需帮助，可咨询在线客服或拨打400-930-3606。");
        messageDO.setPush(false);
        messageDO.setSenderId("0");
        messageDO.setTitle("扣款提示");
        messageDO.setType(MessageDO.TYPE_NOTIFY);
        messageDO.setUserId(userDO.getUserId());
        messageDO.setCreatedTime(time);
        messageDO.setUpdatedTime(time);
        messageDOS.add(messageDO);
    }

    private void addMessage2(List<MessageDO> messageDOS, UserDO userDO, long time) {
        MessageDO messageDO = new MessageDO();
        messageDO.setContentType(2);
        messageDO.setStatus(MessageDO.STATUS_UNREADED);
        messageDO.setContent("尊敬的富宝袋用户：您好！配合集团春节放假安排，富宝袋将在发薪日对2月还款进行代扣（若已还款请忽略）。给您带来不便，敬请谅解。如需要帮助，可咨询在线客服或拨打400-930-3606。");
        messageDO.setPush(false);
        messageDO.setSenderId("0");
        messageDO.setTitle("扣款提示");
        messageDO.setType(MessageDO.TYPE_NOTIFY);
        messageDO.setUserId(userDO.getUserId());
        messageDO.setCreatedTime(time);
        messageDO.setUpdatedTime(time);
        messageDOS.add(messageDO);
    }

    private Set<String> loadCardIds() {
        Set<String> cds = new HashSet<>();
        File source = new File("./card2.txt");
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
