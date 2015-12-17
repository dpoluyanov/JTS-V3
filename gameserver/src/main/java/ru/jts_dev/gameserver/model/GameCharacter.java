package ru.jts_dev.gameserver.model;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.ai.AiVariablesHolder;

/**
 * @author Camelion
 * @since 13.12.15
 */
public class GameCharacter implements AiVariablesHolder {
    private String name;
    private int objectId;
    private String accountName;
    private int sex;
    private int raceId;
    private int classId;
    private double hp;
    private double mp;
    private int sp;
    private long exp;
    private int level;
    private int hairStyle;
    private int hairColor;
    private int face;
    private double maxHP;

    private Vector3D vector3D;
    private boolean moving;

    private AiObject aiObject = new AiObject(this);

    public String getName() {
        return name;
    }

    public int getObjectId() {
        return objectId;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getSex() {
        return sex;
    }

    public int getRaceId() {
        return raceId;
    }

    public int getClassId() {
        return classId;
    }

    public double getHp() {
        return hp;
    }

    public double getMp() {
        return mp;
    }

    public int getSp() {
        return sp;
    }

    public long getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public int getHairStyle() {
        return hairStyle;
    }

    public int getHairColor() {
        return hairColor;
    }

    public int getFace() {
        return face;
    }

    public double getMaxHP() {
        return maxHP;
    }

    public Vector3D getVector3D() {
        return vector3D;
    }

    public void setVector3D(Vector3D vector3D) {
        this.vector3D = vector3D;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
