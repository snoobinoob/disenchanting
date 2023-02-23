package disenchanting;

import necesse.engine.modLoader.ModSettings;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;

public class Settings extends ModSettings {
    public int processTime = 5000;

    @Override
    public void addSaveData(SaveData save) {
        save.addInt("processtime", processTime);
    }

    @Override
    public void applyLoadData(LoadData save) {
        if (save == null)
            return;
        processTime = save.getInt("processtime");
    }


}
