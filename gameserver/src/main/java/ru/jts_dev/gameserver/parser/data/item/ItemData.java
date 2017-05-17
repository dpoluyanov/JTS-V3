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

import ru.jts_dev.gameserver.constants.*;
import ru.jts_dev.gameserver.constants.ItemTypes.ArmorType;
import ru.jts_dev.gameserver.constants.ItemTypes.EtcItemType;
import ru.jts_dev.gameserver.constants.ItemTypes.WeaponType;
import ru.jts_dev.gameserver.parser.data.item.condition.Condition;

import java.util.Collections;
import java.util.List;

/**
 * This class designed to be immutable, thread safe item template.
 * Instances of this class can be shared between multiple characters without any limitations.
 *
 * @author Camelion
 * @since 07.01.16
 */
public final class ItemData {
    private final int itemId;
    private final ItemClass itemClass;
    private final String name;
    private final ItemClass itemType;
    private final List<SlotBitType> slotBitTypes;

    private ArmorType armorType;
    private EtcItemType etcItemType;
    private WeaponType weaponType;
    private int delayShareGroup;
    private List<String> itemMultiSkillList;
    private int recipeId;
    private int blessed;
    private int weight;
    private DefaultAction defaultAction;
    private ConsumeType consumeType;
    private int initialCount;
    private int soulshotCount;
    private int spiritshotCount;
    // two size or empty list
    private List<Integer> reducedSoulshot;
    // not used in datas, always empty list
    private List<Integer> reducedSpiritshot;
    // two size or empty list
    private List<Integer> reducedMpConsume;
    private int immediateEffect;
    private int exImmediateEffect;
    private int dropPeriod;
    // -1, or positive value
    private int duration;
    private int useSkillDistime;
    private int period;
    private int equipReuseDelay;
    // TODO: 13.01.16 unknown
    private long price;
    private long defaultPrice;
    // `none` or skill name
    private String itemSkill;
    // `none` or skill name
    private String criticalAttackSkill;
    private String attackSkill;
    private String magicSkill;
    private int magicSkillUnknownValue;
    private String itemSkillEnchantedFour;
    private List<CapsuledItemData> capsuledItems;
    private MaterialType materialType;
    private CrystalType crystalType;
    private int crystalCount;
    private boolean isTrade;
    private boolean isDrop;
    private boolean isDestruct;
    private boolean isPrivateStore;
    private byte keepType;

    private int physicalDamage;
    private int randomDamage;
    private int critical;
    // can't test, because can be any value
    private double hitModify;
    private int avoidModify;
    private int dualFhitRate;
    private int shieldDefense;
    private int shieldDefenseRate;
    private int attackRange;
    private List<Integer> damageRange;
    private int attackSpeed;

    private int reuseDelay;
    private int mpConsume;
    private int magicalDamage;
    private int durability;
    // always false
    private boolean damaged;
    private int physicalDefense;
    private int magicalDefense;
    private int mpBonus;
    // not used in datas, always EMPTY ARRAY
    private List<String> category;
    private int enchanted;
    private AttributeAttack baseAttributeAttack;
    private List<Integer> baseAttributeDefend;
    private String html;
    // can't test
    private boolean magicWeapon;
    private int enchantEnable;
    private boolean elementalEnable;
    private List<String> unequipSkill;
    // can't test
    private boolean forNpc;
    private List<String> itemEquipOption;
    private List<Condition> useCondition;
    private List<Condition> equipCondition;
    private boolean isOlympiadCanUse;
    private boolean canMove;
    private boolean isPremium;
    /**
     * calculated from {@link #slotBitTypes}
     */
    private int slotBitTypeMask;

    public ItemData(final int itemId, final ItemClass itemClass, final String name,
                    final ItemClass itemType, final List<SlotBitType> slotBitTypes) {
        this.itemId = itemId;
        this.itemClass = itemClass;
        this.name = name;
        this.itemType = itemType;
        this.slotBitTypes = slotBitTypes;

        slotBitTypeMask = calcSlotBitTypeMask();
    }

    private int calcSlotBitTypeMask() {
        return slotBitTypes.size() == 1 ? slotBitTypes.get(0).mask()
                : slotBitTypes
                .stream()
                .mapToInt(SlotBitType::mask)
                .reduce((a, b) -> a | b).getAsInt();
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    void setArmorType(ArmorType armorType) {
        this.armorType = armorType;
    }

    public EtcItemType getEtcItemType() {
        return etcItemType;
    }

    void setEtcItemType(EtcItemType etcItemType) {
        this.etcItemType = etcItemType;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    public int getItemId() {
        return itemId;
    }

    public ItemClass getItemClass() {
        return itemClass;
    }

    public String getName() {
        return name;
    }

    public ItemClass getItemType() {
        return itemType;
    }

    public int getDelayShareGroup() {
        return delayShareGroup;
    }

    void setDelayShareGroup(final int delayShareGroup) {
        this.delayShareGroup = delayShareGroup;
    }

    public int getShieldDefenseRate() {
        return shieldDefenseRate;
    }

    void setShieldDefenseRate(final int shieldDefenseRate) {
        this.shieldDefenseRate = shieldDefenseRate;
    }

    public int getShieldDefense() {
        return shieldDefense;
    }

    void setShieldDefense(final int shieldDefense) {
        this.shieldDefense = shieldDefense;
    }

    public List<String> getItemMultiSkillList() {
        return Collections.unmodifiableList(itemMultiSkillList);
    }

    void setItemMultiSkillList(final List<String> itemMultiSkillList) {
        this.itemMultiSkillList = itemMultiSkillList;
    }

    public int getRecipeId() {
        return recipeId;
    }

    void setRecipeId(final int recipeId) {
        this.recipeId = recipeId;
    }

    public int getWeight() {
        return weight;
    }

    void setWeight(final int weight) {
        this.weight = weight;
    }

    public DefaultAction getDefaultAction() {
        return defaultAction;
    }

    void setDefaultAction(final DefaultAction defaultAction) {
        this.defaultAction = defaultAction;
    }

    public ConsumeType getConsumeType() {
        return consumeType;
    }

    void setConsumeType(final ConsumeType consumeType) {
        this.consumeType = consumeType;
    }

    public int getInitialCount() {
        return initialCount;
    }

    void setInitialCount(final int initialCount) {
        this.initialCount = initialCount;
    }

    public int getSoulshotCount() {
        return soulshotCount;
    }

    void setSoulshotCount(final int soulshotCount) {
        this.soulshotCount = soulshotCount;
    }

    public int getSpiritshotCount() {
        return spiritshotCount;
    }

    void setSpiritshotCount(final int spiritshotCount) {
        this.spiritshotCount = spiritshotCount;
    }

    public List<Integer> getReducedSoulshot() {
        return Collections.unmodifiableList(reducedSoulshot);
    }

    void setReducedSoulshot(final List<Integer> reducedSoulshot) {
        this.reducedSoulshot = reducedSoulshot;
    }

    public List<Integer> getReducedSpiritshot() {
        return Collections.unmodifiableList(reducedSpiritshot);
    }

    void setReducedSpiritshot(final List<Integer> reducedSpiritshot) {
        this.reducedSpiritshot = reducedSpiritshot;
    }

    public int getBlessed() {
        return blessed;
    }

    void setBlessed(final int blessed) {
        this.blessed = blessed;
    }

    public List<Integer> getReducedMpConsume() {
        return Collections.unmodifiableList(reducedMpConsume);
    }

    void setReducedMpConsume(final List<Integer> reducedMpConsume) {
        this.reducedMpConsume = reducedMpConsume;
    }

    public int getImmediateEffect() {
        return immediateEffect;
    }

    void setImmediateEffect(final int immediateEffect) {
        this.immediateEffect = immediateEffect;
    }

    public int getExImmediateEffect() {
        return exImmediateEffect;
    }

    void setExImmediateEffect(final int exImmediateEffect) {
        this.exImmediateEffect = exImmediateEffect;
    }

    public int getDropPeriod() {
        return dropPeriod;
    }

    void setDropPeriod(final int dropPeriod) {
        this.dropPeriod = dropPeriod;
    }

    public int getDuration() {
        return duration;
    }

    void setDuration(final int duration) {
        this.duration = duration;
    }

    public int getUseSkillDistime() {
        return useSkillDistime;
    }

    void setUseSkillDistime(final int useSkillDistime) {
        this.useSkillDistime = useSkillDistime;
    }

    public int getPeriod() {
        return period;
    }

    void setPeriod(final int period) {
        this.period = period;
    }

    public int getEquipReuseDelay() {
        return equipReuseDelay;
    }

    void setEquipReuseDelay(final int equipReuseDelay) {
        this.equipReuseDelay = equipReuseDelay;
    }

    public long getPrice() {
        return price;
    }

    void setPrice(final long price) {
        this.price = price;
    }

    public long getDefaultPrice() {
        return defaultPrice;
    }

    void setDefaultPrice(final long defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getItemSkill() {
        return itemSkill;
    }

    void setItemSkill(final String itemSkill) {
        this.itemSkill = itemSkill;
    }

    public String getCriticalAttackSkill() {
        return criticalAttackSkill;
    }

    void setCriticalAttackSkill(final String criticalAttackSkill) {
        this.criticalAttackSkill = criticalAttackSkill;
    }

    public String getAttackSkill() {
        return attackSkill;
    }

    void setAttackSkill(final String attackSkill) {
        this.attackSkill = attackSkill;
    }

    public String getMagicSkill() {
        return magicSkill;
    }

    void setMagicSkill(final String magicSkill) {
        this.magicSkill = magicSkill;
    }

    public int getMagicSkillUnknownValue() {
        return magicSkillUnknownValue;
    }

    void setMagicSkillUnknownValue(final int magicSkillUnknownValue) {
        this.magicSkillUnknownValue = magicSkillUnknownValue;
    }

    public String getItemSkillEnchantedFour() {
        return itemSkillEnchantedFour;
    }

    void setItemSkillEnchantedFour(final String itemSkillEnchantedFour) {
        this.itemSkillEnchantedFour = itemSkillEnchantedFour;
    }

    public List<CapsuledItemData> getCapsuledItems() {
        return Collections.unmodifiableList(capsuledItems);
    }

    void setCapsuledItems(final List<CapsuledItemData> capsuledItems) {
        this.capsuledItems = capsuledItems;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    void setMaterialType(final MaterialType materialType) {
        this.materialType = materialType;
    }

    public CrystalType getCrystalType() {
        return crystalType;
    }

    void setCrystalType(final CrystalType crystalType) {
        this.crystalType = crystalType;
    }

    public int getCrystalCount() {
        return crystalCount;
    }

    void setCrystalCount(final int crystalCount) {
        this.crystalCount = crystalCount;
    }

    void setIsTrade(final boolean isTrade) {
        this.isTrade = isTrade;
    }

    public boolean isTrade() {
        return isTrade;
    }

    public boolean isDrop() {
        return isDrop;
    }

    void setIsDrop(final boolean isDrop) {
        this.isDrop = isDrop;
    }

    public boolean isDestruct() {
        return isDestruct;
    }

    void setIsDestruct(final boolean isDestruct) {
        this.isDestruct = isDestruct;
    }

    public boolean isPrivateStore() {
        return isPrivateStore;
    }

    void setIsPrivateStore(final boolean isPrivateStore) {
        this.isPrivateStore = isPrivateStore;
    }

    public byte getKeepType() {
        return keepType;
    }

    void setKeepType(final byte keepType) {
        this.keepType = keepType;
    }

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    void setPhysicalDamage(final int physicalDamage) {
        this.physicalDamage = physicalDamage;
    }

    public int getRandomDamage() {
        return randomDamage;
    }

    void setRandomDamage(final int randomDamage) {
        this.randomDamage = randomDamage;
    }

    public int getCritical() {
        return critical;
    }

    void setCritical(final int critical) {
        this.critical = critical;
    }

    public double getHitModify() {
        return hitModify;
    }

    void setHitModify(final double hitModify) {
        this.hitModify = hitModify;
    }

    public int getAvoidModify() {
        return avoidModify;
    }

    void setAvoidModify(final int avoidModify) {
        this.avoidModify = avoidModify;
    }

    public int getDualFhitRate() {
        return dualFhitRate;
    }

    void setDualFhitRate(final int dualFhitRate) {
        this.dualFhitRate = dualFhitRate;
    }

    public int getAttackRange() {
        return attackRange;
    }

    void setAttackRange(final int attackRange) {
        this.attackRange = attackRange;
    }

    public List<Integer> getDamageRange() {
        return Collections.unmodifiableList(damageRange);
    }

    void setDamageRange(final List<Integer> damageRange) {
        this.damageRange = damageRange;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    void setAttackSpeed(final int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public int getReuseDelay() {
        return reuseDelay;
    }

    void setReuseDelay(final int reuseDelay) {
        this.reuseDelay = reuseDelay;
    }

    public int getMpConsume() {
        return mpConsume;
    }

    void setMpConsume(final int mpConsume) {
        this.mpConsume = mpConsume;
    }

    public int getMagicalDamage() {
        return magicalDamage;
    }

    void setMagicalDamage(final int magicalDamage) {
        this.magicalDamage = magicalDamage;
    }

    public int getDurability() {
        return durability;
    }

    void setDurability(final int durability) {
        this.durability = durability;
    }

    public boolean isDamaged() {
        return damaged;
    }

    void setDamaged(final boolean damaged) {
        this.damaged = damaged;
    }

    public int getPhysicalDefense() {
        return physicalDefense;
    }

    void setPhysicalDefense(final int physicalDefense) {
        this.physicalDefense = physicalDefense;
    }

    public int getMagicalDefense() {
        return magicalDefense;
    }

    void setMagicalDefense(final int magicalDefense) {
        this.magicalDefense = magicalDefense;
    }

    public int getMpBonus() {
        return mpBonus;
    }

    void setMpBonus(final int mpBonus) {
        this.mpBonus = mpBonus;
    }

    public List<String> getCategory() {
        return Collections.unmodifiableList(category);
    }

    void setCategory(final List<String> category) {
        this.category = category;
    }

    public int getEnchanted() {
        return enchanted;
    }

    void setEnchanted(final int enchanted) {
        this.enchanted = enchanted;
    }

    public List<SlotBitType> getSlotBitTypes() {
        return Collections.unmodifiableList(slotBitTypes);
    }

    public AttributeAttack getBaseAttributeAttack() {
        return baseAttributeAttack;
    }

    void setBaseAttributeAttack(AttributeAttack baseAttributeAttack) {
        this.baseAttributeAttack = baseAttributeAttack;
    }

    public List<Integer> getBaseAttributeDefend() {
        return Collections.unmodifiableList(baseAttributeDefend);
    }

    void setBaseAttributeDefend(final List<Integer> baseAttributeDefend) {
        this.baseAttributeDefend = baseAttributeDefend;
    }

    public String getHtml() {
        return html;
    }

    void setHtml(final String html) {
        this.html = html;
    }

    public boolean isMagicWeapon() {
        return magicWeapon;
    }

    void setMagicWeapon(final boolean magicWeapon) {
        this.magicWeapon = magicWeapon;
    }

    public int getEnchantEnable() {
        return enchantEnable;
    }

    void setEnchantEnable(final int enchantEnable) {
        this.enchantEnable = enchantEnable;
    }

    public boolean isElementalEnable() {
        return elementalEnable;
    }

    void setElementalEnable(final boolean elementalEnable) {
        this.elementalEnable = elementalEnable;
    }

    public List<String> getUnequipSkill() {
        return Collections.unmodifiableList(unequipSkill);
    }

    void setUnequipSkill(final List<String> unequipSkill) {
        this.unequipSkill = unequipSkill;
    }

    public boolean isForNpc() {
        return forNpc;
    }

    void setForNpc(final boolean forNpc) {
        this.forNpc = forNpc;
    }

    public List<String> getItemEquipOption() {
        return Collections.unmodifiableList(itemEquipOption);
    }

    void setItemEquipOption(final List<String> itemEquipOption) {
        this.itemEquipOption = itemEquipOption;
    }

    public List<Condition> getUseCondition() {
        return Collections.unmodifiableList(useCondition);
    }

    void setUseCondition(final List<Condition> useCondition) {
        this.useCondition = useCondition;
    }

    public List<Condition> getEquipCondition() {
        return Collections.unmodifiableList(equipCondition);
    }

    void setEquipCondition(final List<Condition> equipCondition) {
        this.equipCondition = equipCondition;
    }

    public boolean isOlympiadCanUse() {
        return isOlympiadCanUse;
    }

    void setOlympiadCanUse(final boolean olympiadCanUse) {
        isOlympiadCanUse = olympiadCanUse;
    }

    public boolean isCanMove() {
        return canMove;
    }

    void setCanMove(final boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isPremium() {
        return isPremium;
    }

    void setPremium(final boolean premium) {
        isPremium = premium;
    }

    public int getSlotBitTypeMask() {
        return slotBitTypeMask;
    }

    public static final class CapsuledItemData {
        private final String itemName;
        private final int minCount;
        private final int maxCount;
        private final double chance;

        public CapsuledItemData(final String itemName, final int minCount, final int maxCount, final double chance) {
            this.itemName = itemName;
            this.minCount = minCount;
            this.maxCount = maxCount;
            this.chance = chance;
        }

        public String getItemName() {
            return itemName;
        }

        public int getMinCount() {
            return minCount;
        }

        public int getMaxCount() {
            return maxCount;
        }

        public double getChance() {
            return chance;
        }
    }

    public static final class AttributeAttack {
        private final AttributeType type;
        private final int value;

        public AttributeAttack(final AttributeType type, final int value) {
            this.type = type;
            this.value = value;
        }

        public AttributeType getType() {
            return type;
        }

        public int getValue() {
            return value;
        }
    }
}
