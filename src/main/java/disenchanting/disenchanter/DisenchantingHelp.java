package disenchanting.disenchanter;

import necesse.engine.localization.Localization;
import necesse.engine.util.GameMath;
import necesse.entity.mobs.PlayerMob;
import necesse.gfx.forms.presets.containerComponent.object.ProcessingHelp;
import necesse.gfx.gameTooltips.GameTooltips;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.gfx.gameTooltips.StringTooltips;
import necesse.inventory.InventoryItem;

public class DisenchantingHelp extends ProcessingHelp {
    private final DisenchanterObjectEntity objectEntity;

    private final InventoryItem[] ghostGearItems = new InventoryItem[]{
        new InventoryItem("ironchestplate"), new InventoryItem("ironpickaxe"),
        new InventoryItem("ironsword"), new InventoryItem("ironbow"),
        new InventoryItem("woodstaff"), new InventoryItem("spiderstaff")};

    public DisenchantingHelp(DisenchanterObjectEntity objectEntity) {
        this.objectEntity = objectEntity;
    }

    @Override
    public GameTooltips getCurrentRecipeTooltip(PlayerMob player) {
        if (!objectEntity.isProcessing())
            return null;
        ListGameTooltips tooltips = new ListGameTooltips();
        tooltips.add(objectEntity.getOutputItem().getTooltip(player, null));

        return tooltips;
    }

    @Override
    public InventoryItem getGhostItem(int slot) {
        switch (slot) {
            case 0:
                long currTime = objectEntity.getLevel().getWorldEntity().getTime();
                int index = GameMath.floor(currTime / 2000f) % 6;
                return ghostGearItems[index];
            case 1:
                return new InventoryItem("enchantingscroll");
            case 2:
                return isProcessing() ? new InventoryItem("enchantingscroll") : null;
        }

        return null;
    }

    @Override
    public float getProcessingProgress() {
        return objectEntity.getProcessingProgress();
    }

    @Override
    public GameTooltips getTooltip(int slot, PlayerMob player) {
        String errorCode = objectEntity.validateSlotForItem(slot, player.getDraggingItem());
        return errorCode == null ? null : new StringTooltips(Localization.translate("tooltip", errorCode));
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
