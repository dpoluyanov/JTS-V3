package ru.jts_dev.gameserver.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
// TODO rework
public class AdminCommandHandler implements IHandler<IAdminCommandHandler, String> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final Map<String, IAdminCommandHandler> _handlers = new HashMap<>();

    @PostConstruct
    private void load() {
        //registerHandler(new AdminAdmin());
        log.info("Loaded {} Admin Command Handlers", size());
    }

    @Override
    public void registerHandler(IAdminCommandHandler handler) {
        String[] ids = handler.getAdminCommandList();
        for (String id : ids) {
            _handlers.put(id, handler);
        }
    }

    public IAdminCommandHandler getHandler(String adminCommand) {
        String command = adminCommand;
        if (adminCommand.contains(" ")) {
            command = adminCommand.substring(0, adminCommand.indexOf(" "));
        }
        return _handlers.get(command);
    }

    @Override
    public int size() {
        return _handlers.size();
    }
}
