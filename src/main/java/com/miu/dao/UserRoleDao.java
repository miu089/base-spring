package com.miu.dao;

import com.miu.entity.UserRole;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@ConfigAutowireable
@Dao
public interface UserRoleDao {
  @Select
  List<UserRole> selectByUserId(Integer userId);

}
