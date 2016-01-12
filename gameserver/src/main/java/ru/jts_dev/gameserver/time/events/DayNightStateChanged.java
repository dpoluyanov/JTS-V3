package ru.jts_dev.gameserver.time.events;

import org.springframework.context.ApplicationEvent;

/**
 * @author Java-man
 * @since 12.01.2016
 */
public class DayNightStateChanged extends ApplicationEvent {
    private final boolean nowDay;

    public DayNightStateChanged(boolean nowDay) {
        super(nowDay);
        this.nowDay = nowDay;
    }

    public boolean isNowDay() {
        return nowDay;
    }
}