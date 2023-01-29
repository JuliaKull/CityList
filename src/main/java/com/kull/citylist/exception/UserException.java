package com.kull.citylist.exception;

public class UserException extends RuntimeException {

    public final static String USER_MESSAGE = "User not found";
    public UserException(String message) {
        super(USER_MESSAGE);
    }
}
