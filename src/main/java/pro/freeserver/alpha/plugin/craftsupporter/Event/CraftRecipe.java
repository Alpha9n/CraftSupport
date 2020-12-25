package pro.freeserver.alpha.plugin.craftsupporter.Event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static pro.freeserver.alpha.plugin.craftsupporter.Craftsupporter.plugin;

public class CraftRecipe implements Listener {
    //カスタムアイテム
    public static ItemStack kizi = getItemStack(Material.DEAD_BUSH, 1, "生地", Arrays.asList("§cこのままでは食べられないようだ..."), 1);
    public static ItemStack pizza = getItemStack(Material.PUMPKIN_PIE, 1, "ピザ", Arrays.asList("§bたくさん作ってみんなでシェアしよう!!"), 6);
    public static ItemStack chiffonCake = getItemStack(Material.PUMPKIN_PIE, 1, "シフォンケーキ", Arrays.asList("§6最もシンプルでおいしいケーキ!!"), 7);
    public static ItemStack honey_chiffonCake = getItemStack(Material.PUMPKIN_PIE, 1, "みつかぼケーキ", Arrays.asList("§6とろーりはちみつがかかった甘くておいしいパンプキンケーキ"), 8);
    public static ItemStack dish_plate = getItemStack(Material.BOWL, 1, "皿", Arrays.asList("§bいろいろな食べ物を載せられるお皿"), 1);
    public static ItemStack donburi = getItemStack(Material.BOWL, 1, "丼", Arrays.asList("§bいろいろな食べ物を載せられる器","§6丼やカップケーキに最適"), 2);
    public static ItemStack eggliquid = getItemStack(Material.BEETROOT_SOUP, 1, "ボウルに入った卵液", Arrays.asList("§b卵を溶いただけの液体"), 1);
    public static ItemStack omelet = getItemStack(Material.PUMPKIN_PIE,1,"オムレツ",Arrays.asList("§6おいしいオムレツだ"),9);
    private static ItemStack waterbottle = waterBottle();
    private static ItemStack air = new ItemStack(Material.AIR);

    //レシピリスト
    private static List<ItemStack> rbdye = Arrays.asList(new ItemStack(Material.COAL));
    private static List<ItemStack> rcbdye = Arrays.asList(new ItemStack(Material.CHARCOAL));
    private static List<ItemStack> rkizi = Arrays.asList(new ItemStack(Material.WHEAT),waterbottle);
    private static List<ItemStack> rpizza = Arrays.asList(new ItemStack(Material.BEETROOT),new ItemStack(Material.POTATO),new ItemStack(Material.RABBIT),new ItemStack(Material.KELP),kizi);
    private static List<ItemStack> rhoneycake = Arrays.asList(new ItemStack(Material.CARVED_PUMPKIN),new ItemStack(Material.HONEY_BOTTLE),kizi,donburi);
    private static List<ItemStack> reggrequid = Arrays.asList(new ItemStack(Material.EGG));

    public static void createRecipe() {
        newFreeSlotRecipe("coalrecipe",new ItemStack(Material.BLACK_DYE,1),rbdye);
        newFreeSlotRecipe("ccoalrecipe",new ItemStack(Material.BLACK_DYE,1),rcbdye);
        newFreeSlotRecipe("kizirecipe",kizi,rkizi);
        newFreeSlotRecipe("pizzarecipe",pizza,rpizza);
        newFreeSlotRecipe("honeycakerecipe",honey_chiffonCake,rhoneycake);
        newFreeSlotRecipe("eggrequid",eggliquid,reggrequid);

        newFurnace("chiffoncakerecipe",chiffonCake,kizi,1.0f,180);
        newFurnace("omelet",omelet,eggliquid,1.0f,20);
    }


    //固定スロット(新)
    public static void newSlotFixRecipe(){
        //キー生成
        NamespacedKey donburi_key = new NamespacedKey(plugin, "donburi");
        NamespacedKey dish_key = new NamespacedKey(plugin, "dish");

        //カスタムレシピ作成
        ShapedRecipe donburi_recipe = new ShapedRecipe(donburi_key, CraftRecipe.donburi);
        ShapedRecipe dish_recipe = new ShapedRecipe(dish_key, CraftRecipe.dish_plate);

        //レシピの配置(固定)
        donburi_recipe.shape("N N"," N ");
        dish_recipe.shape("NNN");

        //ショートカットの指定
        //N = Nether_Brick_slab
        donburi_recipe.setIngredient('N', Material.NETHER_BRICK_SLAB);
        dish_recipe.setIngredient('N',Material.NETHER_BRICK_SLAB);

        //Spigot側にアイテムレシピの受け渡し
        Bukkit.addRecipe(donburi_recipe);
        Bukkit.addRecipe(dish_recipe);
    }

    //変動スロット(新)
    public static void newFreeSlotRecipe(String recipeName, ItemStack result, List<ItemStack> ingredients){
        if(ingredients.size()>9) return;
        ShapelessRecipe slr = new ShapelessRecipe(NamespacedKey.minecraft(recipeName), result);
        for(ItemStack ingredient: ingredients){
            slr.addIngredient(ingredient);
        }
        Bukkit.getServer().addRecipe(slr);
    }

    //かまどレシピ(新)
    public static void newFurnace(String recipeName, ItemStack result, ItemStack ingredient, float exp, int cookingTime){
        FurnaceRecipe fr = new FurnaceRecipe(NamespacedKey.minecraft(recipeName), result, new RecipeChoice.ExactChoice(ingredient), exp, cookingTime);
        Bukkit.getServer().addRecipe(fr);
    }

    //水入り瓶のアイテム作成
    public static ItemStack waterBottle() {
        ItemStack bottle = new ItemStack(Material.POTION, 1);
        ItemMeta meta = bottle.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        bottle.setItemMeta(pmeta);
        return bottle;
    }

    // ItemStackAPI
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