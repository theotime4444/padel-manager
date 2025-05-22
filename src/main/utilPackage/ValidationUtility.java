package main.utilPackage;

import main.exceptionPackage.ValidationException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class ValidationUtility {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static void checkRequiredString(String value, String fieldName, int min, int max) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " est obligatoire.");
        }
        if (value.length() < min || value.length() > max) {
            throw new ValidationException(value, fieldName + " doit faire entre " + min + " et " + max + " caractères.");
        }
    }

    public static void checkOptionalString(String value, String fieldName, int min, int max) throws ValidationException {
        if (value != null && !value.trim().isEmpty()) {
            if (value.length() < min || value.length() > max) {
                throw new ValidationException(value, fieldName + " doit faire entre " + min + " et " + max + " caractères.");
            }
        }
    }

    public static void checkValidEmail(String email, String fieldName) throws ValidationException {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException(fieldName + " est obligatoire.");
        }
        if (!pattern.matcher(email).matches()) {
            throw new ValidationException(email, fieldName + " n'est pas valide. Exemple d'email valide: example@example.com");
        }
    }

    public static void checkValidDate(Date date, String fieldName) throws ValidationException {
        if (date == null) {
            throw new ValidationException(fieldName + " est obligatoire.");
        }
        if (date.after(new Date(System.currentTimeMillis()))) {
            throw new ValidationException(date.toString(), fieldName + " ne peut pas être dans le futur.");
        }
    }

    public static void checkValidGender(Character gender, String fieldName) throws ValidationException {
        if (gender == null) {
            throw new ValidationException(fieldName + " est obligatoire.");
        }
        if (gender != 'M' && gender != 'F') {
            throw new ValidationException(String.valueOf(gender), fieldName + " doit être 'M' ou 'F'.");
        }
    }

    public static void checkValidRange(Integer value, int min, int max, String fieldName) throws ValidationException {
        if (value == null) {
            throw new ValidationException(fieldName + " est obligatoire.");
        }
        if (value < min || value > max) {
            throw new ValidationException(String.valueOf(value), fieldName + " doit être entre " + min + " et " + max + ".");
        }
    }

    public static void checkValidId(Integer id, String fieldName) throws ValidationException {
        if (id == null || id <= 0) {
            throw new ValidationException(String.valueOf(id), fieldName + " doit être un nombre positif.");
        }
    }

    public static void checkValidDateTime(LocalDateTime dateTime, String fieldName) throws ValidationException {
        if (dateTime == null) {
            throw new ValidationException(fieldName + " est obligatoire.");
        }
        if (dateTime.isAfter(LocalDateTime.now())) {
            throw new ValidationException(dateTime.toString(), fieldName + " ne peut pas être dans le futur.");
        }
    }
} 