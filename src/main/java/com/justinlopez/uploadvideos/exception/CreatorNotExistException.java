package com.justinlopez.uploadvideos.exception;

public class CreatorNotExistException extends RuntimeException {

    public CreatorNotExistException(String message) {
        super("Creator with id " + message + " does not exist");
    }

}
