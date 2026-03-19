package com.cognizant.healthcaregov.exception;

// Custom exception for when a doctor's slot is already taken or missing
public class SlotUnavailableException extends RuntimeException {
    public SlotUnavailableException(String message) {
        super(message);
    }
}