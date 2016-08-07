package ru.jts_dev.common.id.impl.fastbitset;

import javolution.util.FastBitSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.jts_dev.common.id.IdPool;

import javax.annotation.PostConstruct;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Java-man
 * @since 30.07.2016
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public class FastBitSetIdPool implements IdPool {
    private final Logger logger = LoggerFactory.getLogger(FastBitSetIdPool.class);

    @Value("${jts.common.bitset.max-size:#{T(java.lang.Integer).MAX_VALUE}}")
    private int bitSetSize;
    private FastBitSetAllocator allocator;
    private final Lock lock = new ReentrantLock();

    @PostConstruct
    private void postConstruct() {
        allocator = new FastBitSetAllocator(bitSetSize);
    }

    @Override
    public int borrow() {
        final int id = allocate();
        logger.trace("allocated id: {}", id);
        return id;
    }

    @Override
    public void release(final int id) {
        releaseId(id);
        logger.trace("released id: {}", id);
    }

    private int allocate() {
        lock.lock();
        try {
            final int index = allocator.nextFreeIndex();
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

    private static final class FastBitSetAllocator {
        private final FastBitSet bitSet;
        private final int bitSetSize;

        FastBitSetAllocator(final int bitSetSize) {
            this.bitSetSize = bitSetSize;
            bitSet = new FastBitSet();
        }

        void markFree(final int index) {
            if (index <= 0) {
                throw new IndexOutOfBoundsException("index must be > 0 current: " + index);
            }
            bitSet.clear(index);
        }

        void markUsed(final int index) {
            if (index <= 0) {
                throw new IndexOutOfBoundsException("index must be > 0 current: " + index);
            }
            bitSet.set(index);
        }

        int nextFreeIndex() {
            final int nextClear = bitSet.nextClearBit(1);
            return nextClear;
        }
    }
}