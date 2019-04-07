package com.chyl.mytest.export.repo;

import com.chyl.mytest.export.repo.DO.UserDO;

import java.util.List;
import java.util.Set;

/**
 * @author chyl
 * @create 2019-01-29 6:15 PM
 */
public interface UserRepo {
    List<UserDO> findByCardId(Set<String> ids);
}
