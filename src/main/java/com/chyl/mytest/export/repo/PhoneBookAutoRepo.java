package com.chyl.mytest.export.repo;


import com.chyl.mytest.export.entity.PhoneBookDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author chyl
 * @create 2018-06-07 下午5:20
 */
public interface PhoneBookAutoRepo extends MongoRepository<PhoneBookDO, String> {
    PhoneBookDO findFirstByCardId(String cardId);

    List<PhoneBookDO> findByCardIdIsIn(List<String > cardIdList);
}
