package com.example.demo.bean;

import org.springframework.stereotype.Service;

@Service( "messageTranslatorBean" )
public class MessageTranslatorBean {

  public static final String PREFIX = "Translated: ";

  public String translateMessage(String input) {

    return PREFIX + input;
  }

}
