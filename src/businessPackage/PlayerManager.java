package businessPackage;

import exceptionPackage.*;
import modelPackage.PlayerModel;
import dataAccessPackage.PlayerDataAccess;
import dataAccessPackage.PlayerDBAccess;
import exceptionPackage.ConnectionDataAccessException;

import java.sql.Date;
import java.util.*;

public class PlayerManager implements PlayerDataAccess {
    private PlayerDataAccess playerDataAccess;

    public PlayerManager() throws ConnectionDataAccessException {
        setPlayerDAO(new PlayerDBAccess());
    }

    private void setPlayerDAO(PlayerDataAccess dao) {
        this.playerDataAccess = dao;
    }

    private Boolean validPlayer(PlayerModel player) throws PlayerCreationException {
        String lastName = player.getLastname();
        String firstName = player.getFirstname();
        java.sql.Date birthdayDate = (Date) player.getBirthdayDate();
        char gender = player.getGender();
        int eloPoints = player.getEloPoints();
        String phoneNumber = player.getPhoneNumber();
        String email = player.getEmail();
        boolean isPro = player.getIsPro();
        int playerLocality = player.getLocality();
        String instagramProfile = player.getInstagramProfile();

        if (lastName == null
            || firstName == null
            || birthdayDate == null
            || gender == null
            || eloPoints == null
            || email == null 
            || isPro == null 
            || playerLocality == null)
            throw new PlayerCreationException("Un ou plusieurs champs sont nuls");

        if (lastName.trim().isEmpty() || firstName.trim().isEmpty() || email.trim().isEmpty())
            throw new PlayerCreationException("Un ou plusieurs champs sont vides");

        String emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches())
            throw new PlayerCreationException("L'email n'est pas valide");

        if (!main.utilPackage.FormValidator.validStringLength(email, 1, 50))
            throw new PlayerCreationException("L'email doit être compris entre 1 et 50 caractères");

        if (!main.utilPackage.FormValidator.validStringLength(lastName, 1, 50))
            throw new PlayerCreationException("Le nom doit être compris entre 1 et 50 caractères");

        if (!main.utilPackage.FormValidator.validStringLength(firstName, 1, 50))
            throw new PlayerCreationException("Le prénom doit être compris entre 1 et 50 caractères");

        if (!main.utilPackage.FormValidator.validDateOfBirth(birthdayDate.toLocalDate()))
            throw new PlayerCreationException("La date de naissance n'est pas valide");

        if (!main.utilPackage.FormValidator.validGender(gender))
            throw new PlayerCreationException("Le genre n'est pas valide");

        if (!main.utilPackage.FormValidator.validEloPoints(eloPoints))
            throw new PlayerCreationException("Les points ELO ne sont pas valides");

        if (!main.utilPackage.FormValidator.isFieldNull(phoneNumber) && !main.utilPackage.FormValidator.validStringLength(phoneNumber, 1, 20))
            throw new PlayerCreationException("Le numéro de téléphone n'est pas valide");

        if (!main.utilPackage.FormValidator.isFieldNull(instagramProfile) && !main.utilPackage.FormValidator.validStringLength(instagramProfile, 1, 255))
            throw new PlayerCreationException("Le profil Instagram n'est pas valide");

        if (!main.utilPackage.FormValidator.validBoolean(isPro))
            throw new PlayerCreationException("Le statut professionnel n'est pas valide");

        if (!main.utilPackage.FormValidator.validId(playerLocality))
            throw new PlayerCreationException("La localité n'est pas valide");

        return true;
    }

    @Override
    public Boolean createPlayer(PlayerModel player) throws PlayerCreationException {
        return validPlayer(player) ? playerDataAccess.createPlayer(player) : false;
    }

    @Override
    public Boolean updatePlayer(PlayerModel player) throws PlayerCreationException, PlayerUpdateException {
        return validPlayer(player) ? playerDataAccess.updatePlayer(player) : false;
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
}
