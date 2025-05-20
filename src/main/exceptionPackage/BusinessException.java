package main.exceptionPackage;

public abstract class BusinessException extends Exception {
    private final String invalidValue;

    public BusinessException(String message) {
        super(message);
        this.invalidValue = null;
    }

    public BusinessException(String invalidValue, String message) {
        super(message);
        this.invalidValue = invalidValue;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    @Override
    public String getMessage() {
        String base = super.getMessage();
        if (invalidValue != null) {
            base += " (valeur invalide : " + invalidValue + ")";
        }
        return base;
    }
} 