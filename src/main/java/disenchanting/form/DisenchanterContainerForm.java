package disenchanting.form;

import disenchanting.disenchanter.DisenchanterContainer;
import disenchanting.disenchanter.DisenchanterObjectEntity;
import necesse.engine.network.client.Client;
import necesse.gfx.forms.components.containerSlot.FormContainerProcessingRecipeSlot;
import necesse.gfx.forms.components.containerSlot.FormContainerSlot;
import necesse.gfx.forms.presets.containerComponent.object.FormProcessingProgressArrow;
import necesse.gfx.forms.presets.containerComponent.object.OEInventoryContainerForm;
import necesse.gfx.forms.presets.containerComponent.object.ProcessingHelp;

public class DisenchanterContainerForm extends OEInventoryContainerForm<DisenchanterContainer> {

    public DisenchanterContainerForm(Client client, DisenchanterContainer container) {
        super(client, container);
        int inputSlots = container.objectEntity.inputSlots;
        int outputSlots = slots.length - inputSlots;
        this.inventoryForm.setHeight(
                Math.max(getContainerHeight(inputSlots, 4), getContainerHeight(outputSlots, 4)));
        this.inventoryForm.addComponent(
                new FormProcessingProgressArrow(this.inventoryForm.getWidth() / 2 - 16,
                        30 + (this.inventoryForm.getHeight() - 30) / 2 - 16,
                        container.objectEntity.getProcessingHelp()));

    }

    @Override
    protected void addSlots() {
        slots = new FormContainerSlot[container.INVENTORY_END - container.INVENTORY_START + 1];
        DisenchanterObjectEntity processingOE = container.objectEntity;
        int inputSlots = processingOE.inputSlots;
        ProcessingHelp processingHelp = processingOE.getProcessingHelp();
        int centerWidth = 40;
        for (int i = 0; i < this.slots.length; i++) {
            int x, y, slotIndex = i + container.INVENTORY_START;
            if (i < inputSlots) {
                int sideWidth = inputSlots * 40;
                x = this.inventoryForm.getWidth() / 2 - sideWidth - centerWidth / 2 + i % 4 * 40;
                y = i / 4 * 40;
            } else {
                int indexOffset = i - inputSlots;
                x = this.inventoryForm.getWidth() / 2 + centerWidth / 2 + indexOffset % 4 * 40;
                y = indexOffset / 4 * 40;
            }
            this.slots[i] = this.inventoryForm.addComponent(new FormContainerProcessingRecipeSlot(
                    this.client, slotIndex, x, y + 34, processingHelp));
        }
    }
}
