package berlin.campuscard.hce.application;

import berlin.campuscard.hce.desfire.InvalidParameterException;
import berlin.campuscard.hce.se.Application;

public class LibraryApplication extends Application {
    public LibraryApplication() throws InvalidParameterException {
        super(new LibraryAID(), new LibraryAESKey0(), new LibraryFile0());
    }
}
