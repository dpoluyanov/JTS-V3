package ru.jts_dev.common.id;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Java-man
 * @since 21.01.2016
 */
//@Component
//@Scope(SCOPE_PROTOTYPE)
public final class QueueIdPool implements IdPool {
    private final Queue<Integer> pool = new ConcurrentLinkedQueue<>(
            IntStream.rangeClosed(0, Integer.MAX_VALUE).boxed().collect(Collectors.toList()));

    @Override
    public int borrow() {
        return pool.poll();
    }

    @Override
    public void release(int id) {
        pool.offer(id);
    }
}
