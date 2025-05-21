package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.MembershipModel;

public interface MembershipDataAccess {

    public Boolean createMembership(MembershipModel membership) throws MembershipCreationException;
    public Boolean deleteMembership(MembershipModel membership) throws MembershipDeletionException;
}
