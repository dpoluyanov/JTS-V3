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

    public void setBlessed(int blessed) {
        this.blessed = blessed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public DefaultAction getDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(DefaultAction defaultAction) {
        this.defaultAction = defaultAction;
    }

    public ConsumeType getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(ConsumeType consumeType) {
        this.consumeType = consumeType;
    }

    public void setInitialCount(int initialCount) {
        this.initialCount = initialCount;
    }

    public int getInitialCount() {
        return initialCount;
    }

    public void setSoulshotCount(int soulshotCount) {
        this.soulshotCount = soulshotCount;
    }

    public void setSpiritshotCount(int spiritshotCount) {
        this.spiritshotCount = spiritshotCount;
    }

    public int getSoulshotCount() {
        return soulshotCount;
    }

    public int getSpiritshotCount() {
        return spiritshotCount;
    }

    public void setReducedSoulshot(List<Integer> reducedSoulshot) {
        this.reducedSoulshot = reducedSoulshot;
    }

    public void setReducedSpiritshot(List<Integer> reducedSpiritshot) {
        this.reducedSpiritshot = reducedSpiritshot;
    }

    public List<Integer> getReducedSoulshot() {
        return reducedSoulshot;
    }

    public List<Integer> getReducedSpiritshot() {
        return reducedSpiritshot;
    }

    public int getBlessed() {
        return blessed;
    }

    public void setReducedMpConsume(List<Integer> reducedMpConsume) {
        this.reducedMpConsume = reducedMpConsume;
    }

    public List<Integer> getReducedMpConsume() {
        return reducedMpConsume;
    }

    public void setImmediateEffect(int immediateEffect) {
        this.immediateEffect = immediateEffect;
    }

    public int getImmediateEffect() {
        return immediateEffect;
    }

    public void setExImmediateEffect(int exImmediateEffect) {
        this.exImmediateEffect = exImmediateEffect;
    }

    public int getExImmediateEffect() {
        return exImmediateEffect;
    }

    public void setDropPeriod(int dropPeriod) {
        this.dropPeriod = dropPeriod;
    }

    public int getDropPeriod() {
        return dropPeriod;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setUseSkillDistime(int useSkillDistime) {
        this.useSkillDistime = useSkillDistime;
    }

    public int getUseSkillDistime() {
        return useSkillDistime;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPeriod() {
        return period;
    }

    public void setEquipReuseDelay(int equipReuseDelay) {
        this.equipReuseDelay = equipReuseDelay;
    }

    public int getEquipReuseDelay() {
        return equipReuseDelay;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setDefaultPrice(long defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public long getPrice() {
        return price;
    }

    public long getDefaultPrice() {
        return defaultPrice;
    }

    public void setItemSkill(String itemSkill) {
        this.itemSkill = itemSkill;
    }

    public String getItemSkill() {
        return itemSkill;
    }

    public void setCriticalAttackSkill(String criticalAttackSkill) {
        this.criticalAttackSkill = criticalAttackSkill;
    }

    public String getCriticalAttackSkill() {
        return criticalAttackSkill;
    }

    public void setAttackSkill(String attackSkill) {
        this.attackSkill = attackSkill;
    }

    public String getAttackSkill() {
        return attackSkill;
    }

    public String getMagicSkill() {
        return magicSkill;
    }

    public void setMagicSkill(String magicSkill) {
        this.magicSkill = magicSkill;
    }

    public int getMagicSkillUnknownValue() {
        return magicSkillUnknownValue;
    }

    public void setMagicSkillUnknownValue(int magicSkillUnknownValue) {
        this.magicSkillUnknownValue = magicSkillUnknownValue;
    }

    public void setItemSkillEnchantedFour(String itemSkillEnchantedFour) {
        this.itemSkillEnchantedFour = itemSkillEnchantedFour;
    }

    public String getItemSkillEnchantedFour() {
        return itemSkillEnchantedFour;
    }

    public void setCapsuledItems(List<CapsuledItemData> capsuledItems) {
        this.capsuledItems = capsuledItems;
    }

    public List<CapsuledItemData> getCapsuledItems() {
        return capsuledItems;
    }

    public static final class CapsuledItemData {
        private final String itemName;
        private final int minCount;
        private final int maxCount;
        private final double chance;

        public CapsuledItemData(String itemName, int minCount, int maxCount, double chance) {
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

    private static final class AttributeAttack {
        private final AttributeType type;
        private final int value;

        private AttributeAttack(AttributeType type, int value) {
            this.type = type;
            this.value = value;
        }
    }
}
