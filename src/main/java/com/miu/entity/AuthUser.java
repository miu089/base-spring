package com.miu.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * ログイン認証とユーザー情報保持の用途
 */
@Setter
@Getter
public class AuthUser extends User {
  public final Integer userId;
  public final String loginId;
  public final String userName;

  public AuthUser(com.miu.entity.User user,
                  Collection<? extends GrantedAuthority> authorities) {
    super(user.loginId, user.password, authorities);

    this.userId = user.userId;
    this.loginId = user.loginId;
    this.userName = user.userName;
  }


}
