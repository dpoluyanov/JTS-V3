package ru.jts_dev.gameserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Camelion
 * @since 17.01.16
 */
@Entity
public class GameItem {
    @Id
    @GeneratedValue
    private int objectId;
}
