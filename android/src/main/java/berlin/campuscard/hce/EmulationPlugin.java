package berlin.campuscard.hce;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginHandle;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

@CapacitorPlugin(
        name = "Emulation",
        permissions = {
                @Permission(
                        alias = "nfc",
                        strings = {
                                Manifest.permission.NFC,
                                Manifest.permission.BIND_NFC_SERVICE
                        }
                )
        }
)
public class EmulationPlugin extends Plugin {

    private static Bridge staticBridge;

    public static EmulationPlugin getEmulationInstance() {
        if (staticBridge != null && staticBridge.getWebView() != null) {
            PluginHandle handle = staticBridge.getPlugin("Emulation");
            if (handle == null) {
                return null;
            }
            return (EmulationPlugin) handle.getInstance();
        }
        return null;
    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void changeAppData(PluginCall call) {
        Log.i("EmulationPlugin", "changing app data -- native");
        int lastDigit = call.getInt("lastDigit");
        if (lastDigit < 0 || lastDigit > 9) {
            call.reject("Last digit should be a single-digit number");
            return;
        }

        SharedPreferences sp = this.bridge.getContext().getSharedPreferences("com.berlin.campuscard.hce.library", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("librarydata", "00000000000000000000485548533031323334353" + lastDigit + "3000000000000000000000");
        e.apply();

        call.resolve();
    }

    @Override
    public void load() {
        super.load();
        Log.i("EmulationPlugin", "load()");
        staticBridge = this.bridge;
    }

    public void onNewData(String data) {
        Log.i("EmulationPlugin", "onNewData:" + data);
        JSObject ret = new JSObject();
        ret.put("value", data);
        notifyListeners("onNewData", ret);
    }
}
