package berlin.campuscard.hce;

import android.Manifest;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
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
public class CampuscardEmulationPlugin extends Plugin {

    private CampuscardEmulation implementation = new CampuscardEmulation();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
}
