package com.miu.config;

import com.miu.custom.SessionExpiredDetectingLoginUrlAuthenticationEntryPoint;
import com.miu.namedvalue.UserRoleName;
import com.miu.provider.CustomeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  CustomeAuthenticationProvider authenticationProvider;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers(
        "/static/**",
        "/css/**",
        "/js/**",
        "/fonts/**",
        "/images/**",
        "/webjars/bootstrap/**",
        "/webjars/jquery/**",
        "/webjars/font-awesome/**"
    );
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeRequests()
        .mvcMatchers("/admin/**")
        // 管理権限のみアクセス可能なURL(権限はCustomUserDetailsServiceで設定)
        .hasAnyAuthority(
            UserRoleName.ROLE_ADMIN.getName()
        )
        .antMatchers("/version/version").permitAll() // バージョン確認用ページは全員許可
        .anyRequest()
        .authenticated()
    ;

    // apiのcsrfトークンは無視したいときの設定
    // httpSecurity.csrf().ignoringAntMatchers("/api/**");

    // 自分のコンテンツはiframeで読み込み許可する
    httpSecurity.headers().frameOptions().sameOrigin();

    httpSecurity.formLogin()
        .loginPage("/login").permitAll()   // ログインのビュー
        .loginProcessingUrl("/sign_in")    // ログイン処理を行うパス(マッピングしてるわけではない。そういう指示のためのパス)
        .failureUrl("/login?error")        // ログインエラー時の遷移先 ※パラメーターに「error」を付与
        .defaultSuccessUrl("/top", true)   // ログイン成功時の遷移先
        .usernameParameter("loginId")      // ログイン時のキー
        .passwordParameter("password")     // ログイン時のパスワード
    ;

    httpSecurity.logout()
        .logoutSuccessUrl("/login?logout") //ログアウト時の遷移先 POSTでアクセス
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .permitAll()
    ;

    httpSecurity.exceptionHandling()
        // 通常のRequestとAjaxを両方対応するSessionTimeout用
        .authenticationEntryPoint(authenticationEntryPoint())
        // csrfはsessionがないと動かない。SessionTimeout時にPOSTすると403 Forbiddenを必ず返してしまうため、
        // MissingCsrfTokenExceptionの時はリダイレクトを、それ以外の時は通常の扱いとする。
        .accessDeniedHandler(accessDeniedHandler());

  }

  @Autowired
  public void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider);
  }

  @Bean
  public HttpFirewall defaultHttpFirewall() {
    return new DefaultHttpFirewall();
  }

  @Bean
  AuthenticationEntryPoint authenticationEntryPoint() {
    return new SessionExpiredDetectingLoginUrlAuthenticationEntryPoint("/login");
  }

  @Bean
  AccessDeniedHandler accessDeniedHandler() {
    return new AccessDeniedHandler() {
      @Override
      public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (accessDeniedException instanceof MissingCsrfTokenException ||
            accessDeniedException instanceof InvalidCsrfTokenException) {
          authenticationEntryPoint().commence(request, response, null);
        } else {
          new AccessDeniedHandlerImpl().handle(request, response, accessDeniedException);
        }
      }
    };
  }

}
