package pro.freeserver.alpha.plugin.craftsupporter.Event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import static pro.freeserver.alpha.plugin.craftsupporter.Craftsupporter.plugin;
import static pro.freeserver.alpha.plugin.craftsupporter.Event.CraftRecipe.kizi;

public class CraftEvent implements Listener {
    @EventHandler
    public void onCraftEvent(CraftItemEvent e){

        Player player = (Player) e.getWhoClicked();
        Recipe recipe = e.getRecipe();

        System.out.println("今やよ～");

        if(e.getRecipe().getResult().getLore().get(0).equalsIgnoreCase("§cこのままでは食べられないようだ...")){
            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage("§f[§c重要§f]: §cインベントリに空きがないため地面に落とします。");
                player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.GLASS_BOTTLE));
            } else {
                player.getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE));
            }
        }
    }
}
