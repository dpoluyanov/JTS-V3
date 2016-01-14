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

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setCrystalType(CrystalType crystalType) {
        this.crystalType = crystalType;
    }

    public CrystalType getCrystalType() {
        return crystalType;
    }

    public void setCrystalCount(int crystalCount) {
        this.crystalCount = crystalCount;
    }

    public int getCrystalCount() {
        return crystalCount;
    }

    public void setIsTrade(boolean isTrade) {
        this.isTrade = isTrade;
    }

    public boolean isTrade() {
        return isTrade;
    }

    public boolean isDrop() {
        return isDrop;
    }

    public void setIsDrop(boolean isDrop) {
        this.isDrop = isDrop;
    }

    public boolean isDestruct() {
        return isDestruct;
    }

    public void setIsDestruct(boolean isDestruct) {
        this.isDestruct = isDestruct;
    }

    public boolean isPrivateStore() {
        return isPrivateStore;
    }

    public void setIsPrivateStore(boolean isPrivateStore) {
        this.isPrivateStore = isPrivateStore;
    }

    public void setKeepType(byte keepType) {
        this.keepType = keepType;
    }

    public byte getKeepType() {
        return keepType;
    }

    public void setPhysicalDamage(int physicalDamage) {
        this.physicalDamage = physicalDamage;
    }

    public void setRandomDamage(int randomDamage) {
        this.randomDamage = randomDamage;
    }

    public int getPhysicalDamage() {
        return physicalDamage;
    }

    public int getRandomDamage() {
        return randomDamage;
    }

    public void setCritical(int critical) {
        this.critical = critical;
    }

    public int getCritical() {
        return critical;
    }

    public void setHitModify(double hitModify) {
        this.hitModify = hitModify;
    }

    public double getHitModify() {
        return hitModify;
    }

    public int getAvoidModify() {
        return avoidModify;
    }

    public void setAvoidModify(int avoidModify) {
        this.avoidModify = avoidModify;
    }

    public int getDualFhitRate() {
        return dualFhitRate;
    }

    public void setDualFhitRate(int dualFhitRate) {
        this.dualFhitRate = dualFhitRate;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public List<Integer> getDamageRange() {
        return damageRange;
    }

    public void setDamageRange(List<Integer> damageRange) {
        this.damageRange = damageRange;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public void setReuseDelay(int reuseDelay) {
        this.reuseDelay = reuseDelay;
    }

    public int getReuseDelay() {
        return reuseDelay;
    }

    public int getMpConsume() {
        return mpConsume;
    }

    public void setMpConsume(int mpConsume) {
        this.mpConsume = mpConsume;
    }

    public int getMagicalDamage() {
        return magicalDamage;
    }

    public void setMagicalDamage(int magicalDamage) {
        this.magicalDamage = magicalDamage;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public int getPhysicalDefense() {
        return physicalDefense;
    }

    public void setPhysicalDefense(int physicalDefense) {
        this.physicalDefense = physicalDefense;
    }

    public int getMagicalDefense() {
        return magicalDefense;
    }

    public void setMagicalDefense(int magicalDefense) {
        this.magicalDefense = magicalDefense;
    }

    public int getMpBonus() {
        return mpBonus;
    }

    public void setMpBonus(int mpBonus) {
        this.mpBonus = mpBonus;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public int getEnchanted() {
        return enchanted;
    }

    public void setEnchanted(int enchanted) {
        this.enchanted = enchanted;
    }

    public List<SlotBitType> getSlotBitTypes() {
        return slotBitTypes;
    }

    public AttributeAttack getBaseAttributeAttack() {
        return baseAttributeAttack;
    }

    public void setBaseAttributeAttack(AttributeAttack baseAttributeAttack) {
        this.baseAttributeAttack = baseAttributeAttack;
    }

    public List<Integer> getBaseAttributeDefend() {
        return baseAttributeDefend;
    }

    public void setBaseAttributeDefend(List<Integer> baseAttributeDefend) {
        this.baseAttributeDefend = baseAttributeDefend;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public void setMagicWeapon(boolean magicWeapon) {
        this.magicWeapon = magicWeapon;
    }

    public boolean isMagicWeapon() {
        return magicWeapon;
    }

    public int getEnchantEnable() {
        return enchantEnable;
    }

    public void setEnchantEnable(int enchantEnable) {
        this.enchantEnable = enchantEnable;
    }

    public boolean isElementalEnable() {
        return elementalEnable;
    }

    public void setElementalEnable(boolean elementalEnable) {
        this.elementalEnable = elementalEnable;
    }

    public List<String> getUnequipSkill() {
        return unequipSkill;
    }

    public void setUnequipSkill(List<String> unequipSkill) {
        this.unequipSkill = unequipSkill;
    }

    public boolean isForNpc() {
        return forNpc;
    }

    public void setForNpc(boolean forNpc) {
        this.forNpc = forNpc;
    }

    public List<String> getItemEquipOption() {
        return itemEquipOption;
    }

    public void setItemEquipOption(List<String> itemEquipOption) {
        this.itemEquipOption = itemEquipOption;
    }

    public List<Condition> getUseCondition() {
        return useCondition;
    }

    public void setUseCondition(List<Condition> useCondition) {
        this.useCondition = useCondition;
    }

    public List<Condition> getEquipCondition() {
        return equipCondition;
    }

    public void setEquipCondition(List<Condition> equipCondition) {
        this.equipCondition = equipCondition;
    }

    public boolean isOlympiadCanUse() {
        return isOlympiadCanUse;
    }

    public void setOlympiadCanUse(boolean olympiadCanUse) {
        isOlympiadCanUse = olympiadCanUse;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
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

    public static final class AttributeAttack {
        private AttributeType type;
        private int value;

        public AttributeAttack(AttributeType type, int value) {
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
