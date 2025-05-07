package main.dataAccessPackage;

import main.exceptionPackage.*;
import main.modelPackage.MembershipModel;

import java.util.*;

public interface MembershipDataAccess {
    public Boolean createMembership(MembershipModel membership) throws MembershipCreationException;
    public List<MembershipModel> getAllMemberships() throws MembershipSearchException;
    public List<MembershipModel> getMembershipsByPlayerId(int playerId) throws MembershipSearchException;
    public List<MembershipModel> getMembershipsByClubId(int clubId) throws MembershipSearchException;
}
