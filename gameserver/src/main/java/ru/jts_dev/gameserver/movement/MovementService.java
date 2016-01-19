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
    private static final long MOVE_TASK_INTERVAL_MILLIS = 200L;
    private static final long MOVE_SPEED_MULTIPLIER = 200L;

    private final ScheduledExecutorService scheduler;

    @Autowired
    private GameSessionService sessionService;

    @Autowired
    public MovementService(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;
    }

    public void moveTo(GameCharacter character, Vector3D end) {
        Vector3D start = character.getVector3D();

        Line line = new Line(start, end, 1.0D);
        double distance = start.distance(end);
        Vector3D direction = line.getDirection();
        character.setRotation(new Rotation(start, direction));
        character.setVector3D(start);
        character.setMoving(true);

        scheduler.schedule(new MoveTask(character, start, end, direction, distance), MOVE_TASK_INTERVAL_MILLIS,
                TimeUnit.MILLISECONDS);
    }

    private final class MoveTask implements Runnable {
        private final GameCharacter character;
        private final Vector3D start;
        private final Vector3D end;
        private final Vector3D direction;
        private final double distance;

        private MoveTask(GameCharacter character,
                         Vector3D start, Vector3D end, Vector3D direction, double distance) {
            this.character = character;
            this.start = start;
            this.end = end;
            this.direction = direction;
            this.distance = distance;
        }

        @Override
        public void run() {
            if (character.isMoving()) {
                double speed = 100.0D; // TODO speed
                Vector3D temp = character.getVector3D().add(speed * MOVE_SPEED_MULTIPLIER, direction);
                sessionService.send(character, new MoveToLocation(character, temp));
                character.setVector3D(temp);

                if (start.distance(character.getVector3D()) >= distance) {
                    sessionService.send(character, new StopMove(character));
                    character.setVector3D(end);
                    character.setMoving(false);
                } else {
                    scheduler.schedule(
                            new MoveTask(character, start, end, direction, distance),
                            MOVE_TASK_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
                }
            }
        }
    }
}
