package disenchanting.form;

import disenchanting.disenchanter.DisenchanterContainer;
import disenchanting.disenchanter.DisenchanterObjectEntity;
import necesse.engine.network.client.Client;
import necesse.gfx.forms.components.FormFlow;
import necesse.gfx.forms.components.containerSlot.FormContainerProcessingRecipeSlot;
import necesse.gfx.forms.components.containerSlot.FormContainerSlot;
import necesse.gfx.forms.presets.containerComponent.object.FormProcessingProgressArrow;
import necesse.gfx.forms.presets.containerComponent.object.OEInventoryContainerForm;
import necesse.gfx.forms.presets.containerComponent.object.ProcessingHelp;

public class DisenchanterContainerForm extends OEInventoryContainerForm<DisenchanterContainer> {

    public DisenchanterContainerForm(Client client, DisenchanterContainer container) {
        super(client, container);
    }

    @Override
    protected void addSlots(FormFlow flow) {
        slots = new FormContainerSlot[container.INVENTORY_END - container.INVENTORY_START + 1];
        DisenchanterObjectEntity processingOE = container.objectEntity;
        int inputSlots = processingOE.inputSlots;
        ProcessingHelp processingHelp = processingOE.getProcessingHelp();
        int centerWidth = 40;
        for (int i = 0; i < slots.length; i++) {
            int x, y, slotIndex = i + container.INVENTORY_START;
            if (i < inputSlots) {
                int sideWidth = inputSlots * 40;
                x = inventoryForm.getWidth() / 2 - sideWidth - centerWidth / 2 + i % 4 * 40;
                y = i / 4 * 40;
            } else {
                int indexOffset = i - inputSlots;
                x = inventoryForm.getWidth() / 2 + centerWidth / 2 + indexOffset % 4 * 40;
                y = indexOffset / 4 * 40;
            }
            slots[i] = inventoryForm.addComponent(new FormContainerProcessingRecipeSlot(
                    client, container, slotIndex, x, y + 34, processingHelp));
        }
        inventoryForm.addComponent(
                new FormProcessingProgressArrow(inventoryForm.getWidth() / 2 - 16, 38,
                        container.objectEntity.getProcessingHelp()));
        flow.next(40);
    }
}
