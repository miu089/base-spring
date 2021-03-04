package com.miu.provider;

import com.google.common.base.Strings;
import com.miu.entity.AuthUser;
import com.miu.service.CustomUserDetailsService;
import com.miu.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 認証の処理自体を独自に行いたいので拡張している
 */
@Component
@Slf4j
public class CustomeAuthenticationProvider extends MessageService implements AuthenticationProvider {
  @Autowired
  CustomUserDetailsService customUserDetailService;

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    String userId = auth.getName();
    String password = auth.getCredentials().toString();

    if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(password)) {
      throw new AuthenticationCredentialsNotFoundException(addResultMessage("errors.login"));
    }

    // パスワード照合
    AuthUser user = (AuthUser) customUserDetailService.loadUserByUsername(userId);

    if (user == null) {
      throw new AuthenticationCredentialsNotFoundException(addResultMessage("errors.login"));
    }

    // 平文認証 XXX: 開発の確認用にひとまず
    if (!user.getPassword().equals(password)) {
      throw new AuthenticationCredentialsNotFoundException(addResultMessage("errors.login"));
    }

    return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> token) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(token);
  }


}
