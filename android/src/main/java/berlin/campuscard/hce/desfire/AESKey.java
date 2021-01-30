package berlin.campuscard.hce.desfire;

import berlin.campuscard.hce.ByteUtils;

public class AESKey {
    private final byte[] key;

    public AESKey(String key) throws InvalidParameterException {
        if (key.length() == 32) {
            this.key = ByteUtils.toByteArray(key);
        } else {
            throw new InvalidParameterException("AES key length should be 32 chars");
        }
    }

    public byte[] getKey() {
        return key;
    }
}
