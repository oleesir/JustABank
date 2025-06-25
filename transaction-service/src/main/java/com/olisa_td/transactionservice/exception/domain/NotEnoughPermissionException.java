package com.olisa_td.transactionservice.exception.domain;

public class NotEnoughPermissionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public NotEnoughPermissionException(String message) {
    super(message);
  }
}
