package com.miu.entity;


import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;


@Entity
public class TestTable extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer testTableId;
  public String name;

}
