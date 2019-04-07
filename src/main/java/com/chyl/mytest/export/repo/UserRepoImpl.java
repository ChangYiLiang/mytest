package com.chyl.mytest.export.repo;

import com.chyl.mytest.export.repo.DO.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author chyl
 * @create 2019-01-29 6:16 PM
 */
@Repository
public class UserRepoImpl implements UserRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<UserDO> findByCardId(Set<String> ids) {
        Criteria criteria = Criteria.where("cardId").in(ids);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.project("userId"));
        AggregationResults<UserDO> aggregate = mongoTemplate.aggregate(aggregation, "user", UserDO.class);
        return aggregate.getMappedResults();
    }
}
