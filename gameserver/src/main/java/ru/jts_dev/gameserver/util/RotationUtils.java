package ru.jts_dev.gameserver.util;

import org.springframework.stereotype.Component;

/**
 * @author Java-man
 * @since 26.01.2016
 */
@Component
public final class RotationUtils {
    private static final int CIRCLE_DEGREES = 360;
    private static final double HEADING_PER_DEGREE = 65536.0D / 360.0D;    // 182.044444444

    public int convertAngleToClientHeading(int angle) {
        if (angle < 0)
            angle = CIRCLE_DEGREES + angle;
        return (int) (angle * HEADING_PER_DEGREE);
    }

    public double convertClientHeadingToAngle(int heading) {
        return heading / HEADING_PER_DEGREE;
    }
}
