package com.chyl.mytest.export.repo;

import com.chyl.mytest.export.repo.DO.MessageDO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Beldon
 * @create 2017-12-20 下午5:10
 */
public interface MessageAutoRepo extends MongoRepository<MessageDO, String> {

}
