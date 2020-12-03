package pro.freeserver.alpha.plugin.craftsupporter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pro.freeserver.alpha.plugin.craftsupporter.Event.CraftEvent;
import pro.freeserver.alpha.plugin.craftsupporter.Event.CraftRecipe;

public final class Craftsupporter extends JavaPlugin {

    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        checkAlreadyRecipe();
        getServer().getPluginManager().registerEvents(new CraftEvent(),this);
        getCommand("CraftSupporter").setExecutor(new MainCommandClass());
        getLogger().info("§aCraftSupporterが起動しました");
    }

    @Override
    public void onDisable() {
        getLogger().info("§cCraftSupporterが終了しました");
    }

    public void checkAlreadyRecipe(){
        CraftRecipe.createRecipe();
        CraftRecipe.newSlotFixRecipe();
    }
}
