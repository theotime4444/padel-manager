package main.businessPackage;

import main.exceptionPackage.*;
import main.modelPackage.PlayerModel;
import main.dataAccessPackage.PlayerDataAccess;
import main.dataAccessPackage.PlayerDBAccess;
import main.exceptionPackage.ConnectionDataAccessException;

import java.sql.Date;
import java.util.*;
import java.util.regex.*;

public class PlayerManager implements PlayerDataAccess {
    private final PlayerDataAccess playerDataAccess;

    public PlayerManager() throws ConnectionDataAccessException {
        this.playerDataAccess = new PlayerDBAccess();
    }

    @Override
    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException {
        validatePlayer(player);
        return playerDataAccess.createPlayer(player);
    }

    @Override
    public Boolean updatePlayer(PlayerModel player) throws PlayerCreationException, PlayerUpdateException {
        validatePlayer(player);
        return playerDataAccess.updatePlayer(player);
    }

    @Override
    public List<PlayerModel> getAllPlayers() throws PlayerSearchException {
        return playerDataAccess.getAllPlayers();
    }

    @Override
    public PlayerModel getPlayerById(int playerId) throws PlayerSearchException {
        return playerDataAccess.getPlayerById(playerId);
    }

    @Override
    public List<PlayerModel> getPlayersByFullName(String firstName, String lastName) throws PlayerSearchException {
        return playerDataAccess.getPlayersByFullName(firstName, lastName);
    }

    @Override
    public Boolean deletePlayer(PlayerModel player) throws PlayerDeletionException {
        return playerDataAccess.deletePlayer(player);
    }

    //Validation
    private void validatePlayer(PlayerModel player) throws PlayerCreationException {
        if (player == null) throw new PlayerCreationException("Le joueur est nul");

        checkRequiredString(player.getLastname(), "Le nom", 1, 50);
        checkRequiredString(player.getFirstname(), "Le prénom", 1, 50);
        checkValidEmail(player.getEmail());
        checkValidDate((Date) player.getBirthdayDate(), "La date de naissance");
        checkValidGender(player.getGender());
        checkValidRange(player.getEloPoints(), 0, 5000, "Les points ELO"); // plage à adapter
        checkValidId(player.getLocality(), "La localité");

        checkOptionalString(player.getPhoneNumber(), "Le numéro de téléphone", 1, 20);
        checkOptionalString(player.getInstagramProfile(), "Le profil Instagram", 1, 255);
    }

    private void checkRequiredString(String value, String fieldName, int min, int max) throws PlayerCreationException {
        if (value == null || value.trim().isEmpty()) {
            throw new PlayerCreationException(fieldName + " est obligatoire.");
        }
        if (value.length() < min || value.length() > max) {
            throw new PlayerCreationException(fieldName + " doit faire entre " + min + " et " + max + " caractères.");
        }
    }

    private void checkOptionalString(String value, String fieldName, int min, int max) throws PlayerCreationException {
        if (value != null && (value.length() < min || value.length() > max)) {
            throw new PlayerCreationException(fieldName + " doit faire entre " + min + " et " + max + " caractères.");
        }
    }

    private void checkValidEmail(String email) throws PlayerCreationException {
        checkRequiredString(email, "L'email", 1, 50);
        String emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        if (!Pattern.matches(emailRegex, email)) {
            throw new PlayerCreationException("L'email n'est pas valide.");
        }
    }

    private void checkValidDate(Date date, String fieldName) throws PlayerCreationException {
        if (date == null) {
            throw new PlayerCreationException(fieldName + " est obligatoire.");
        }
        if (date.toLocalDate().isAfter(java.time.LocalDate.now())) {
            throw new PlayerCreationException(fieldName + " ne peut pas être dans le futur.");
        }
    }

    private void checkValidGender(char gender) throws PlayerCreationException {
        if (gender != 'M' && gender != 'F') {
            throw new PlayerCreationException("Le genre doit être 'M' ou 'F'.");
        }
    }

    private void checkValidRange(int value, int min, int max, String fieldName) throws PlayerCreationException {
        if (value < min || value > max) {
            throw new PlayerCreationException(fieldName + " doit être compris entre " + min + " et " + max + ".");
        }
    }

    private void checkValidId(int id, String fieldName) throws PlayerCreationException {
        if (id <= 0) {
            throw new PlayerCreationException(fieldName + " doit être un identifiant positif.");
        }
    }
}
