package ru.jts_dev.common.id.impl.bitset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.id.IdPool;
import ru.jts_dev.common.id.impl.AllocationException;

import javax.annotation.PostConstruct;
import java.util.BitSet;
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
    private final Lock lock = new ReentrantLock();

    @Value("${jts.common.bitset.max-size:#{T(java.lang.Integer).MAX_VALUE}}")
    private int bitSetSize;
    private BitSetAllocator allocator;

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

    private int allocate() {
        lock.lock();
        try {
            final int index = allocator.nextFreeIndex();
            if (index == -1) {
                throw new AllocationException("No available indexes in pool");
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

    @PostConstruct
    private void postConstruct() {
        // initialize allocator with given size
        allocator = new BitSetAllocator(bitSetSize);
    }

    private static final class BitSetAllocator {
        private final BitSet bits;
        private final int bitSetSize;

        BitSetAllocator(final int bitSetSize) {
            this.bitSetSize = bitSetSize;
            bits = new BitSet(bitSetSize);
        }

        void markFree(final int index) {
            if (index <= 0 || index > bitSetSize) {
                throw new IndexOutOfBoundsException("index must be > 0 and <= " + bitSetSize + " current: " + index);
            }
            bits.clear(index);
        }

        void markUsed(final int index) {
            if (index <= 0 || index > bitSetSize) {
                throw new IndexOutOfBoundsException("index must be > 0 and <= " + bitSetSize + " current: " + index);
            }
            bits.set(index);
        }

        int nextFreeIndex() {
            final int nextClear = bits.nextClearBit(1);
            if (nextClear > bitSetSize)
                return -1;
            return nextClear;
        }
    }
}
