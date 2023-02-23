package disenchanting.disenchanter;

import necesse.engine.network.NetworkClient;
import necesse.engine.network.PacketReader;
import necesse.entity.objectEntity.ObjectEntity;
import necesse.inventory.container.object.OEInventoryContainer;

public class DisenchanterContainer extends OEInventoryContainer {
    public DisenchanterObjectEntity objectEntity;

    public DisenchanterContainer(NetworkClient client, int uniqueSeed, ObjectEntity oe,
            PacketReader reader) {
        super(client, uniqueSeed, (DisenchanterObjectEntity) oe, reader);

        objectEntity = (DisenchanterObjectEntity) oe;
    }
}
