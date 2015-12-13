package ru.jts_dev.gameserver.ai.tasks.impl;


import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.ai.AiVariablesHolder;
import ru.jts_dev.gameserver.ai.tasks.Task;

/**
 * @author Java-man
 */
public class Attack extends Task {
    @Override
    public void reset() {
        start();
    }

    @Override
    public void act(final AiObject aiObject, AiVariablesHolder aiVariablesHolder) {
        /*final Optional<? extends Creature> target = holder.getTarget();
		aiObject.doAttack(target.get());
		succeed();*/
    }

    @Override
    public boolean isMeetRequirements(final AiObject aiObject) {
        return true;
        //return !aiObject.isAttackingDisabled() && holder.getTarget().isPresent() && holder.getTarget().get().isAttackable(aiObject);
    }

    @Override
    public int getWeight() {
        return 1000;
    }
}
