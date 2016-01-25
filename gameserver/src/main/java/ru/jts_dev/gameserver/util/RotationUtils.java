package ru.jts_dev.gameserver.util;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.springframework.stereotype.Component;

/**
 * @author Java-man
 * @since 26.01.2016
 */
@Component
public class RotationUtils {
    public int convertAngleToClientHeading(double angle) {
        if (angle < 0)
            angle = 360 + angle;
        return (int) (angle * 182.044444444);
    }


    public double convertClientHeadingToAngle(int heading) {
        return heading / 182.044444444;
    }

    public Rotation clientRotation(Rotation rotation, double angle, int side) {
        if (side == 1)
            return rotateOnRight(rotation, angle);
        else
            return rotateOnLeft(rotation, angle);
    }

    public Rotation rotateOnLeft(Rotation rotation, double angle) {
        return rotation.applyTo(new Rotation(rotation.getAxis(), angle));
    }

    public Rotation rotateOnRight(Rotation rotation, double angle) {
        return rotation.applyInverseTo(new Rotation(rotation.getAxis(), angle));
    }
}
