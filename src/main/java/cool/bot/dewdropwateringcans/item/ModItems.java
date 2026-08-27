package cool.bot.dewdropwateringcans.item;

import cool.bot.botslib.item.DewDropCreativeTab;
import cool.bot.dewdropwateringcans.DewDropWateringCans;
import cool.bot.dewdropwateringcans.item.wateringCan.WateringCanItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DewDropWateringCans.MODID)

    public static final DeferredHolder<Item, Item> COPPER_WATERING_CAN = ITEMS.register("copper_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.COMMON).durability(12), 60, 18, 0));

    public static final DeferredHolder<Item, Item> IRON_WATERING_CAN = ITEMS.register("iron_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.COMMON).durability(24), 45, 15, 1));

    public static final DeferredHolder<Item, Item> GOLD_WATERING_CAN = ITEMS.register("gold_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.COMMON).durability(54), 45, 12, 2));

    public static final DeferredHolder<Item, Item> DIAMOND_WATERING_CAN = ITEMS.register("diamond_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.COMMON).durability(90), 40, 12, 3));

    public static final DeferredHolder<Item, Item> NETHERITE_WATERING_CAN = ITEMS.register("netherite_watering_can",
            () -> new WateringCanItem(new Item.Properties().rarity(Rarity.RARE).durability(120), 40, 5, 4));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == DewDropCreativeTab.DEWDROP_TAB.getKey()) {
            event.accept(ModItems.COPPER_WATERING_CAN.get());
            event.accept(ModItems.IRON_WATERING_CAN.get());
            event.accept(ModItems.GOLD_WATERING_CAN.get());
            event.accept(ModItems.DIAMOND_WATERING_CAN.get());
            event.accept(ModItems.NETHERITE_WATERING_CAN.get());
        }
    }

}