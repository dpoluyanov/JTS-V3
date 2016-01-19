package ru.jts_dev.gameserver.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.model.GameCharacter;

/**
 * @author Camelion
 * @since 17.01.16
 */
@Service
public class InventoryService {
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    public void giveItem(GameCharacter character, int itemId) {
    }

    public void giveItems(GameCharacter character, int... itemIds) {
    }
}
