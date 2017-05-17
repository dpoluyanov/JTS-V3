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

import ru.jts_dev.gameserver.model.GameItem;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static javax.persistence.CascadeType.ALL;

/**
 * This class represent character inventory.
 * All operations on this inventory items is synchronized and can be used in concurrent environment.
 * For performing atomic operation, for example: check common items size and add item, if size less than 60
 * inventory should be synchronized manually as follow:
 * <pre>{@code
 *      synchronized(characterInventory) {
 *          if(commonItemsSize() < 60) {
 *              giveItem(item);
 *          }
 *      }
 * }
 * </pre>
 * <p>
 * That's all doing in {@link InventoryService},
 * and it strongly recommended for all inventory operations.
 *
 * @author Camelion
 * @since 17.01.16
 */
@Embeddable
public class CharacterInventory {
    @OneToMany(cascade = ALL)
    private List<GameItem> items = new ArrayList<>(8);

    final synchronized void giveItem(final GameItem item) {
        items.add(item);
    }

    final synchronized void forEach(final Consumer<GameItem> consumer) {
        items.forEach(consumer);
    }

    /**
     * Returns unmodifiable representation of character {@link #items}.
     * Received list should be used only for reading purposes, not for write.
     *
     * @return - character items
     */
    final List<GameItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    final int commonItemsSize() {
        return items.size();
    }
}
