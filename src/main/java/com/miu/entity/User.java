package com.miu.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

@Entity
public class User extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer userId;
  public String loginId;
  public String password;
  public String userName;

}
