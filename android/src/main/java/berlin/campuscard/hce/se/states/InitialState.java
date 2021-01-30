package berlin.campuscard.hce.se.states;

//import com.piotrekwitkowski.log.Log;
import android.util.Log;

import berlin.campuscard.hce.se.Command;
import berlin.campuscard.hce.desfire.Commands;
import berlin.campuscard.hce.desfire.ResponseCodes;
import berlin.campuscard.hce.desfire.AID;
import berlin.campuscard.hce.desfire.InvalidParameterException;
import berlin.campuscard.hce.se.Application;

public class InitialState extends State {
    private static final String TAG = "InitialState";
    private final Application[] applications;

    public InitialState(Application[] applications) {
        this.applications = applications;
    }

    public CommandResult processCommand(Command command) {
        Log.i(TAG, "processCommand()");

        if (command.getCode() == Commands.SELECT_APPLICATION) {
            return selectApplication(command.getData());
        } else {
            return new CommandResult(this, ResponseCodes.ILLEGAL_COMMAND);
        }
    }

    private CommandResult selectApplication(byte[] aid) {
        Log.i(TAG, "selectApplication()");

        try {
            AID aidToSelect = new AID(aid);
            return new CommandResult(selectApplication(aidToSelect), ResponseCodes.SUCCESS);
        } catch (InvalidParameterException ex) {
            return new CommandResult(this, ResponseCodes.LENGTH_ERROR);
        } catch (ApplicationNotFoundException ex) {
            return new CommandResult(this, ResponseCodes.APPLICATION_NOT_FOUND);
        }
    }

    private ApplicationSelectedState selectApplication(AID aidToSelect) throws ApplicationNotFoundException {
        Log.i(TAG, "selectApplication()");
        for (Application a : applications) {
            if (a.getAid().equals(aidToSelect)) {
                return new ApplicationSelectedState(a);
            }
        }
        throw new ApplicationNotFoundException();
    }

}
