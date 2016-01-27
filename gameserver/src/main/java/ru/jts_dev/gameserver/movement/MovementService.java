package ru.jts_dev.gameserver.movement;

import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jts_dev.gameserver.model.GameCharacter;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.packets.out.MoveToLocation;
import ru.jts_dev.gameserver.packets.out.StopMove;
import ru.jts_dev.gameserver.service.BroadcastService;
import ru.jts_dev.gameserver.util.RotationUtils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Java-man
 * @since 13.12.2015
 */
@Service
public class MovementService {
    private static final long MOVE_TASK_INTERVAL_MILLIS = 200L;
    public static final double MOVE_SPEED_MULTIPLIER = MOVE_TASK_INTERVAL_MILLIS / 1000.0D;

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;
    @Autowired
    private BroadcastService broadcastService;
    @Autowired
    private RotationUtils rotationUtils;

    public void moveTo(final GameSession session, final GameCharacter character, final Vector3D end) {
        final Vector3D start = character.getVector3D();

        final Line line = new Line(start, end, 1.0D);
        final double distance = start.distance(end);
        final Vector3D direction = line.getDirection();
        character.setRotation(new Rotation(start, end));
        character.setVector3D(start);
        character.setMoving(true);

        final Runnable moveTask = new MoveTask(session, character, start, end, direction, distance, true);
        scheduledExecutorService.schedule(moveTask, MOVE_TASK_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
    }

    private final class MoveTask implements Runnable {
        private final GameSession session;
        private final GameCharacter character;
        private final Vector3D start;
        private final Vector3D end;
        private final Vector3D direction;
        private final double distance;
        private final boolean first;

        private MoveTask(final GameSession session, final GameCharacter character, final Vector3D start,
                         final Vector3D end, final Vector3D direction, final double distance, final boolean first) {
            this.session = session;
            this.character = character;
            this.start = start;
            this.end = end;
            this.direction = direction;
            this.distance = distance;
            this.first = first;
        }

        @Override
        public void run() {
            if (character.isMoving()) {
                final double speed = 200.0D; // TODO speed
                final Vector3D temp = character.getVector3D().add(speed * MOVE_SPEED_MULTIPLIER, direction);
                if (first)
                    broadcastService.send(session, new MoveToLocation(character, end));
                character.setVector3D(temp);

                if (start.distance(character.getVector3D()) >= distance) {
                    final int clientHeading = rotationUtils.convertAngleToClientHeading(character.getRotation().getAngle());
                    broadcastService.send(session, new StopMove(character, clientHeading));
                    character.setVector3D(end);
                    character.setMoving(false);
                } else {
                    final Runnable moveTask = new MoveTask(session, character, start, end, direction, distance, false);
                    scheduledExecutorService.schedule(moveTask, MOVE_TASK_INTERVAL_MILLIS, TimeUnit.MILLISECONDS);
                }
            }
        }
    }
}
