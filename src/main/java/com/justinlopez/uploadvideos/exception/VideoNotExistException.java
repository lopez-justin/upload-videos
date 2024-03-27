package com.justinlopez.uploadvideos.exception;

public class VideoNotExistException extends RuntimeException {
    public VideoNotExistException(String message) {
        super("Video with id " + message + " does not exist.");
    }
}
