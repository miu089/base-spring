package com.miu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * 引数の指定が面倒なのでControllerなどで継承して利用する用
 */
@Service
public class MessageService {
  @Autowired
  protected MessageSource msg;

  public String addResultMessage(String key){
    return  msg.getMessage(key,null, Locale.JAPANESE)+"\n";
  }

  public String addResultMessage(String key, String value){
    return  msg.getMessage(key,new String[]{ value }, Locale.JAPANESE)+"\n";
  }

  public String addResultMessage(String key, String value, int i){
    return  msg.getMessage(key,new String[]{ value, String.valueOf(i) }, Locale.JAPANESE)+"\n";
  }

}
