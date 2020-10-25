package pro.freeserver.alpha.plugin.craftsupporter;

import org.bukkit.plugin.java.JavaPlugin;
import pro.freeserver.alpha.plugin.craftsupporter.Event.CraftRecipe;

public final class Craftsupporter extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CraftRecipe(),this);
        getCommand("CraftSupporter").setExecutor(new MainCommandClass());
        getLogger().info("§aCraftSupporterが起動しました");
    }

    @Override
    public void onDisable() {
        getLogger().info("§cCraftSupporterが終了しました");
    }
}
