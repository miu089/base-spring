package com.miu;

import com.miu.dialect.TextLineDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    System.out.println("サービス起動しました。");
  }

  /** 作成したダイアレクトの登録 */
  @Bean
  TextLineDialect textLineDialect() {
    return new TextLineDialect();
  }

}
