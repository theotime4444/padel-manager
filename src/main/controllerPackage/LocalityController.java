package main.controllerPackage;

import main.businessPackage.LocalityManager;
import main.exceptionPackage.*;
import main.modelPackage.LocalityModel;

import java.util.List;

public class LocalityController {
    private LocalityManager localityManager;

    public LocalityController() {
        this.localityManager = new LocalityManager();
    }

    public LocalityModel getLocalityById(int id) throws LocalitySearchException {
        return localityManager.getLocalityById(id);
    }

    public List<LocalityModel> getAllLocalities() throws LocalitySearchException {
        return localityManager.getAllLocalities();
    }

    public List<String> getAllRegions() throws LocalitySearchException {
        return localityManager.getAllRegions();
    }
} 