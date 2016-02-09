package ru.jts_dev.common.id.impl.bitset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.id.IdPool;
import ru.jts_dev.common.id.impl.AllocationException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Java-man
 * @since 24.01.2016
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public final class BitSetIdPool implements IdPool {
    private final Logger logger = LoggerFactory.getLogger(BitSetIdPool.class);

    private final BitSetAllocator allocator = new BitSetAllocator(Integer.MAX_VALUE);
    private final Lock lock = new ReentrantLock();
    private final Condition availableCond = lock.newCondition();

    private int allocate() {
        lock.lock();
        try {
            final int index = allocator.nextFreeIndex();
            if (index == -1) {
                throw new AllocationException("No available indexes in pool.");
            }
            allocator.markUsed(index);
            return index;
        } finally {
            lock.unlock();
        }
    }

    private int allocate(final long time, final TimeUnit unit) throws InterruptedException {
        lock.lock();
        try {
            int index = allocator.nextFreeIndex(availableCond);
            if (index == -1) {
                if (availableCond.await(time, unit)) {
                    index = allocator.nextFreeIndex();
                } else {
                    throw new AllocationException("No available indexes in pool.");
                }
            }
            allocator.markUsed(index);
            return index;
        } finally {
            lock.unlock();
        }
    }

    private void releaseId(final int id) {
        lock.lock();
        try {
            allocator.markFree(id);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int borrow() {
        final int id = allocate();
        logger.debug("allocated id: {}", id);
        return id;
    }

    @Override
    public void release(final int id) {
        releaseId(id);
        logger.debug("released id: {}", id);
    }
}
