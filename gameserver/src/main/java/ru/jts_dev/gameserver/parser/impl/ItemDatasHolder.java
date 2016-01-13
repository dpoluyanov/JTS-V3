package ru.jts_dev.gameserver.parser.impl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.constants.ItemClass;
import ru.jts_dev.gameserver.constants.SlotBitType;
import ru.jts_dev.gameserver.parser.ItemDatasBaseListener;
import ru.jts_dev.gameserver.parser.ItemDatasLexer;
import ru.jts_dev.gameserver.parser.ItemDatasParser;
import ru.jts_dev.gameserver.parser.ItemDatasParser.SetContext;
import ru.jts_dev.gameserver.parser.data.item.ItemData;
import ru.jts_dev.gameserver.parser.data.item.SetData;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Camelion
 * @since 07.01.16
 */
@Component
public class ItemDatasHolder extends ItemDatasBaseListener {
    private final Map<Integer, SetData> setsData = new HashMap<>();
    private final Map<Integer, ItemData> itemData = new HashMap<>();

    @Autowired
    private ApplicationContext context;

    public Map<Integer, SetData> getSetsData() {
        return Collections.unmodifiableMap(setsData);
    }

    public Map<Integer, ItemData> getItemData() {
        return Collections.unmodifiableMap(itemData);
    }

    /**
     * parse itemdata set section
     * {@see ItemDatas.g4} `set` rule
     *
     * @param ctx - parsed {@link SetContext}
     */
    @Override
    public void exitSet(SetContext ctx) {
        int setId = Integer.valueOf(ctx.int_object().getText());
        int slotChest = Integer.valueOf(ctx.slot_chest().int_object().getText());
        SetData data = new SetData(setId, slotChest);

        if (ctx.slot_legs() != null)
            data.setSlotLegs(ctx.slot_legs().int_list().value);

        if (ctx.slot_head() != null)
            data.setSlotHead(ctx.slot_head().int_list().value);

        if (ctx.slot_gloves() != null)
            data.setSlotGloves(ctx.slot_gloves().int_list().value);

        if (ctx.slot_feet() != null)
            data.setSlotFeet(ctx.slot_feet().int_list().value);

        if (ctx.slot_lhand() != null)
            data.setSlotLhand(ctx.slot_lhand().int_list().value);

        data.setSlotAdditional(ctx.slot_additional().name_object().identifier_object().getText());
        data.setSetSkill(ctx.set_skill().name_object().identifier_object().getText());
        data.setSetEffectSkill(ctx.set_effect_skill().name_object().identifier_object().getText());
        data.setSetAdditionalEffectSkill(ctx.set_additional_effect_skill().name_object().identifier_object().getText());

        if (ctx.set_additional2_condition() != null)
            data.setSetAdditional2Condition(Integer.valueOf(ctx.set_additional2_condition().int_object().getText()));

        if (ctx.set_additional2_effect_skill() != null)
            data.setSetAdditional2EffectSkill(ctx.set_additional2_effect_skill().name_object().identifier_object().getText());

        data.setStrInc(ctx.str_inc().int_list().value);
        data.setConInc(ctx.con_inc().int_list().value);
        data.setDexInc(ctx.dex_inc().int_list().value);
        data.setIntInc(ctx.int_inc().int_list().value);
        data.setMenInc(ctx.men_inc().int_list().value);
        data.setWitInc(ctx.wit_inc().int_list().value);
        setsData.put(setId, data);
    }

    @Override
    public void exitItem(ItemDatasParser.ItemContext ctx) {
        int itemId = ctx.item_id().value;
        ItemClass itemClass = ctx.item_class().value;
        String name = ctx.name_object().value;
        ItemClass itemType = ctx.item_type().value;
        List<SlotBitType> slotBitTypes = ctx.slot_bit_type_list().value;

        ItemData data = new ItemData(itemId, itemClass, name, itemType, slotBitTypes);
        data.setArmorType(ctx.armor_type_wrapper().value);
        data.setEtcItemType(ctx.etcitem_type_wrapper().value);
        data.setWeaponType(ctx.weapon_type_wrapper().value);
        data.setDelayShareGroup(ctx.delay_share_group().value);
        data.setItemMultiSkillList(ctx.item_multi_skill_list().value);
        data.setRecipeId(ctx.recipe_id().value);
        data.setBlessed(ctx.blessed().value);
        data.setWeight(ctx.weight().value);
        data.setDefaultAction(ctx.default_action_wrapper().value);
        data.setConsumeType(ctx.consume_type_wrapper().value);
        data.setInitialCount(ctx.initial_count().value);
        data.setSoulshotCount(ctx.soulshot_count().value);
        data.setSpiritshotCount(ctx.spiritshot_count().value);
        data.setReducedSoulshot(ctx.reduced_soulshot().value);
        data.setReducedSpiritshot(ctx.reduced_spiritshot().value);
        data.setReducedMpConsume(ctx.reduced_mp_consume().value);
        data.setImmediateEffect(ctx.immediate_effect().value);
        data.setExImmediateEffect(ctx.ex_immediate_effect().value);
        data.setDropPeriod(ctx.drop_period().value);
        data.setDuration(ctx.duration().value);
        data.setUseSkillDistime(ctx.use_skill_distime().value);
        data.setPeriod(ctx.period().value);
        data.setEquipReuseDelay(ctx.equip_reuse_delay().value);
        data.setPrice(ctx.price().value);
        data.setDefaultPrice(ctx.default_price().value);
        data.setItemSkill(ctx.item_skill().value);
        data.setCriticalAttackSkill(ctx.critical_attack_skill().value);
        data.setAttackSkill(ctx.attack_skill().value);
        data.setMagicSkill(ctx.magic_skill().value);
        data.setMagicSkillUnknownValue(ctx.magic_skill().unk);
        data.setItemSkillEnchantedFour(ctx.item_skill_enchanted_four().value);
        data.setCapsuledItems(ctx.capsuled_items().value);
        data.setMaterialType(ctx.material_type_wrapper().value);
        data.setCrystalType(ctx.crystal_type_wrapper().value);
        data.setCrystalCount(ctx.crystal_count().value);

        // TODO: 09.01.16 fill item data from parsed values

        data.setShieldDefense(ctx.shield_defense().value);
        data.setShieldDefenseRate(ctx.shield_defense_rate().value);

        assert !itemData.containsKey(itemId) : "Duplicate ItemId " + itemId;

        itemData.put(itemId, data);
    }

    @PostConstruct
    private void parse() throws IOException {
        Resource file = context.getResource("scripts/itemdata.txt");
        try (InputStream is = file.getInputStream()) {
            ANTLRInputStream input = new ANTLRInputStream(is);
            ItemDatasLexer lexer = new ItemDatasLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ItemDatasParser parser = new ItemDatasParser(tokens);

            ParseTree tree = parser.file();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(this, tree);
        }
    }
}
