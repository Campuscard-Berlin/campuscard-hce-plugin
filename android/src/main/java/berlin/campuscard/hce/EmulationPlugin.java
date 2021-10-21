package berlin.campuscard.hce;

import android.Manifest;
import android.util.Log;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginHandle;
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
