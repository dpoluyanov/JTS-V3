package ru.jts_dev.common.id;

import java.util.BitSet;
import java.util.concurrent.locks.Condition;

/**
 * @author Java-man
 * @since 25.01.2016
 */
public final class BitSetAllocator {
    private final BitSet bits;

    private final int bitSetSize;
    private int bitsAvailable;
    private Condition availableCond;

    public BitSetAllocator(final int bitSetSize) {
        this.bitSetSize = bitSetSize;
        bitsAvailable = bitSetSize;
        bits = new BitSet(bitSetSize);
    }

    public int getBitsAvailable() {
        return bitsAvailable;
    }

    public int getBitSetSize() {
        return bitSetSize;
    }

    public int nextFreeIndex(Condition signalWhenAvail) {
        int index = nextFreeIndex();
        if (index == -1) {
            availableCond = signalWhenAvail;
        }
        return index;
    }

    public void clear() {
        bitsAvailable = bitSetSize;
        bits.clear();
    }

    public void markFree(int index) {
        if (index <= 0 || index >= bitSetSize) {
            throw new RuntimeException("index must be > 0 and < " + bitSetSize);
        }
        if (bits.get(index)) {
            ++bitsAvailable;
        }
        bits.clear(index);

        if (availableCond != null) {
            availableCond.signalAll();
            availableCond = null;
        }
    }

    public void markUsed(int index) {
        if (index <= 0 || index >= bitSetSize) {
            throw new RuntimeException("index must be > 0 and < " + bitSetSize);
        }
        if (!bits.get(index)) {
            --bitsAvailable;
        }
        bits.set(index);
    }

    public int nextFreeIndex() {
        int nextClear = bits.nextClearBit(1);
        if (nextClear >= bitSetSize)
            return -1;
        return nextClear;
    }
}
