package pro.freeserver.alpha.plugin.craftsupporter;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.plugin.java.JavaPlugin;
import pro.freeserver.alpha.plugin.craftsupporter.Event.CraftRecipe;

public final class Craftsupporter extends JavaPlugin {

    @Override
    public void onEnable() {
        registerRecipe();
        getServer().getPluginManager().registerEvents(new CraftRecipe(),this);
        getCommand("CraftSupporter").setExecutor(new MainCommandClass());
        getLogger().info("§aCraftSupporterが起動しました");
    }

    @Override
    public void onDisable() {
        getLogger().info("§cCraftSupporterが終了しました");
    }

    public void registerRecipe(){
        FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(this,"chiffon_cake"),CraftRecipe.chiffonCake, new RecipeChoice.ExactChoice(CraftRecipe.kizi),0,5);
        Bukkit.addRecipe(recipe);
    }
}
