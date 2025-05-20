package main.exceptionPackage;

public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String invalidValue, String message) {
        super(invalidValue, message);
    }
} 