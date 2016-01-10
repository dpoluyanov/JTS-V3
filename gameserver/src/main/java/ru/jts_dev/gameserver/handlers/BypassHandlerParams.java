package ru.jts_dev.gameserver.handlers;

import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.List;
import java.util.Map;

/**
 * Parameter container for bypass command handlers only.
 *
 * @author Yorie
 */
public class BypassHandlerParams extends HandlerParams<String> {
    private final GameCharacter target;
    // TODO: Понапридумывали хуеты. Выпилить и сделать по-нормальному вместе с bypass-валидатором в L2PcInstance.
    private final String bypassSource;

    public BypassHandlerParams(GameCharacter character, String bypassSource, String command, GameCharacter target) {
        super(character, command);
        this.target = target;
        this.bypassSource = bypassSource;
    }

    public BypassHandlerParams(GameCharacter character, String bypassSource, String command, GameCharacter target,
                               List<String> args, Map<String, String> queryArgs) {
        super(character, command, args, queryArgs);
        this.target = target;
        this.bypassSource = bypassSource;
    }

    /**
     * @return Returns bypass source command (source string with non-chunked parameters).
     */
    public String getSource() {
        return bypassSource;
    }

    public GameCharacter getTarget() {
        return target;
    }
}
