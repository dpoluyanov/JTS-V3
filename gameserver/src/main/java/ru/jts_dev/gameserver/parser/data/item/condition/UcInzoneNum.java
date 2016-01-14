package ru.jts_dev.gameserver.parser.data.item.condition;

import java.util.List;

/**
 * @author Camelion
 * @since 14.01.16
 */
public class UcInzoneNum extends Condition {
    private final List<Integer> instanceZones;

    public UcInzoneNum(List<Integer> instanceZones) {
        this.instanceZones = instanceZones;
    }
}
