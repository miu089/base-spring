package com.miu.custom;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionExpiredDetectingLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

  public SessionExpiredDetectingLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
    super(loginFormUrl);
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    super.commence(request, response, authException);
  }

  private PortResolver portResolver = new PortResolverImpl();

  @Override
  protected String buildRedirectUrlToLoginPage(HttpServletRequest request,
                                               HttpServletResponse response,
                                               AuthenticationException authException) {
    // セッションタイムアウト時にリダイレクト先を決定する処理
    String redirectUrl = super.buildRedirectUrlToLoginPage(request, response, authException);

    if (isRequestedSessionInvalid(request)) {
      redirectUrl += redirectUrl.contains("?") ? "&" : "?";
      redirectUrl += "timeout";
    }

    return redirectUrl;
  }

  private boolean isRequestedSessionInvalid(HttpServletRequest request) {
    return request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
  }


}
