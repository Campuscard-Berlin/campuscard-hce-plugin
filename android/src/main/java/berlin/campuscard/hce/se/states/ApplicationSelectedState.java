package berlin.campuscard.hce.se.states;

//import com.piotrekwitkowski.log.Log;
import android.util.Log;

import berlin.campuscard.hce.ByteUtils;
import berlin.campuscard.hce.desfire.Commands;
import berlin.campuscard.hce.desfire.ResponseCodes;
import berlin.campuscard.hce.se.Command;
import berlin.campuscard.hce.se.Application;
import berlin.campuscard.hce.se.Authentication;
import berlin.campuscard.hce.se.AuthenticationException;
import berlin.campuscard.hce.se.AuthenticationResponse;
import berlin.campuscard.hce.se.NoSuchKeyException;

public class ApplicationSelectedState extends State {
    private static final String TAG = "ApplicationSelectedState";
    private final Application application;
    private boolean authenticationInProgress = false;
    private Authentication authentication;

    ApplicationSelectedState(Application application) {
        this.application = application;
    }

    public CommandResult processCommand(Command command) {
        Log.i(TAG, "processCommand()");

        byte commandCode = command.getCode();
        byte[] commandData = command.getData();

        try {
            if (!authenticationInProgress && commandCode == Commands.AUTHENTICATE_AES) {
                return new CommandResult(this, ByteUtils.concatenate(ResponseCodes.ADDITIONAL_FRAME, initiateAESAuthentication(commandData)));
            } else if (authenticationInProgress && commandCode == Commands.ADDITIONAL_FRAME) {
                return proceedAuthentication(commandData);
            } else {
                return new CommandResult(this, ResponseCodes.ILLEGAL_COMMAND);
            }
        } catch (AuthenticationException e) {
            return new CommandResult(this, ResponseCodes.AUTHENTICATION_ERROR);
        } catch (CommandDataLengthException e) {
            return new CommandResult(this, ResponseCodes.LENGTH_ERROR);
        } catch (NoSuchKeyException e) {
            return new CommandResult(this, ResponseCodes.NO_SUCH_KEY);
        }
    }

    private byte[] initiateAESAuthentication(byte[] commandData) throws AuthenticationException, CommandDataLengthException, NoSuchKeyException {
        if (commandData.length == 1) {
            byte[] challenge = getChallenge(commandData[0]);
            Log.i(TAG, "challenge: " + ByteUtils.toHexString(challenge));
            this.authenticationInProgress = true;
            return challenge;
        } else {
            throw new CommandDataLengthException();
        }
    }

    private byte[] getChallenge(byte keyNumber) throws AuthenticationException, NoSuchKeyException {
        Log.i(TAG, "proceedAuthentication() " + keyNumber);

        try {
            this.authentication = new Authentication(this.application);
            return this.authentication.initiate(keyNumber);
        } catch (NoSuchKeyException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthenticationException();
        }
    }

    private CommandResult proceedAuthentication(byte[] readerChallenge) throws AuthenticationException, CommandDataLengthException {
        Log.i(TAG, "proceedAuthentication() " + readerChallenge.length);
        if (readerChallenge.length == 32) {
            try {
                AuthenticationResponse authenticationResponse = this.authentication.proceed(readerChallenge);
                this.authenticationInProgress = false;
                byte[] response = ByteUtils.concatenate(ResponseCodes.SUCCESS, authenticationResponse.getEncryptedRotatedA());
                return new CommandResult(new ApplicationAuthenticatedState(this.application, authenticationResponse.getSessionKey()), response);
            } catch (Exception e) {
                throw new AuthenticationException();
            }
        } else {
            throw new CommandDataLengthException();
        }
    }

}