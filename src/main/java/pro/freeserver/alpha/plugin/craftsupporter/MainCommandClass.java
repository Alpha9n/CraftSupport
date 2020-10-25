package pro.freeserver.alpha.plugin.craftsupporter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommandClass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("craftsupporter") || label.equalsIgnoreCase("cs")){
            if (sender instanceof Player){
                sender.sendMessage("§a======§l§bCraftSuppoter§r§a======" + "\n生地\nピザ");
            }
        }
        return false;
    }
}
