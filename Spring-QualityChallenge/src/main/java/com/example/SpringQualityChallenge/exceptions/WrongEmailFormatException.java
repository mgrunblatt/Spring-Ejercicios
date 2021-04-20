package com.example.SpringQualityChallenge.exceptions;

public class WrongEmailFormatException extends Exception {
    public WrongEmailFormatException(String message) {
        super(message);
    }
}
