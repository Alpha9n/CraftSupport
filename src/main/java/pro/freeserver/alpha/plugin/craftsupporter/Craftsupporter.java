package pro.freeserver.alpha.plugin.craftsupporter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import pro.freeserver.alpha.plugin.craftsupporter.Event.CraftRecipe;

public final class Craftsupporter extends JavaPlugin {

    @Override
    public void onEnable() {
        registerFurnaceRecipe();
        registerRecipe();
        getServer().getPluginManager().registerEvents(new CraftRecipe(),this);
        getCommand("CraftSupporter").setExecutor(new MainCommandClass());
        getLogger().info("§aCraftSupporterが起動しました");
    }

    @Override
    public void onDisable() {
        getLogger().info("§cCraftSupporterが終了しました");
    }

    public void registerFurnaceRecipe(){
        //シフォンケーキの作成
        FurnaceRecipe recipe = new FurnaceRecipe(new NamespacedKey(this,"chiffon_cake"),CraftRecipe.chiffonCake, new RecipeChoice.ExactChoice(CraftRecipe.kizi),0,600);
        Bukkit.addRecipe(recipe);
    }

    public void registerRecipe(){
        //キー生成
        NamespacedKey donburi_key = new NamespacedKey(this, "donburi");
        NamespacedKey dish_key = new NamespacedKey(this, "dish");

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
}
