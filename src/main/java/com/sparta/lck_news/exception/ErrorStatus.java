package com.sparta.lck_news.exception;

import lombok.Getter;

@Getter
public enum ErrorStatus {


  ACCOUNT_DISABLED("계정비활성 상태입니다.", 400),
  ID_NOT_FOUND("아이디가 존재하지 않습니다.", 400),
  DUPLICATE_USER("중복된 사용자가 존재합니다.", 400),

  PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다.", 400),
  PASSWORD_SAME_AS_CURRENT("변경할 비밀번호가 현재 비밀번호와 같습니다.", 400),

  INVALID_USERNAME_FORMAT("아이디 형식이 올바르지 않습니다.", 400),
  INVALID_PASSWORD_FORMAT("비밀번호 형식이 올바르지 않습니다.", 400),

  POST_ID_NOT_FOUND("POST ID가 존재하지 않습니다.", 400);


  private final String errorMessage;
  private final int status;

  ErrorStatus(String errorMessage, int status) {
    this.errorMessage = errorMessage;
    this.status = status;
  }

}
