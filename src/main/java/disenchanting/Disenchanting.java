package disenchanting;

import disenchanting.disenchanter.DisenchanterContainer;
import disenchanting.disenchanter.DisenchanterObject;
import disenchanting.form.DisenchanterContainerForm;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.ContainerRegistry;
import necesse.engine.registries.ObjectRegistry;

@ModEntry
public class Disenchanting {
    public static int DISENCHANTER_CONTAINER;
    public static Settings settings;

    public void init() {
        ObjectRegistry.registerObject("disenchanter", new DisenchanterObject(), 120f, true);


        DISENCHANTER_CONTAINER = ContainerRegistry.registerOEContainer(
                (client, uniqueSeed, oe, content) -> new DisenchanterContainerForm(client,
                        new DisenchanterContainer(client.getClient(), uniqueSeed, oe,
                                new PacketReader(content))),
                (client, uniqueSeed, oe, content, serverObj) -> new DisenchanterContainer(client,
                        uniqueSeed, oe, new PacketReader(content)));
    }

    public ModSettings initSettings() {
        settings = new Settings();
        return settings;
    }
}
