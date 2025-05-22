package main.controllerPackage;

import main.businessPackage.CourtManager;
import main.exceptionPackage.CourtSearchException;
import main.modelPackage.CourtModel;

public class CourtController {
    private CourtManager courtManager;

    public CourtController() {
        this.courtManager = new CourtManager();
    }

    public CourtModel getCourtById(int id) throws CourtSearchException {
        return courtManager.getCourtById(id);
    }
} 