/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

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
