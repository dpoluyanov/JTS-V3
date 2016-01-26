package ru.jts_dev.gameserver.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Java-man
 * @since 04.01.2016
 */
public final class GameTimeConstants {
    public static final ZonedDateTime MIN_DATE_TIME = ZonedDateTime
            .of(LocalDate.ofYearDay(0, 1), LocalTime.MIN, ZoneId.systemDefault());

    private GameTimeConstants() {
    }
}
