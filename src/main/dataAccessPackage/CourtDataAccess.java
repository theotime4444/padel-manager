package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.CourtModel;


import java.util.*;

public interface CourtDataAccess {
    public CourtModel getCourtById(int id) throws CourtSearchException;
    public List<CourtModel> getAllCourts() throws CourtSearchException;
}
