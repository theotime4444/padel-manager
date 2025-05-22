package main.dataAccessPackage;

import main.exceptionPackage.TournamentSearchException;
import main.modelPackage.TournamentModel;

public interface TournamentDataAccess {
    TournamentModel getTournamentByName(String name) throws TournamentSearchException;
} 