package ru.jts_dev.gameserver.util;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.springframework.stereotype.Component;

import static org.apache.commons.math3.geometry.euclidean.threed.RotationConvention.VECTOR_OPERATOR;

/**
 * @author Java-man
 * @since 26.01.2016
 */
@Component
public final class RotationUtils {
    private static final int CIRCLE_DEGREES = 360;
    private static final double HEADING_PER_DEGREE = 65536.0D / 360.0D;    // 182.044444444

    public int convertAngleToClientHeading(double angle) {
        if (angle < 0)
            angle = CIRCLE_DEGREES + angle;
        return (int) (angle * HEADING_PER_DEGREE);
    }


    public double convertClientHeadingToAngle(int heading) {
        return heading / HEADING_PER_DEGREE;
    }

    public Rotation clientRotation(Rotation rotation, double angle, int side) {
        if (side == 1)
            return rotateOnRight(rotation, angle);
        else
            return rotateOnLeft(rotation, angle);
    }

    public Rotation rotateOnLeft(Rotation rotation, double angle) {
        Vector3D axis = rotation.getAxis(VECTOR_OPERATOR);
        return rotation.applyTo(new Rotation(axis, angle, VECTOR_OPERATOR));
    }

    public Rotation rotateOnRight(Rotation rotation, double angle) {
        Vector3D axis = rotation.getAxis(VECTOR_OPERATOR);
        return rotation.applyInverseTo(new Rotation(axis, angle, VECTOR_OPERATOR));
    }
}
