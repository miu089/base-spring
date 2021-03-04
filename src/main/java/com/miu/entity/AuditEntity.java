package com.miu.entity;

import org.seasar.doma.Entity;

import java.time.LocalDateTime;

@Entity
public class AuditEntity {
  public LocalDateTime createdAt;
  public Integer createdBy;
  public LocalDateTime updatedAt;
  public Integer updatedBy;

}
