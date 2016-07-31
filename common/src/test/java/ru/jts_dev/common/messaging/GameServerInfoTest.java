package ru.jts_dev.common.messaging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.jts_dev.Fixture;
import ru.jts_dev.FixtureParameterResolver;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Fast unit test for method {@link GameServerInfo} methods execution
 *
 * @author Camelion
 * @since 31.07.16
 */
@Tag("fast")
@ExtendWith(FixtureParameterResolver.class)
@DisplayName("Typical GameServerInfo test")
public class GameServerInfoTest {
    @Test
    public void getServerId(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertEquals(typical.getServerId(), 1, "typical getServerId() works correctly");
    }

    @Test
    public void getAddress(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertEquals(typical.getAddress(), InetAddress.getLocalHost(), "typical getAddress() works correctly");
    }

    @Test
    public void getPort(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertEquals(typical.getPort(), 9016, "typical getPort() works correctly");
    }

    @Test
    public void isAgeLimit(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertFalse(typical.isAgeLimit(), "typical isAgeLimit() works correctly");
    }

    @Test
    public void isPvp(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertFalse(typical.isPvp(), "typical isPvp() works correctly");
    }

    @Test
    public void getOnlinePlayers(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertEquals(typical.getOnlinePlayers(), 100, "typical getOnlinePlayers() works correctly");
    }

    @Test
    public void getMaxPlayers(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertEquals(typical.getMaxPlayers(), 5000, "typical getMaxPlayers() works correctly");
    }

    @Test
    public void isEnabled(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertTrue(typical.isEnabled(), "typical isEnabled() works correctly");
    }

    @Test
    public void getServerType(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertEquals(typical.getServerType(), 1, "typical getServerType() works correctly");
    }

    @Test
    public void isBracketsEnabled(@Fixture("typical") GameServerInfo typical) throws Exception {
        assertFalse(typical.isBracketsEnabled(), "typical isBracketsEnabled() works correctly");
    }
}