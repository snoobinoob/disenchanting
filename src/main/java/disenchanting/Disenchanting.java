package disenchanting;

import disenchanting.disenchanter.DisenchanterContainer;
import disenchanting.disenchanter.DisenchanterObject;
import disenchanting.form.DisenchanterContainerForm;
import necesse.engine.commands.CommandsManager;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.clientCommands.BoolClientCommand;
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

    public void init() throws NoSuchFieldException {
        ObjectRegistry.registerObject("disenchanter", new DisenchanterObject(), 120f, true);


        DISENCHANTER_CONTAINER = ContainerRegistry.registerSettlementDependantOEContainer(
            (client, uniqueSeed, settlement, oe, content) -> new DisenchanterContainerForm(client,
                new DisenchanterContainer(client.getClient(), uniqueSeed, oe,
                    new PacketReader(content))),
            (client, uniqueSeed, settlement, oe, content, serverObj) -> new DisenchanterContainer(client,
                uniqueSeed, oe, new PacketReader(content)));

        CommandsManager.registerServerCommand(new BoolClientCommand(
            "disenchanting:requirematchingenchant",
            "Control whether enchant types must match",
            PermissionLevel.ADMIN,
            Settings.class.getField("requireMatchingEnchant"),
            settings
        ));
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
            ).showAfter("workstation")
        );
    }
}
