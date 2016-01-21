package ru.jts_dev.common.id;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Java-man
 * @since 21.01.2016
 */
@Component
@Scope(SCOPE_PROTOTYPE)
public final class QueueIdPool implements IdPool {
    private final Queue<Integer> queue = new ConcurrentLinkedQueue<>(
            IntStream.rangeClosed(0, Integer.MAX_VALUE).boxed().collect(Collectors.toList()));

    @Override
    public int borrow() {
        return queue.poll();
    }

    @Override
    public void release(int objectId) {
        queue.offer(objectId);
    }
}
