package com.miu.service;


import com.google.common.base.Strings;
import com.miu.dao.UserDao;
import com.miu.dao.UserRoleDao;
import com.miu.entity.AuthUser;
import com.miu.entity.User;
import com.miu.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ログイン認証するためのサービス
 * spring securityを利用
 */
@Service
public class CustomUserDetailsService extends MessageService implements UserDetailsService {
  @Autowired
  UserDao userDao;
  @Autowired
  UserRoleDao userRoleDao;

  @Override
  public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    if (Strings.isNullOrEmpty(loginId)) {
      throw new UsernameNotFoundException(addResultMessage("errors.login"));
    }

    User user = userDao.selectOneByLoginId(loginId);

    if (user == null) {
      throw new UsernameNotFoundException(addResultMessage("errors.login"));
    }

    if (StringUtils.isEmpty(user.password)) {
      throw new UsernameNotFoundException(addResultMessage("errors.login"));
    }

    // 権限設定
    List<GrantedAuthority> grantList = new ArrayList<>();
    List<UserRole> userRoleList = userRoleDao.selectByUserId(user.userId);
    for (UserRole userRole : userRoleList) {
      grantList.add(new SimpleGrantedAuthority(userRole.roleName.getName()));
    }

    return new AuthUser(user, grantList);

  }

}
