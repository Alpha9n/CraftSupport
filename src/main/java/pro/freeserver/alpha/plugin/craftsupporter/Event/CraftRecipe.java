package pro.freeserver.alpha.plugin.craftsupporter.Event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CraftRecipe implements Listener {
    public ItemStack kizi = getItemStack(Material.DEAD_BUSH,1,"生地",Arrays.asList("§cこのままでは食べられないようだ..."),1);

    @EventHandler
    public void onPlayerCraftItem(PrepareItemCraftEvent e){
        if(e.getInventory().getMatrix().length < 9){
            return;
        }
        //クラフトメソッド
        checkCraft(kizi,e.getInventory(), new HashMap<Integer, ItemStack>(){{
            put(1, new ItemStack(Material.WHEAT));
            put(2, new ItemStack(Material.WATER_BUCKET));
        }});
    }

    //クラフトが正しいかチェックするクラス
    public void checkCraft(ItemStack result, CraftingInventory inv, HashMap<Integer, ItemStack> ingredients){
        ItemStack[] matrix = inv.getMatrix();
        for(int i = 0 ; i < 9; i++){
            if(ingredients.containsKey(i)){
                if(matrix[i] == null || !matrix[i].equals(ingredients.get(i))){
                    return;
                }
            } else {
                if(matrix[i] != null){
                    return;
                }
            }
        }
        inv.setResult(result);
    }

    public static ItemStack getItemStack(Material material, int amount, String displayName, List<String> lore, int customModelData) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
