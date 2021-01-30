package berlin.campuscard.hce.se.states;

import berlin.campuscard.hce.se.Command;

public abstract class State {
    public abstract CommandResult processCommand(Command c);
}
