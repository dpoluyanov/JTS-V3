package ru.jts_dev.gameserver.model;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.hibernate.validator.constraints.Range;
import ru.jts_dev.gameserver.ai.AiObject;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import static ru.jts_dev.gameserver.packets.out.CharacterCreateFail.REASON_CREATION_FAILED;
import static ru.jts_dev.gameserver.parser.data.CharacterStat.RACE_HUMAN;
import static ru.jts_dev.gameserver.parser.data.CharacterStat.RACE_KAMAEL;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Entity
public class GameCharacter {
    @Id
    @GeneratedValue
    private int objectId;

    @Pattern(regexp = "[A-Za-z0-9]{4,16}", message = "4-16 ENG symbols")
    @Column(unique = true)
    private String name;

    @Range(min = 0, max = 1)
    @Column
    private int sex;

    @Range(min = RACE_HUMAN, max = RACE_KAMAEL)
    @Column
    private int raceId;

    // TODO: 26.12.15 move to our validator
    /*
    @Digits.List({
            @Digits(integer = CLASS_HUMAN_FIGHTER, fraction = 0, message = ""),
            @Digits(integer = CLASS_HUMAN_MAGICAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ELF_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ELF_MAGICAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_DARKELF_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_DARKELF_MAGICAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ORC_FIGHTER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_ORC_SHAMAN, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_DWARF_APPRENTICE, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_KAMAEL_M_SOLDIER, fraction = 0, message = REASON_CREATION_FAILED),
            @Digits(integer = CLASS_KAMAEL_M_SOLDIER, fraction = 0, message = REASON_CREATION_FAILED),
    })*/
    @Column
    private int classId;

    @Range(min = 0, max = 6, message = REASON_CREATION_FAILED)
    @Column
    private int hairStyle;

    @Range(min = 0, max = 3, message = REASON_CREATION_FAILED)
    @Column
    private int hairColor;

    @Range(min = 0, max = 2, message = REASON_CREATION_FAILED)
    @Column
    private int face;

    @Range(min = 0)
    @Column
    private double hp;

    @Range(min = 0)
    @Column
    private double mp;

    @Range(min = 0)
    @Column
    private int sp;

    @Range(min = 0)
    @Column
    private long exp;

    @Column
    private boolean lastUsed;
    // TODO: 26.12.15 pattern for account Name
    @Column
    private String accountName;
    // TODO: 25.12.15 calculable level?
    @Column
    private int level;
    @Transient
    private Vector3D vector3D = new Vector3D(0, 0, 0);
    // TODO: 21.12.15 should be calculable stat
    @Transient
    private double maxHP;
    @Transient
    private boolean moving;
    @Transient
    private AiObject aiObject = new AiObject(this);

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getObjectId() {
        return objectId;
    }

    public String getLogin() {
        return accountName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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

    public void setHairStyle(int hairStyle) {
        this.hairStyle = hairStyle;
    }

    public int getHairColor() {
        return hairColor;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
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

    public AiObject getAiObject() {
        return aiObject;
    }

    // only for hibernate mapping
    @Access(AccessType.PROPERTY)
    @Column(name = "x")
    private double getX() {
        return vector3D.getX();
    }

    private void setX(double x) {
        vector3D = new Vector3D(x, vector3D.getY(), vector3D.getZ());
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "y")
    private double getY() {
        return vector3D.getY();
    }

    private void setY(double y) {
        vector3D = new Vector3D(vector3D.getX(), y, vector3D.getZ());
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "z")
    private double getZ() {
        return vector3D.getZ();
    }

    private void setZ(double z) {
        vector3D = new Vector3D(vector3D.getX(), vector3D.getY(), z);
    }

    public boolean isLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(boolean lastUsed) {
        this.lastUsed = lastUsed;
    }
}
