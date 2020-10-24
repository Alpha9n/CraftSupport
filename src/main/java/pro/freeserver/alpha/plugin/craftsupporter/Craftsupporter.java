package pro.freeserver.alpha.plugin.craftsupporter;

import org.bukkit.plugin.java.JavaPlugin;

public final class Craftsupporter extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("CraftSupporter").setExecutor(new MainCommandClass());
        getLogger().info("§aCraftSupporterが起動しました");
    }

    @Override
    public void onDisable() {
        getLogger().info("§cCraftSupporterが終了しました");
    }
}
