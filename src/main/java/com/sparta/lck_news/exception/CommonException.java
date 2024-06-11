package com.sparta.lck_news.exception;

import lombok.Getter;

@Getter
public class CommonException extends IllegalArgumentException{

  private final int status;

  public CommonException(ErrorStatus errorStatus) {
    super(errorStatus.getErrorMessage());
    this.status = errorStatus.getStatus();
  }
}
