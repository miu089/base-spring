package com.miu.dao;

import com.miu.entity.User;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@ConfigAutowireable
@Dao
public interface UserDao {
  @Select
  User selectOneByLoginId(String loginId);

}
