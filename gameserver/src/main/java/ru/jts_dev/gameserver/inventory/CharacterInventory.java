package ru.jts_dev.gameserver.inventory;

import ru.jts_dev.gameserver.model.GameItem;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Camelion
 * @since 17.01.16
 */
@Embeddable
public class CharacterInventory {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    @OneToMany
    private List<GameItem> items = new ArrayList<>();

    /* package */ void giveItem(GameItem item) {
        writeLock.lock();
        try {
            items.add(item);
        } finally {
            writeLock.unlock();
        }
    }
}
