package ru.jts_dev.gameserver.model;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.hibernate.validator.constraints.Range;
import ru.jts_dev.gameserver.ai.AiObject;
import ru.jts_dev.gameserver.parser.data.CharacterStat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ru.jts_dev.gameserver.packets.out.CharacterCreateFail.REASON_CREATION_FAILED;

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

    // TODO: 25.12.15 computed level?
    @Column
    private int level;

    @Embedded
    private CharacterStat stat;

    @OneToMany
    private List<GameItem> inventory = new ArrayList<>();

    @Transient
    private String connectionId;

    // TODO: 21.12.15 should be calculable stat
    @Transient
    private double maxHp, maxMp;

    @Transient
    private Vector3D vector3D = new Vector3D(0, 0, 0);
    @Transient
    private Rotation rotation;
    @Transient
    private boolean moving;

    @Transient
    private AiObject aiObject = new AiObject(this);

    public double getHp() {
        return hp;
    }

    public double getMaxMp() {
        return maxMp;
    }

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

    public double getMaxHp() {
        return maxHp;
    }

    public Vector3D getVector3D() {
        return vector3D;
    }

    public void setVector3D(Vector3D vector3D) {
        this.vector3D = vector3D;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
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

    /**
     * this method only for hibernate mapping!!! NOT FOR USE!!!
     *
     * @return x coordinate of vector3D
     */
    @Access(AccessType.PROPERTY)
    @Column(name = "x")
    private double getX() {
        return vector3D.getX();
    }

    /**
     * this method only for hibernate mapping!!! NOT FOR USE!!!
     *
     * @param x - x character coordinate from db
     */
    private void setX(double x) {
        vector3D = new Vector3D(x, vector3D.getY(), vector3D.getZ());
    }

    /**
     * this method only for hibernate mapping!!! NOT FOR USE!!!
     *
     * @return y coordinate of vector3D
     */
    @Access(AccessType.PROPERTY)
    @Column(name = "y")
    private double getY() {
        return vector3D.getY();
    }

    /**
     * this method only for hibernate mapping!!! NOT FOR USE!!!
     *
     * @param y - y character coordinate from db
     */
    private void setY(double y) {
        vector3D = new Vector3D(vector3D.getX(), y, vector3D.getZ());
    }

    /**
     * this method only for hibernate mapping!!! NOT FOR USE!!!
     *
     * @return z coordinate of vector3D
     */
    @Access(AccessType.PROPERTY)
    @Column(name = "z")
    private double getZ() {
        return vector3D.getZ();
    }

    /**
     * this method only for hibernate mapping!!! NOT FOR USE!!!
     *
     * @param z - z character coordinate from db
     */
    private void setZ(double z) {
        vector3D = new Vector3D(vector3D.getX(), vector3D.getY(), z);
    }

    public boolean isLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(boolean lastUsed) {
        this.lastUsed = lastUsed;
    }

    public CharacterStat getStat() {
        return stat;
    }

    public void setStat(CharacterStat stat) {
        this.stat = stat;
    }

    public List<GameItem> getInventory() {
        return Collections.synchronizedList(inventory);
    }
}
