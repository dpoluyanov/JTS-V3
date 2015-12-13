package ru.jts_dev.gameserver.ai.tasks;

import ru.jts_dev.gameserver.ai.tasks.impl.MoveTo;

/**
 * Static convenience methods to create tasks
 *
 * @author Java-man
 */
public final class Tasks {
    private Tasks() {
    }

    public static Task sequence(final Task... tasks) {
        final Sequence sequence = new Sequence();
        for (final Task task : tasks) {
            sequence.addTask(task);
        }
        return sequence;
    }

    public static Task selector(final Task... tasks) {
        final Selector selector = new Selector();
        for (final Task task : tasks) {
            selector.addTask(task);
        }
        return selector;
    }

    public static Task conditionalSequence(final Task... tasks) {
        final ConditionalSequence sequence = new ConditionalSequence();
        for (final Task task : tasks) {
            sequence.addTask(task);
        }
        return sequence;
    }

    public static MoveTo moveTo() {
        return new MoveTo();
    }

    public static Task repeatInfinite(final Task task) {
        return new Repeat(task);
    }

    public static Task repeat(final Task task, final int times) {
        return new Repeat(task, times);
    }
}
