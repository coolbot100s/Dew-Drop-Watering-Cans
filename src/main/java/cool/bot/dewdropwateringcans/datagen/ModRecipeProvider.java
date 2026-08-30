package cool.bot.dewdropwateringcans.datagen;

import cool.bot.dewdropwateringcans.DewDropWateringCans;
import cool.bot.dewdropwateringcans.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public class ModRecipeProvider  extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.COPPER_WATERING_CAN.get())
                .pattern("a a")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Tags.Items.INGOTS_COPPER)
                .define('b', Items.WATER_BUCKET)
                .unlockedBy("has_bucket", has(Items.WATER_BUCKET))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(DewDropWateringCans.MODID, "copper_watering_can"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.IRON_WATERING_CAN.get())
                .pattern("a a")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Tags.Items.INGOTS_IRON)
                .define('b', Items.WATER_BUCKET)
                .unlockedBy("has_bucket", has(Items.WATER_BUCKET))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(DewDropWateringCans.MODID, "iron_watering_can"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.GOLD_WATERING_CAN.get())
                .pattern("a a")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Tags.Items.INGOTS_GOLD)
                .define('b', Items.WATER_BUCKET)
                .unlockedBy("has_bucket", has(Items.WATER_BUCKET))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(DewDropWateringCans.MODID, "gold_watering_can"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DIAMOND_WATERING_CAN.get())
                .pattern("a a")
                .pattern("aba")
                .pattern("aaa")
                .define('a', Tags.Items.GEMS_DIAMOND)
                .define('b', Items.WATER_BUCKET)
                .unlockedBy("has_bucket", has(Items.WATER_BUCKET))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(DewDropWateringCans.MODID, "diamond_watering_can"));


        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),Ingredient.of(ModItems.DIAMOND_WATERING_CAN.get()), Ingredient.of(Items.NETHERITE_INGOT), RecipeCategory.TOOLS, ModItems.NETHERITE_WATERING_CAN.get())
                .unlocks("has_daimond_watering_can", has(ModItems.DIAMOND_WATERING_CAN.get()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath(DewDropWateringCans.MODID,"netherite_watering_can"));

    }
}