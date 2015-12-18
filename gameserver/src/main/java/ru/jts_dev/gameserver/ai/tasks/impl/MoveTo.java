package ru.jts_dev.gameserver.ai.tasks.impl;

import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.ai.tasks.Task;
import ru.jts_dev.gameserver.model.GameCharacter;

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
    public void act(final AiObject aiObject, GameCharacter gameCharacter) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        double x = gameCharacter.getVector3D().getX() + random.nextInt(100);
        double y = gameCharacter.getVector3D().getY() + random.nextInt(100);
        double z = gameCharacter.getVector3D().getZ() + random.nextInt(100);
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
