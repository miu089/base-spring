package com.miu.entity;

import com.miu.namedvalue.UserRoleName;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;

@Entity
public class UserRole extends AuditEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer userRoleId;
  public Integer userId;
  public UserRoleName roleName;

}
