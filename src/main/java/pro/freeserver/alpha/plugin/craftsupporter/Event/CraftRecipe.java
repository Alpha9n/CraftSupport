package pro.freeserver.alpha.plugin.craftsupporter.Event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CraftRecipe implements Listener {
    //カスタムアイテム
    public static ItemStack kizi = getItemStack(Material.DEAD_BUSH, 1, "生地", Arrays.asList("§cこのままでは食べられないようだ..."), 1);
    public static ItemStack pizza = getItemStack(Material.PUMPKIN_PIE, 1, "ピザ", Arrays.asList("§bたくさん作ってみんなでシェアしよう!!"), 6);
    public static ItemStack chiffonCake = getItemStack(Material.PUMPKIN_PIE, 1, "シフォンケーキ", Arrays.asList("§b最もシンプルでおいしいケーキ!!"), 1);

    @EventHandler
    public void onPlayerCraftItem(PrepareItemCraftEvent e) {
        if (e.getInventory().getMatrix().length < 9) {
            return;
        }
        //生地作成
        checkCraft(kizi, e.getInventory(),
                Arrays.asList(
                        new ItemStack(Material.WHEAT, 1),
                        waterBottle()
                ));
        //ピザ作成
        checkCraft(pizza, e.getInventory(),
                Arrays.asList(
                        new ItemStack(Material.BEETROOT, 1),
                        new ItemStack(Material.POTATO, 1),
                        new ItemStack(Material.RABBIT, 1),
                        new ItemStack(Material.KELP, 1),
                        kizi
                ));
    }

    //クラフトが正しいかチェックしたのちに結果を出力するクラス(固定スロット)
    public void slotFix(ItemStack result, CraftingInventory inv, HashMap<Integer, ItemStack> ingredients) {
        ItemStack[] matrix = inv.getMatrix();
        for (int i = 0; i < 9; i++) {
            if (ingredients.containsKey(i)) {
                if (matrix[i] == null || !matrix[i].equals(ingredients.get(i))) {
                    return;
                }
            } else {
                if (matrix[i] != null) {
                    return;
                }
            }
        }
        inv.setResult(result);
    }

    //クラフトが正しいかチェックしたのちに結果を出力するクラス(変動スロット)
    public void checkCraft(ItemStack result, CraftingInventory inv, List<ItemStack> ingredients) {
        ArrayList<ItemStack> matrix = new ArrayList<>(Arrays.asList(inv.getMatrix()));
        ArrayList<Integer> checkedList = new ArrayList<>();
        for (int matIndex = 0; matIndex < matrix.size(); matIndex++) {
            if (matrix.get(matIndex) != null){
                boolean isPartial = false;
                for (int ingIndex = 0; ingIndex < ingredients.size(); ingIndex++) {
                    ItemStack ingredient = ingredients.get(ingIndex);
                    if (!checkedList.contains(ingIndex)) {
                        ItemStack material = matrix.get(matIndex);
                        if (isPartialMatch(ingredient, material)) {
                            isPartial = true;
                            checkedList.add(ingIndex);
                            break;
                        }
                    }
                }
                if (!isPartial) return;
            }
        }
        checkedList = new ArrayList<>();
        for (int ingIndex = 0; ingIndex < ingredients.size(); ingIndex++) {
            boolean isPartial = false;
            for (int matIndex = 0; matIndex < matrix.size(); matIndex++) {
                ItemStack ingredient = ingredients.get(ingIndex);
                if (!checkedList.contains(ingIndex)) {
                    ItemStack material = matrix.get(matIndex);
                    if (isPartialMatch(ingredient, material)) {
                        isPartial = true;
                        checkedList.add(ingIndex);
                        break;
                    }
                }
            }
            if (!isPartial) return;
        }
        inv.setResult(result);
    }

    //水入り瓶のアイテム作成
    public ItemStack waterBottle() {
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        ItemMeta meta = bottle.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        bottle.setItemMeta(pmeta);
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
        if ((fromItem == null) || (toItem == null)) {
            System.out.println(1);
            return false;
        }
        if (!fromItem.getType().equals(toItem.getType())){
            System.out.println(fromItem.getType());
            System.out.println(toItem.getType());
            return false;
        }
        System.out.println(fromItem.isSimilar(toItem));
        if (fromItem.isSimilar(toItem)) return true;
        ItemMeta fromMeta = fromItem.getItemMeta();
        ItemMeta toMeta = toItem.getItemMeta();
        List<Object> fromContentList;
        List<Object> toContentList;
        if (fromMeta instanceof PotionMeta) {
            fromContentList = getPotionContentList((PotionMeta) fromMeta);
            toContentList = getPotionContentList((PotionMeta) toMeta);
        } else {
            fromContentList = getContentList(fromMeta);
            toContentList = getContentList(toMeta);
        }

        for (int i=0; i<fromContentList.size(); i++) {
            System.out.println(i);
            Object content = fromContentList.get(i);
            if (content != null) {
                if (toContentList.get(i) == null) return false;
                if (!content.equals(toContentList.get(i))) return false;
            }
        }
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