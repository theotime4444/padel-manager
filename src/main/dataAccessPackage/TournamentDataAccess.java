package main.dataAccessPackage;

import main.exceptionPackage.TournamentSearchException;
import main.modelPackage.TournamentModel;

import java.util.List;

public interface TournamentDataAccess {
    List<TournamentModel> getTournamentsByName(String name) throws TournamentSearchException;
} 