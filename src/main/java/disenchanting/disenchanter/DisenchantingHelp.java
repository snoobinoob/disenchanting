package disenchanting.disenchanter;

import necesse.engine.util.GameMath;
import necesse.engine.util.GameBlackboard;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.forms.presets.containerComponent.object.ProcessingHelp;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
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
    public GameTooltips getCurrentRecipeTooltip(PlayerMob paramPlayerMob) {
        if (!objectEntity.isProcessing())
            return null;
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(objectEntity.getOutputItem().getTooltip(paramPlayerMob, new GameBlackboard()));

        return tooltips;
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
        if (slot == 2 && isProcessing())
            return new InventoryItem("enchantingscroll");

        return null;
    }

    @Override
    public float getProcessingProgress() {
        return objectEntity.getProcessingProgress();
    }

    @Override
    public GameTooltips getTooltip(int slot, PlayerMob paramPlayerMob) {
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
