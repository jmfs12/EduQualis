package com.social.eduqualis.repository;

public class FailToSaveVideoException extends RuntimeException {
    public FailToSaveVideoException(String message) {
        super(message);
    }
}
