package ru.jts_dev.gameserver.parser.data.item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Camelion
 * @since 07.01.16
 */
public class SetData {
    private final int setId;
    private final int slotChest;
    private List<Integer> slotLegs = new ArrayList<>();
    private List<Integer> slotHead = new ArrayList<>();
    private List<Integer> slotGloves = new ArrayList<>();
    private List<Integer> slotFeet = new ArrayList<>();
    private List<Integer> slotLhand = new ArrayList<>();
    private String slotAdditional;
    private String setSkill;
    private String setEffectSkill;
    private String setAdditionalEffectSkill;
    private int setAdditional2Condition;
    private String setAdditional2EffectSkill;
    private List<Integer> strInc;
    private List<Integer> conInc;
    private List<Integer> dexInc;
    private List<Integer> intInc;
    private List<Integer> menInc;
    private List<Integer> witInc;

    public SetData(int setId, int slotChest) {
        this.setId = setId;
        this.slotChest = slotChest;
    }

    public List<Integer> getSlotLegs() {
        return slotLegs;
    }

    public void setSlotLegs(List<Integer> slotLegs) {
        this.slotLegs = slotLegs;
    }

    public List<Integer> getSlotHead() {
        return slotHead;
    }

    public void setSlotHead(List<Integer> slotHead) {
        this.slotHead = slotHead;
    }

    public List<Integer> getSlotGloves() {
        return slotGloves;
    }

    public void setSlotGloves(List<Integer> slotGloves) {
        this.slotGloves = slotGloves;
    }

    public List<Integer> getSlotFeet() {
        return slotFeet;
    }

    public void setSlotFeet(List<Integer> slotFeet) {
        this.slotFeet = slotFeet;
    }

    public List<Integer> getSlotLhand() {
        return slotLhand;
    }

    public void setSlotLhand(List<Integer> slotLhand) {
        this.slotLhand = slotLhand;
    }

    public String getSlotAdditional() {
        return slotAdditional;
    }

    public void setSlotAdditional(String slotAdditional) {
        this.slotAdditional = slotAdditional;
    }

    public String getSetSkill() {
        return setSkill;
    }

    public void setSetSkill(String setSkill) {
        this.setSkill = setSkill;
    }

    public String getSetEffectSkill() {
        return setEffectSkill;
    }

    public void setSetEffectSkill(String setEffectSkill) {
        this.setEffectSkill = setEffectSkill;
    }

    public String getSetAdditionalEffectSkill() {
        return setAdditionalEffectSkill;
    }

    public void setSetAdditionalEffectSkill(String setAdditionalEffectSkill) {
        this.setAdditionalEffectSkill = setAdditionalEffectSkill;
    }

    public int getSetAdditional2Condition() {
        return setAdditional2Condition;
    }

    public void setSetAdditional2Condition(int setAdditional2Condition) {
        this.setAdditional2Condition = setAdditional2Condition;
    }

    public String getSetAdditional2EffectSkill() {
        return setAdditional2EffectSkill;
    }

    public void setSetAdditional2EffectSkill(String setAdditional2EffectSkill) {
        this.setAdditional2EffectSkill = setAdditional2EffectSkill;
    }

    public List<Integer> getStrInc() {
        return strInc;
    }

    public void setStrInc(List<Integer> strInc) {
        this.strInc = strInc;
    }

    public List<Integer> getConInc() {
        return conInc;
    }

    public void setConInc(List<Integer> conInc) {
        this.conInc = conInc;
    }

    public List<Integer> getDexInc() {
        return dexInc;
    }

    public void setDexInc(List<Integer> dexInc) {
        this.dexInc = dexInc;
    }

    public List<Integer> getIntInc() {
        return intInc;
    }

    public void setIntInc(List<Integer> intInc) {
        this.intInc = intInc;
    }

    public List<Integer> getMenInc() {
        return menInc;
    }

    public void setMenInc(List<Integer> menInc) {
        this.menInc = menInc;
    }

    public List<Integer> getWitInc() {
        return witInc;
    }

    public void setWitInc(List<Integer> witInc) {
        this.witInc = witInc;
    }

    public int getSlotChest() {
        return slotChest;
    }
}
