package berlin.campuscard.hce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;

import berlin.campuscard.hce.se.SecureElement;
import berlin.campuscard.hce.se.Application;
import berlin.campuscard.hce.se.Emulation;
import berlin.campuscard.hce.desfire.InvalidParameterException;
import berlin.campuscard.hce.application.LibraryApplication;

public class HCEService extends HostApduService {
    private static final String TAG = "HCEService";
    private static boolean firstInteraction = true;
    private static Emulation emulation;

    @Override
    public byte[] processCommandApdu(byte[] command, Bundle extras) {
        EmulationPlugin plugin = EmulationPlugin.getEmulationInstance();
        Log.i(TAG, "<-- " + ByteUtils.toHexString(command));
        plugin.onNewData("<-- " + ByteUtils.toHexString(command));

        byte[] response = firstInteraction ? getFirstResponse(command) : getNextResponse(command);
        Log.i(TAG, "--> " + ByteUtils.toHexString(response));
        plugin.onNewData("--> " + ByteUtils.toHexString(response));
        return response;
    }

    private byte[] getFirstResponse(byte[] command) {
        Log.i(TAG, "<-- " + ByteUtils.toHexString(command));
//        notifications.createNotificationChannel(this);
//        notifications.show("<--" + ByteUtils.toHexString(command));

        try {
            emulation = getEmulation();
            firstInteraction = false;
            return Iso7816.RESPONSE_SUCCESS;
        } catch (InvalidParameterException e) {
            Log.e(TAG, e.getMessage());
            return Iso7816.RESPONSE_INTERNAL_ERROR;
        }
    }

    private Emulation getEmulation() throws InvalidParameterException {
        Log.e(TAG, "getEmulation");
        SharedPreferences sp = getSharedPreferences("com.berlin.campuscard.hce.library", Context.MODE_PRIVATE);
        String s = sp.getString("librarydata", null);
        Log.i(TAG, s);
        Application libraryApplication = new LibraryApplication(s);
        Application[] applications = new Application[] {libraryApplication};
        SecureElement seWrapper = new SecureElement(applications);
        return new Emulation(seWrapper);
    }

    private byte[] getNextResponse(byte[] command) {
        Log.i(TAG, "<-- " + ByteUtils.toHexString(command));
//        notifications.show("<--" + ByteUtils.toHexString(command));
        return emulation.getResponse(command);
    }

    @Override
    public void onDeactivated(int reason) {
        Log.i(TAG, "onDeactivated(). Reason: " + reason);
        firstInteraction = true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i("HCEService", "onRebind()");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("HCEService", "onCreate()");
    }
}
