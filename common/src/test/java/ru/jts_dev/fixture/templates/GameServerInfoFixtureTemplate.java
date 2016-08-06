package ru.jts_dev.fixture.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import ru.jts_dev.common.messaging.GameServerInfo;

import java.net.InetAddress;

/**
 * @author Camelion
 * @since 31.07.16
 */
public class GameServerInfoFixtureTemplate implements TemplateLoader {
    @Override
    public void load() {
        try {
            Fixture.of(GameServerInfo.class).addTemplate("default", new Rule() {{
                add("serverId", random(Byte.class, range(1, 127)));
                add("address", InetAddress.getLocalHost());
                add("port", random(Integer.class, range(1000, 65535)));
                add("ageLimit", random(true, false));
                add("pvp", random(true, false));
                add("onlinePlayers", random(Integer.class, range(0, 10_000)));
                add("maxPlayers", random(Integer.class, range(10_000, 20_000)));
                add("enabled", random(true, false));
                add("serverType", random(1, 2, 4, 8, 16, 32, 64));
                add("bracketsEnabled", random(true, false));
            }});

            Fixture.of(GameServerInfo.class).addTemplate("typical", new Rule() {{
                add("serverId", 1);
                add("address", InetAddress.getLocalHost());
                add("port", 9016);
                add("ageLimit", false);
                add("pvp", false);
                add("onlinePlayers", 100);
                add("maxPlayers", 5000);
                add("enabled", true);
                add("serverType", 1);
                add("bracketsEnabled", false);
            }});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
