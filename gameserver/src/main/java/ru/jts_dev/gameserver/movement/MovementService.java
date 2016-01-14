package ru.jts_dev.gameserver.movement;

import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.packets.out.MoveToLocation;
import ru.jts_dev.gameserver.packets.out.StopMove;
import ru.jts_dev.gameserver.service.GameSessionService;

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
    private GameSessionService sessionService;

    @Autowired
    public MovementService(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public void moveTo(GameCharacter character, Vector3D end) {
        Vector3D start = character.getVector3D();

        // TODO speed
        float speed = 100;

        Line line = new Line(start, end, 1.0D);
        double distance = start.distance(end);
        Vector3D direction = line.getDirection();
        character.setRotation(new Rotation(start, direction));
        character.setVector3D(start);
        character.setMoving(true);

        scheduler.schedule((Runnable) () -> {
            if (character.isMoving()) {
                Vector3D temp = character.getVector3D().add(speed, direction);
                sessionService.send(character, new MoveToLocation(character, temp));
                character.setVector3D(temp);

                if (start.distance(character.getVector3D()) >= distance) {
                    sessionService.send(character, new StopMove(character));
                    character.setVector3D(end);
                    character.setMoving(false);
                }
            }
        }, 500L, TimeUnit.MILLISECONDS);
    }
}
