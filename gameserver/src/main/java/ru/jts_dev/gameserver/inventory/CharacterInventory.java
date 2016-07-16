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
