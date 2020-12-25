package pro.freeserver.alpha.plugin.craftsupporter.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class EatEvent implements Listener {

    @EventHandler
    public void onEatEvent(PlayerItemConsumeEvent e){

        ItemStack eatitem = e.getItem();
        Player player = e.getPlayer();


    }
}
