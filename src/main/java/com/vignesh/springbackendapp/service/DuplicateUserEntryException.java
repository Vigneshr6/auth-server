package com.vignesh.springbackendapp.service;

public class DuplicateUserEntryException extends Exception {
  public DuplicateUserEntryException(String message) {
    super(message);
  }
}
