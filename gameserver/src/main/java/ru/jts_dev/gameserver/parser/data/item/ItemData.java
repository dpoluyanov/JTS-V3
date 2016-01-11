package ru.jts_dev.gameserver.parser.data.item;

import ru.jts_dev.gameserver.constants.*;
import ru.jts_dev.gameserver.constants.ItemTypes.ArmorType;
import ru.jts_dev.gameserver.constants.ItemTypes.EtcItemType;
import ru.jts_dev.gameserver.parser.data.item.condition.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Camelion
 * @since 07.01.16
 */
public class ItemData {
    private final int itemId;
    private final ItemClass itemClass;
    private final String name;
    private final ItemClass itemType;
    private final List<SlotBitType> slotBitTypes;

    private ArmorType armorType;
    private EtcItemType etcItemType;
    private ItemTypes.WeaponType weaponType;
    private int delayShareGroup;
    private List<String> itemMultiSkillList = new ArrayList<>();
    private int recipeId;
    private boolean blessed;
    private int weight;
    private DefaultAction defaultAction;
    private ConsumeType consumeType;
    private int initialCount;
    private int soulshotCount;
    private int spiritshotCount;
    private int[] reducedSoulshot;
    // not used in datas, always EMPTY ARRAY
    private int[] reducedSpiritshot;
    private int[] reducedMpConsume;
    private int immediateEffect;
    private int exImmediateEffect;
    private int dropPeriod;
    private int duration;
    private int useSkillDistTime;
    private int period;
    private int equipReuseDelay;
    private int price;
    private int defaultPrice;
    private String itemSkill;
    private String criticalAttackSkill;
    private String attackSkill;
    private String magicSkill;
    private String itemSkillEnchantedFour;
    private List<CapsuledItemData> capsuledItems = new ArrayList<>();
    private MaterialType materialType;
    private int crystalCount;
    private boolean isTrade;
    private boolean isDrop;
    private boolean isDestruct;
    private boolean isPrivateStore;
    private byte keep_type;

    private int physicalDamage;
    private int randomDamage;
    private int critical;
    private double hitModify;
    private int avoidModify;
    private int dualFhitRate;
    private int shieldDefense;
    private int shieldDefenseRate;
    private int attackRange;
    private int[] damageRange;
    private int attackSpeed;

    private int reuseDelay;
    private int mpConsume;
    private int magicalDamage;
    private int durability;
    private int damaged;
    private int physicalDefense;
    private int magicalDefense;
    private int mpBonus;
    // not used in datas, always EMPTY ARRAY
    private String[] category;
    private int enchanted;
    private AttributeAttack baseAttributeAttack;
    private int[] baseAttributeDefend;
    private String html;
    private int magicWeapon;
    private boolean enchantEnable;
    private boolean elementalEnable;
    private List<String> unequipSkill = new ArrayList<>();
    private boolean forNpc;
    private List<String> itemEquipOption = new ArrayList<>();
    private List<Condition> useCondition = new ArrayList<>();
    private List<Condition> equipCondition = new ArrayList<>();
    private boolean isOlympiadCanUse;
    private boolean canMove;
    private boolean isPremium;

    public ItemData(int itemId, ItemClass itemClass, String name, ItemClass itemType, List<SlotBitType> slotBitTypes) {
        this.itemId = itemId;
        this.itemClass = itemClass;
        this.name = name;
        this.itemType = itemType;
        this.slotBitTypes = slotBitTypes;
    }

    public ArmorType getArmorType() {
        return armorType;
    }

    public void setArmorType(ArmorType armorType) {
        this.armorType = armorType;
    }

    public EtcItemType getEtcItemType() {
        return etcItemType;
    }

    public void setEtcItemType(EtcItemType etcItemType) {
        this.etcItemType = etcItemType;
    }

    public ItemTypes.WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(ItemTypes.WeaponType weaponType) {
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

    public void setDelayShareGroup(int delayShareGroup) {
        this.delayShareGroup = delayShareGroup;
    }

    public int getShieldDefenseRate() {
        return shieldDefenseRate;
    }

    public void setShieldDefenseRate(int shieldDefenseRate) {
        this.shieldDefenseRate = shieldDefenseRate;
    }

    public int getShieldDefense() {
        return shieldDefense;
    }

    public void setShieldDefense(int shieldDefense) {
        this.shieldDefense = shieldDefense;
    }

    public List<String> getItemMultiSkillList() {
        return itemMultiSkillList;
    }

    public void setItemMultiSkillList(List<String> itemMultiSkillList) {
        this.itemMultiSkillList = itemMultiSkillList;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setBlessed(boolean blessed) {
        this.blessed = blessed;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    private static final class CapsuledItemData {
        private final String itemName;
        private final int minCount;
        private final int maxCount;
        private final double chance;

        private CapsuledItemData(String itemName, int minCount, int maxCount, double chance) {
            this.itemName = itemName;
            this.minCount = minCount;
            this.maxCount = maxCount;
            this.chance = chance;
        }
    }

    private static final class AttributeAttack {
        private final AttributeType type;
        private final int value;

        private AttributeAttack(AttributeType type, int value) {
            this.type = type;
            this.value = value;
        }
    }
}
