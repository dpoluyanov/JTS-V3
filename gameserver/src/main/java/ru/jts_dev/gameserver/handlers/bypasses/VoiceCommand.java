package ru.jts_dev.gameserver.handlers.bypasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.handlers.*;

import java.util.Collections;
import java.util.List;

/**
 * @author AN3O
 */
@Component
public class VoiceCommand extends CommandHandler<String> {
    @Autowired
    private VoicedHandlerManager voicedHandlerManager;

    @TextCommand
    public boolean voice(BypassHandlerParams params) {
        // only voice commands allowed
        if (!params.getArgs().isEmpty() && params.getArgs().get(0).startsWith(".")) {
            String command = params.getArgs().get(0);

            if (command.isEmpty()) {
                return false;
            }

            command = command.substring(1);

            List<String> args = params.getArgs().size() > 1 ? params.getArgs().subList(1, params.getArgs().size()) : Collections.<String>emptyList();

            if (!command.isEmpty()) {
                voicedHandlerManager.execute(new HandlerParams<>(params.getSession(), params.getCharacter(), command,
                        args, Collections.<String, String>emptyMap()));
            }
        }
        return false;
    }
}