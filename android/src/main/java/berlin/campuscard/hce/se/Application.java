package berlin.campuscard.hce.se;

import berlin.campuscard.hce.desfire.AESKey;
import berlin.campuscard.hce.desfire.AID;
import berlin.campuscard.hce.desfire.File;

public abstract class Application {
    private final AID aid;
    private final AESKey key0;
    private final File file0;

    protected Application(AID aid, AESKey aesKey0, File file0) {
        this.aid = aid;
        this.key0 = aesKey0;
        this.file0 = file0;
    }

    public AID getAid() {
        return aid;
    }

    public File getFile0() { return file0; }

    AESKey getKey0() {
        return this.key0;
    }

}
