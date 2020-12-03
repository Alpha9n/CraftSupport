package pro.freeserver.alpha.plugin.craftsupporter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommandClass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("craftsupporter") || label.equalsIgnoreCase("cs")){
            if (sender instanceof Player){
                sender.sendMessage("§a======§r[§l§bCraftSuppoter§r/§b" + Bukkit.getPluginManager().getPlugin("Craftsupporter").getDescription().getVersion() + "§r]§a======" + "\n§b作成者§r:§bAlpha9n\n \n§a・生地\n・ピザ\n・シフォンケーキ\n・みつかぼケーキ\n・どんぶり\n・皿");
            }
        }
        return false;
    }
}
