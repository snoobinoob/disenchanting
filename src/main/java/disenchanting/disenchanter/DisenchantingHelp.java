package disenchanting.disenchanter;

import necesse.engine.util.GameMath;
import necesse.gfx.forms.presets.containerComponent.object.ProcessingHelp;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.inventory.InventoryItem;

public class DisenchantingHelp extends ProcessingHelp {
    private DisenchanterObjectEntity objectEntity;
    private InventoryItem ghostGearItems[] = new InventoryItem[] {
            new InventoryItem("ironchestplate"), new InventoryItem("ironpickaxe"),
            new InventoryItem("ironsword"), new InventoryItem("ironbow"),
            new InventoryItem("woodstaff"), new InventoryItem("spiderstaff")};

    public DisenchantingHelp(DisenchanterObjectEntity objectEntity) {
        this.objectEntity = objectEntity;
    }

    @Override
    public GameTooltips getCurrentRecipeTooltip() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InventoryItem getGhostItem(int slot) {
        if (slot == 0) {
            long currTime = objectEntity.getLevel().getWorldEntity().getTime();
            int index = GameMath.floor(currTime / 2000) % 6;
            return ghostGearItems[index];
        }
        if (slot == 1)
            return new InventoryItem("enchantingscroll");

        return null;
    }

    @Override
    public float getProcessingProgress() {
        return objectEntity.getProcessingProgress();
    }

    @Override
    public GameTooltips getTooltip(int slot) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isProcessing() {
        return objectEntity.isProcessing();
    }

    @Override
    public boolean needsFuel() {
        return false;
    }

}
