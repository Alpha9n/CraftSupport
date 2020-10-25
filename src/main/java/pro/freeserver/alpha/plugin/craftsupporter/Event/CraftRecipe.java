package pro.freeserver.alpha.plugin.craftsupporter.Event;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CraftRecipe implements Listener {
    //カスタムアイテム
    public ItemStack kizi = getItemStack(Material.DEAD_BUSH,1,"生地",Arrays.asList("§cこのままでは食べられないようだ..."),1);
    public ItemStack pizza = getItemStack(Material.PUMPKIN_PIE,1,"ピザ",Arrays.asList("§bたくさん作ってみんなでシェアしよう！！"),6);

    @EventHandler
    public void onPlayerCraftItem(PrepareItemCraftEvent e){
        if(e.getInventory().getMatrix().length < 9){
            return;
        }
        //生地作成
        checkCraft(kizi,e.getInventory(),
                Arrays.asList(
                        new ItemStack(Material.WHEAT,1),
                        waterBottle()
                ));
        //ピザ作成
        checkCraft(pizza,e.getInventory(),
                Arrays.asList(
                        new ItemStack(Material.BEETROOT,1),
                        new ItemStack(Material.POTATO,1),
                        new ItemStack(Material.RABBIT,1),
                        new ItemStack(Material.KELP,1),
                        kizi
                ));
    }

    //クラフトが正しいかチェックしたのちに結果を出力するクラス(固定スロット)
    public void slotFix(ItemStack result, CraftingInventory inv, HashMap<Integer, ItemStack> ingredients){
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

    //クラフトが正しいかチェックしたのちに結果を出力するクラス(変動スロット)
    public void checkCraft(ItemStack result, CraftingInventory inv, List<ItemStack> ingredients) {
        ArrayList<ItemStack> matrix = new ArrayList<>(Arrays.asList(inv.getMatrix()));

        for (ItemStack ingredient : ingredients) {
            boolean isPartial = false;
            for (ItemStack material : matrix) {
                if (ingredient.getItemMeta() instanceof PotionMeta){
                    if (isPotionPartialMatch(ingredient, material)) {
                        isPartial = true;
                        break;
                    }
                } else {
                    if (isPartialMatch(ingredient, material)) {
                        isPartial = true;
                        break;
                    }
                }
            }
            if (!isPartial) return;
        }
        inv.setResult(result);
    }
    //水入り瓶のアイテム作成
    public ItemStack waterBottle(){
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        ItemMeta meta = bottle.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        bottle.setItemMeta(meta);
        return bottle;
    }
    //ここから下、人外未知のコード(tererunの)
    public static ItemStack getItemStack(Material material, int amount, String displayName, List<String> lore, int customModelData) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    public static boolean isPartialMatch(ItemStack fromItem, ItemStack toItem) {
        if ((fromItem == null) || (toItem == null)) return false;
        if (!fromItem.getType().equals(toItem.getType())) return false;
        if (fromItem.isSimilar(toItem)) return true;
        ItemMeta fromMeta = fromItem.getItemMeta();
        ItemMeta toMeta = toItem.getItemMeta();
        List<Object> fromContentList = getContentList(fromMeta);
        List<Object> toContentList = getContentList(toMeta);
        for (int i=0; i<fromContentList.size(); i++) {
            Object content = fromContentList.get(i);
            if (content != null) {
                if (toContentList.get(i) == null) return false;
                if (!content.equals(toContentList.get(i))) return false;
            }
        }
        if ((fromMeta.isUnbreakable()) && (!toMeta.isUnbreakable())) return false;
        return true;
    }

    private static List<Object> getContentList(ItemMeta equalsMeta) {
        Object customModelData;
        Object displayName;
        Object localizedName;
        Object lore;

        if (equalsMeta.hasCustomModelData()) {
            customModelData = equalsMeta.getCustomModelData();
        } else {
            customModelData = null;
        }

        if (equalsMeta.hasDisplayName()) {
            displayName = equalsMeta.getDisplayName();
        } else {
            displayName = null;
        }

        if (equalsMeta.hasLocalizedName()) {
            localizedName = equalsMeta.getLocalizedName();
        } else {
            localizedName = null;
        }

        if (equalsMeta.hasLore()) {
            lore = equalsMeta.getLore();
        } else {
            lore = null;
        }

        List<Object> checkList = Arrays.asList(
                equalsMeta.getAttributeModifiers(),
                customModelData,
                equalsMeta.getDestroyableKeys(),
                displayName,
                equalsMeta.getEnchants(),
                localizedName,
                lore,
                equalsMeta.getPlaceableKeys()
        );
        return checkList;
    }

    public static boolean isPotionPartialMatch(ItemStack fromItem, ItemStack toItem) {
        if ((fromItem == null) || (toItem == null)) return false;
        if (!fromItem.getType().equals(toItem.getType())) return false;
        if (fromItem.isSimilar(toItem)) return true;
        PotionMeta fromMeta = (PotionMeta) fromItem.getItemMeta();
        PotionMeta toMeta = (PotionMeta) toItem.getItemMeta();
        List<Object> fromContentList = getPotionContentList(fromMeta);
        List<Object> toContentList = getPotionContentList(toMeta);
        for (int i=0; i<fromContentList.size(); i++) {
            Object content = fromContentList.get(i);
            if (content != null) {
                if (toContentList.get(i) == null) return false;
                if (!content.equals(toContentList.get(i))) return false;
            }
        }
        if ((fromMeta.isUnbreakable()) && (!toMeta.isUnbreakable())) return false;
        return true;
    }

    private static List<Object> getPotionContentList(PotionMeta equalsMeta) {
        List<Object> contentsList = getContentList(equalsMeta);
        Object customEffects;
        Object color;

        if (equalsMeta.hasCustomEffects()) {
            customEffects = equalsMeta.getCustomEffects();
        } else {
            customEffects = null;
        }

        if (equalsMeta.hasColor()) {
            color = equalsMeta.getColor();
        } else {
            color = null;
        }
        contentsList.add(equalsMeta.getBasePotionData());
        contentsList.add(customEffects);
        contentsList.add(color);
        return contentsList;
    }
}
