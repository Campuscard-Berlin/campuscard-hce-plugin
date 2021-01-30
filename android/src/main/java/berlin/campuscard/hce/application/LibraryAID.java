package berlin.campuscard.hce.application;

import berlin.campuscard.hce.desfire.AID;
import berlin.campuscard.hce.desfire.InvalidParameterException;

class LibraryAID extends AID {
    LibraryAID() throws InvalidParameterException {
        super("015548");
    }
}
