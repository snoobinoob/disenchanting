package disenchanting;

import disenchanting.disenchanter.DisenchanterContainer;
import disenchanting.disenchanter.DisenchanterObject;
import disenchanting.form.DisenchanterContainerForm;
import necesse.engine.modLoader.ModSettings;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.ContainerRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

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

    public void postInit() {
        Recipes.registerModRecipe(
            new Recipe(
                "disenchanter",
                1,
                RecipeTechRegistry.WORKSTATION,
                new Ingredient[]{
                    new Ingredient("anylog", 10),
                    new Ingredient("anystone", 10),
                    new Ingredient("voidshard", 2),
                }
            ).showAfter("alchemytable")
        );
    }
}
