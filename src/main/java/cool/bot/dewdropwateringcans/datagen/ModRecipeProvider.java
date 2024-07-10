package cool.bot.dewdropwateringcans.datagen;

import cool.bot.dewdropwateringcans.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;


public class ModRecipeProvider  extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_WATERING_CAN.get())
                .pattern("a a")
                .pattern("aba")
                .pattern("aaa")
                .define('a',Tags.Items.INGOTS_COPPER)
                .define('b', Items.WATER_BUCKET)
                .unlockedBy("has_bucket", has(Items.WATER_BUCKET))
                .save(pWriter, "copper_watering_can");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_WATERING_CAN.get())
                .pattern("a a")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Tags.Items.INGOTS_IRON)
                .define('b', Items.WATER_BUCKET)
                .unlockedBy("has_bucket", has(Items.WATER_BUCKET))
                .save(pWriter, "iron_watering_can");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.GOLD_WATERING_CAN.get())
                .pattern("a a")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Tags.Items.INGOTS_GOLD)
                .define('b', Items.WATER_BUCKET)
                .unlockedBy("has_bucket", has(Items.WATER_BUCKET))
                .save(pWriter, "gold_watering_can");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_WATERING_CAN.get())
                .pattern("a a")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Tags.Items.GEMS_DIAMOND)
                .define('b', Items.WATER_BUCKET)
                .unlockedBy("has_bucket", has(Items.WATER_BUCKET))
                .save(pWriter, "diamond_watering_can");


        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),Ingredient.of(ModItems.DIAMOND_WATERING_CAN.get()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, ModItems.NETHERITE_WATERING_CAN.get())
                .unlocks("has_daimond_watering_can", has(ModItems.DIAMOND_WATERING_CAN.get()))
                .save(pWriter, "netherite_watering_can");

    }
}