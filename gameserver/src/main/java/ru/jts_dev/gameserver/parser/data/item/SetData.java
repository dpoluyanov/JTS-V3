/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.jts_dev.gameserver.parser.data.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class designed to be immutable, thread safe set definition.
 * Instances of this class can be shared between multiple characters without any limitations.
 *
 * @author Camelion
 * @since 07.01.16
 */
public final class SetData {
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

    public SetData(final int setId, final int slotChest) {
        this.setId = setId;
        this.slotChest = slotChest;
    }

    public List<Integer> getSlotLegs() {
        return Collections.unmodifiableList(slotLegs);
    }

    void setSlotLegs(final List<Integer> slotLegs) {
        this.slotLegs = slotLegs;
    }

    public List<Integer> getSlotHead() {
        return Collections.unmodifiableList(slotHead);
    }

    void setSlotHead(final List<Integer> slotHead) {
        this.slotHead = slotHead;
    }

    public List<Integer> getSlotGloves() {
        return Collections.unmodifiableList(slotGloves);
    }

    void setSlotGloves(List<Integer> slotGloves) {
        this.slotGloves = slotGloves;
    }

    public List<Integer> getSlotFeet() {
        return Collections.unmodifiableList(slotFeet);
    }

    void setSlotFeet(final List<Integer> slotFeet) {
        this.slotFeet = slotFeet;
    }

    public List<Integer> getSlotLhand() {
        return Collections.unmodifiableList(slotLhand);
    }

    void setSlotLhand(final List<Integer> slotLhand) {
        this.slotLhand = slotLhand;
    }

    public String getSlotAdditional() {
        return slotAdditional;
    }

    void setSlotAdditional(final String slotAdditional) {
        this.slotAdditional = slotAdditional;
    }

    public String getSetSkill() {
        return setSkill;
    }

    void setSetSkill(final String setSkill) {
        this.setSkill = setSkill;
    }

    public String getSetEffectSkill() {
        return setEffectSkill;
    }

    void setSetEffectSkill(final String setEffectSkill) {
        this.setEffectSkill = setEffectSkill;
    }

    public String getSetAdditionalEffectSkill() {
        return setAdditionalEffectSkill;
    }

    void setSetAdditionalEffectSkill(final String setAdditionalEffectSkill) {
        this.setAdditionalEffectSkill = setAdditionalEffectSkill;
    }

    public int getSetAdditional2Condition() {
        return setAdditional2Condition;
    }

    void setSetAdditional2Condition(final int setAdditional2Condition) {
        this.setAdditional2Condition = setAdditional2Condition;
    }

    public String getSetAdditional2EffectSkill() {
        return setAdditional2EffectSkill;
    }

    void setSetAdditional2EffectSkill(final String setAdditional2EffectSkill) {
        this.setAdditional2EffectSkill = setAdditional2EffectSkill;
    }

    public List<Integer> getStrInc() {
        return Collections.unmodifiableList(strInc);
    }

    void setStrInc(final List<Integer> strInc) {
        this.strInc = strInc;
    }

    public List<Integer> getConInc() {
        return Collections.unmodifiableList(conInc);
    }

    void setConInc(final List<Integer> conInc) {
        this.conInc = conInc;
    }

    public List<Integer> getDexInc() {
        return Collections.unmodifiableList(dexInc);
    }

    void setDexInc(final List<Integer> dexInc) {
        this.dexInc = dexInc;
    }

    public List<Integer> getIntInc() {
        return Collections.unmodifiableList(intInc);
    }

    void setIntInc(final List<Integer> intInc) {
        this.intInc = intInc;
    }

    public List<Integer> getMenInc() {
        return Collections.unmodifiableList(menInc);
    }

    void setMenInc(final List<Integer> menInc) {
        this.menInc = menInc;
    }

    public List<Integer> getWitInc() {
        return Collections.unmodifiableList(witInc);
    }

    void setWitInc(final List<Integer> witInc) {
        this.witInc = witInc;
    }

    public int getSlotChest() {
        return slotChest;
    }
}
