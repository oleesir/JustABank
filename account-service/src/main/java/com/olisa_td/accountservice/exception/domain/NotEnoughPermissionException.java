package com.olisa_td.accountservice.exception.domain;

public class NotEnoughPermissionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NotEnoughPermissionException(String message) {
    super(message);
  }
}
