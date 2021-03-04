package com.miu.dao;

import com.miu.entity.TestTable;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@ConfigAutowireable
@Dao
public interface TestTableDao {
  @Select
  List<TestTable> selectAll();

}
