package disenchanting.disenchanter;

import disenchanting.Disenchanting;
import necesse.entity.objectEntity.ProcessingInventoryObjectEntity;
import necesse.gfx.forms.presets.containerComponent.object.ProcessingHelp;
import necesse.inventory.InventoryItem;
import necesse.inventory.enchants.Enchantable;
import necesse.inventory.enchants.ItemEnchantment;
import necesse.inventory.item.miscItem.EnchantingScrollItem;
import necesse.level.maps.Level;

public class DisenchanterObjectEntity extends ProcessingInventoryObjectEntity {
    DisenchantingHelp help;
    Enchantable<ItemEnchantment> gear;
    EnchantingScrollItem scroll;

    public DisenchanterObjectEntity(Level level, int x, int y) {
        super(level, "disenchanter", x, y, 2, 1);
        help = new DisenchantingHelp(this);

        inventory.filter = ((slot, item) -> (item == null) ? true
                : (slot < inputSlots ? isValidInputItem(slot, item) : false));
    }

    @Override
    public int getProcessTime() {
        return Disenchanting.settings.processTime;
    }

    @Override
    public ProcessingHelp getProcessingHelp() {
        return help;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onSlotUpdate(int slot) {
        super.onSlotUpdate(slot);
        if (slot == 0) {
            InventoryItem item = inventory.getItem(slot);
            gear = item == null ? null : (Enchantable<ItemEnchantment>) item.item;
        } else if (slot == 1) {
            InventoryItem item = inventory.getItem(slot);
            scroll = item == null ? null : (EnchantingScrollItem) item.item;
        }
        // updateProcessing = true;
    }

    @Override
    public boolean isValidInputItem(InventoryItem item) {
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean isValidInputItem(int slot, InventoryItem item) {
        if (item.item.isEnchantable(item)) {
            if (slot != 0 || gear != null || item.getGndData().getItem("enchantment") == null)
                return false;
            if (scroll != null) {
                Enchantable<ItemEnchantment> enchantable = (Enchantable<ItemEnchantment>) item.item;
                ItemEnchantment enchantment = scroll.getEnchantment(inventory.getItem(1));
                return enchantable.isValidEnchantment(item, enchantment);
            }
            return true;
        }
        if (item.item instanceof EnchantingScrollItem) {
            if (slot != 1 || scroll != null)
                return false;
            if (gear != null) {
                EnchantingScrollItem scrollItem = (EnchantingScrollItem) item.item;
                return gear.isValidEnchantment(inventory.getItem(0),
                        scrollItem.getEnchantment(item));
            }
            return true;
        }
        return false;
    }

    private InventoryItem getOutputItem() {
        InventoryItem out = inventory.getItem(1).copy();
        int enchantId = scroll.getEnchantmentID(inventory.getItem(0));
        scroll.setEnchantment(out, enchantId);
        return out;
    }

    @Override
    public boolean canProcessInput() {
        return gear != null && scroll != null && inventory.getAmount(2) == 0;
    }

    @Override
    public boolean processInput() {
        if (canProcessInput()) {
            addOutput(getOutputItem());
            inventory.removeItems(null, null, inventory.getItemSlot(0), 1, "disenchant");
            inventory.removeItems(null, null, inventory.getItemSlot(1), 1, "disenchant");
            return true;
        }
        return false;
    }
}
