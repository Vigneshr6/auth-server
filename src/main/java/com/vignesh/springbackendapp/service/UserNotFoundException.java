package com.vignesh.springbackendapp.service;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not found");
    }
}
