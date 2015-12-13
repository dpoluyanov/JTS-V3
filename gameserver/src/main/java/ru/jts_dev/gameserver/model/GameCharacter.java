package ru.jts_dev.gameserver.model;

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
    private int x;
    private int y;
    private int z;
    private double hp;
    private double mp;
    private int sp;
    private long exp;
    private int level;
    private int hairStyle;
    private int hairColor;
    private int face;
    private double maxHP;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
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
}
