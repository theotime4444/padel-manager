package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.LocalityModel;

import java.util.*;

public interface LocalityDataAccess {
    public LocalityModel getLocalityById(int id) throws LocalitySearchException;
}
