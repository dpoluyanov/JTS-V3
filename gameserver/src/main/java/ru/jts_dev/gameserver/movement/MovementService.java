package ru.jts_dev.gameserver.movement;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.model.GameCharacter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Java-man
 * @since 13.12.2015
 */
@Service
public class MovementService {
    private final ScheduledExecutorService scheduler;

    @Autowired
    public MovementService(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public void moveTo(GameCharacter character, Vector3D end) {
        Vector3D start = character.getVector3D();

        // TODO speed
        float speed = 100;

        double distance = start.distance(end);
        Vector3D direction = end.subtract(start).normalize();
        character.setVector3D(start);
        character.setMoving(true);

        scheduler.schedule((Runnable) () -> {
            if (character.isMoving()) {
                character.setVector3D(character.getVector3D().add(speed, direction));
                if (start.distance(character.getVector3D()) >= distance) {
                    character.setVector3D(end);
                    character.setMoving(false);
                }
            }
        }, 500, TimeUnit.MILLISECONDS);
    }
}
