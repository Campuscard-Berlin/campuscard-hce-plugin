package berlin.campuscard.hce.se;

//import com.piotrekwitkowski.log.Log;
import android.util.Log;

import berlin.campuscard.hce.se.states.CommandResult;
import berlin.campuscard.hce.se.states.InitialState;
import berlin.campuscard.hce.se.states.State;

public class SecureElement {
    private static final String TAG = "SoftwareSEWrapper";
    private State state;

    public SecureElement(Application[] applications) {
        this.state = new InitialState(applications);
    }

    byte[] processCommand(Command command) {
        Log.i(TAG, "processCommand()");

        CommandResult result = this.state.processCommand(command);
        this.state = result.getState();
        return result.getResponse();
    }

}
