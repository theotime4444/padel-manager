package main.businessPackage;

import main.dataAccessPackage.LocalityDataAccess;
import main.dataAccessPackage.LocalityDBAccess;
import main.exceptionPackage.*;
import main.modelPackage.LocalityModel;

import java.util.List;

public class LocalityManager {
    private LocalityDataAccess localityDataAccess;

    public LocalityManager() {
        this.localityDataAccess = new LocalityDBAccess();
    }

    public LocalityModel getLocalityById(int id) throws LocalitySearchException {
        return localityDataAccess.getLocalityById(id);
    }

    public List<LocalityModel> getAllLocalities() throws LocalitySearchException {
        return localityDataAccess.getAllLocalities();
    }

    public List<String> getAllRegions() throws LocalitySearchException {
        return localityDataAccess.getAllRegions();
    }
} 