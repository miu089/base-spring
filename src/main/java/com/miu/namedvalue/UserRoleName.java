package com.miu.namedvalue;

public enum UserRoleName {
  ROLE_ADMIN("ROLE_ADMIN"), // 管理者
  ROLE_USER("ROLE_USER"),   // ユーザー
  ;

  private String name;

  UserRoleName(String name) {
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

}
