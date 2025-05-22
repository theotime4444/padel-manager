package main.businessPackage;

import main.dataAccessPackage.CourtDataAccess;
import main.dataAccessPackage.CourtDBAccess;
import main.exceptionPackage.CourtSearchException;
import main.modelPackage.CourtModel;

public class CourtManager {
    private CourtDataAccess courtDataAccess;

    public CourtManager() {
        this.courtDataAccess = new CourtDBAccess();
    }

    public CourtModel getCourtById(int id) throws CourtSearchException {
        if (id <= 0) {
            throw new CourtSearchException("L'ID du terrain doit être positif");
        }
        return courtDataAccess.getCourtById(id);
    }
} 