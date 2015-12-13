package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;

/**
 * @author Camelion
 * @since 13.12.15
 */
public class VersionCheck extends OutgoingMessageWrapper {
    private final byte[] key;

    public VersionCheck(byte[] key) {
        this.key = key;
    }

    @Override
    public void write() {
        putByte(0x2E);
        putByte(0x01); // 0 - wrong protocol, 1 protocol ok
        putBytes(key);
        putInt(0x01);
        putInt(0x01); // TODO: 13.12.15 serverId?
        putByte(0x00);
        // TODO: 13.12.15 obfuscation: https://github.com/l2jfree/svn/blob/15e8c48533246d30712806a099f09d17b522f86b/branches/genesis/l2jfree-core/src/main/java/com/l2jfree/gameserver/network/client/packets/sendable/characterless/VersionCheck.java
        putInt(0x00); // Seed (obfuscation key)
    }
}
