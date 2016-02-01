package ru.jts_dev.gameserver.packets.out;

import ru.jts_dev.common.packets.OutgoingMessageWrapper;
import ru.jts_dev.gameserver.constants.ItemClass;
import ru.jts_dev.gameserver.model.GameItem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Camelion
 * @since 29.01.16
 */
public final class ItemList extends OutgoingMessageWrapper {

    private final List<GameItem> items;
    private final boolean silent;

    /**
     * Creates new message instance with 'common' character items,
     * without quest items. Quest items should be placed in {@link ExQuestItemList}
     *
     * @param items  - all characters items, except quest items
     * @param silent - if {@code true}, inventory not will be showed
     */
    public ItemList(final List<GameItem> items, final boolean silent) {
        assert items.stream()
                .filter(item -> item.getItemData().getItemClass() == ItemClass.QUESTITEM)
                .collect(Collectors.toList()).isEmpty();

        this.items = items;
        this.silent = silent;
    }

    @Override
    public void write() {
        writeByte(0x11);
        writeShort(silent ? 0x00 : 0x01);
        writeShort(items.size());
        for (final GameItem item : items) {
            writeInt(item.getObjectId()); // ObjectId
            writeInt(item.getItemData().getItemId()); // ItemId
            writeInt(0x00); // todo equip slot
            writeLong(item.getCount()); // Quantity
            writeShort(item.getItemData().getItemType().getValue()); // Item Type 2 : 00-weapon, 01-shield/armor, 02-ring/earring/necklace, 03-questitem, 04-adena, 05-item
            writeShort(0x00); // todo unknown: etc_type?
            writeShort(item.isEquipped() ? 0x01 : 0x00); // Equipped : 00-No, 01-yes
            writeInt(item.getItemData().getSlotBitTypeMask());
            writeShort(item.getEnchant()); // Enchant level (pet level shown in control item)
            writeShort(0x00); // todo unknown
            writeInt(0x00); // augmentation?
            writeInt(0x00); // todo item period?
            writeInt(0x00); // TODO: 30.01.16 item duration?  

            // atribute enchant
            writeShort(-2);
            writeShort(0x00);

            writeShort(0x00);
            writeShort(0x00);
            writeShort(0x00);
            writeShort(0x00);
            writeShort(0x00);
            writeShort(0x00);

            // Enchant Effects
            writeShort(0x00);
            writeShort(0x00);
            writeShort(0x00);
        }

        writeShort(0x00); // todo (locked items length)
        writeByte(0x00); // block Mode
        // TODO: 30.01.16
        // iter(blockedItems)
                //writeInt(item)
    }
}
