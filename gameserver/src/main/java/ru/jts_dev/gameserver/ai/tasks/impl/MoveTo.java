package ru.jts_dev.gameserver.ai.tasks.impl;

import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.ai.AiVariablesHolder;
import ru.jts_dev.gameserver.ai.tasks.Task;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Java-man
 */
public class MoveTo extends Task {
    @Override
    public void reset() {
        start();
    }

    @Override
    public void act(final AiObject aiObject, AiVariablesHolder aiVariablesHolder) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x = aiVariablesHolder.getX() + random.nextInt(100);
        int y = aiVariablesHolder.getX() + random.nextInt(100);
        int z = aiVariablesHolder.getX() + random.nextInt(100);
        // TODO
        //aiObject.moveToLocation(x, y, z, 0, true);
		succeed();
    }

    @Override
    public boolean isMeetRequirements(final AiObject aiObject) {
        return true;
        //return !aiObject.isMovementDisabled();
    }

    @Override
    public int getWeight() {
        return 100;
    }
}
