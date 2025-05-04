package main.businessPackage;

import main.exceptionPackage.*;
import main.modelPackage.ClubModel;
import main.dataAccessPackage.ClubDBAccess;
import main.dataAccessPackage.ClubDataAccess;
import main.modelPackage.PlayerModel;

import java.sql.Date;
import java.util.*;
import java.util.regex.Pattern;

public class ClubManager implements ClubDataAccess {
    private final ClubDataAccess clubDataAccess;

    public ClubManager() throws ConnectionDataAccessException {
        this.clubDataAccess= new ClubDBAccess();
    }


    @Override
    public Boolean createClub(ClubModel club) throws ClubCreationException {
        validateClub(club);
        return clubDataAccess.createClub(club);
    }

    @Override
    public Boolean updateClub(ClubModel club) throws ClubCreationException {
        validateClub(club);
        return clubDataAccess.updateClub(club);
    }

    @Override
    public ClubModel getLastClubByPlayer(PlayerModel player) throws ClubSearchException, ConnectionDataAccessException, PlayerCreationException {
        PlayerManager playerManager = new PlayerManager();
        playerManager.validatePlayer(player);
        return clubDataAccess.getLastClubByPlayer(player);
    }

    @Override
    public Boolean deleteClub(ClubModel club) throws ClubDeletionException {
        return clubDataAccess.deleteClub(club);
    }

    //Validation
    private void validateClub(ClubModel club) throws ClubCreationException {
        if (club == null) throw new ClubCreationException("Le joueur est nul");

        checkRequiredString(club.getName(), "Le nom", 1, 50);
        checkRequiredString(club.getStreetAddress(), "La rue", 1, 50);
        checkValidId(club.getLocalityID(), "La localité");
        checkRequiredString(club.getPhoneNumber(), "Le numéro de téléphone", 1, 50);
        checkValidDate((Date) club.getCreationDate(), "La date de création");

        checkOptionalString(club.getWebsite(), "Le site internet", 1, 50);
        checkOptionalString(club.getInstagramProfile(), "Le profil Instagram", 1, 255);
    }

    private void checkRequiredString(String value, String fieldName, int min, int max) throws ClubCreationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ClubCreationException(fieldName + " est obligatoire.");
        }
        if (value.length() < min || value.length() > max) {
            throw new ClubCreationException(fieldName + " doit faire entre " + min + " et " + max + " caractères.");
        }
    }

    private void checkOptionalString(String value, String fieldName, int min, int max) throws ClubCreationException {
        if (value != null && (value.length() < min || value.length() > max)) {
            throw new ClubCreationException(fieldName + " doit faire entre " + min + " et " + max + " caractères.");
        }
    }

    private void checkValidEmail(String email) throws ClubCreationException {
        checkRequiredString(email, "L'email", 1, 50);
        String emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        if (!Pattern.matches(emailRegex, email)) {
            throw new ClubCreationException("L'email n'est pas valide.");
        }
    }

    private void checkValidDate(Date date, String fieldName) throws ClubCreationException {
        if (date == null) {
            throw new ClubCreationException(fieldName + " est obligatoire.");
        }
        if (date.toLocalDate().isAfter(java.time.LocalDate.now())) {
            throw new ClubCreationException(fieldName + " ne peut pas être dans le futur.");
        }
    }

    private void checkValidGender(char gender) throws ClubCreationException {
        if (gender != 'M' && gender != 'F') {
            throw new ClubCreationException("Le genre doit être 'M' ou 'F'.");
        }
    }

    private void checkValidRange(int value, int min, int max, String fieldName) throws ClubCreationException {
        if (value < min || value > max) {
            throw new ClubCreationException(fieldName + " doit être compris entre " + min + " et " + max + ".");
        }
    }

    private void checkValidId(int id, String fieldName) throws ClubCreationException {
        if (id <= 0) {
            throw new ClubCreationException(fieldName + " doit être un identifiant positif.");
        }
    }
}
