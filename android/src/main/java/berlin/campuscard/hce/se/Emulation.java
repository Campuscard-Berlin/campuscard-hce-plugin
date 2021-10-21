package berlin.campuscard.hce.se;

//import com.piotrekwitkowski.log.Log;

import android.util.Log;

public class Emulation {
    private static final String TAG = "Emulation";
    private final SecureElement secureElement;

    public Emulation(SecureElement secureElement) {
        this.secureElement = secureElement;
    }

    public byte[] getResponse(byte[] apdu) {
        Log.i(TAG, "getResponse()");

        byte[] unwrappedApdu = new byte[apdu.length - 5 <= 0 ? 1 : apdu.length - 5];
        unwrappedApdu[0] = apdu[1];

        if (apdu.length > 5) {
          System.arraycopy(apdu, 5, unwrappedApdu, 1, apdu.length - 6);
        }

        byte[] unwrappedResponse = secureElement.processCommand(new Command(unwrappedApdu));

        byte[] wrappedResponse = new byte[unwrappedResponse.length + 1];
        System.arraycopy(unwrappedResponse, 1, wrappedResponse, 0, unwrappedResponse.length - 1);
        wrappedResponse[wrappedResponse.length - 2] = (byte) 0x91;
        wrappedResponse[wrappedResponse.length - 1] = unwrappedResponse[0];

        return wrappedResponse;
    }

}
