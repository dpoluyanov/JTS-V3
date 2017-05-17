/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.jts_dev.gameserver.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jts_dev.common.id.IdPool;
import ru.jts_dev.gameserver.constants.ItemClass;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameItem;
import ru.jts_dev.gameserver.parser.data.item.ItemData;
import ru.jts_dev.gameserver.parser.data.item.ItemDatasHolder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for operations with character inventory.
 *
 * @author Camelion
 * @since 17.01.16
 */
@Service
public class InventoryService {
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final IdPool itemIdPool;
    private final ItemDatasHolder itemDatasHolder;

    @Autowired
    public InventoryService(IdPool itemIdPool, ItemDatasHolder itemDatasHolder) {
        this.itemIdPool = itemIdPool;
        this.itemDatasHolder = itemDatasHolder;
    }

    /**
     * Collect all items, that type not equals to {@link ItemClass#QUESTITEM}.
     * Returns copied and filtered list of {@code character} items.
     * There are no guaranties of mutability, thread-safety for returned {@link List}.
     *
     * @param character - character, which items will be returned
     * @return - list of all items, except quest items
     */
    public static List<GameItem> getCommonItemsFrom(final GameCharacter character) {
        return character.getInventory().getItems()
                .stream()
                .filter(item -> item.getItemData().getItemType() != ItemClass.QUESTITEM)
                .collect(Collectors.toList());
    }

    /**
     * Create new {@link GameItem} and add it to character inventory.
     *
     * @param character - character, which receive item
     * @param itemId    - itemId, of added item
     */
    public void giveItem(final GameCharacter character, final int itemId) {
        GameItem gameItem = createGameItem(itemId);

        character.getInventory().giveItem(gameItem);
    }

    /**
     * Creates and add list of items to character.
     * Synchronize {@link CharacterInventory} for performing atomic {@link #giveItem(GameCharacter, int)} operation.
     *
     * @param character - character, which receive item
     * @param itemIds   - list of itemIds, which will be added to character
     */
    public void giveItems(final GameCharacter character, final List<Integer> itemIds) {
        synchronized (character.getInventory()) {
            for (final int itemId : itemIds) {
                giveItem(character, itemId);
            }
        }
    }

    private GameItem createGameItem(final int itemId) {
        assert itemDatasHolder.getItemData().containsKey(itemId);

        final ItemData itemData = itemDatasHolder.getItemData().get(itemId);

        final GameItem gameItem = new GameItem(itemIdPool.borrow(), itemData);

        log.trace("Created new item({}) with itemId {} and objectId {}",
                gameItem.getItemData().getName(),
                gameItem.getItemData().getItemId(),
                gameItem.getObjectId());

        return gameItem;
    }
}
