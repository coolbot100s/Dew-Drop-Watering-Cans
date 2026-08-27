package cool.bot.dewdropwateringcans.item;

import cool.bot.dewdropwateringcans.DewDropWateringCans;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModTabs {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DewDropWateringCans.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DEWDROP_TAB = CREATIVE_MODE_TABS.register("dewdrop_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.COPPER_WATERING_CAN.get()))
                    .title(Component.translatable("creativetab.dewdrop_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.COPPER_WATERING_CAN.get());
                        pOutput.accept(ModItems.IRON_WATERING_CAN.get());
                        pOutput.accept(ModItems.GOLD_WATERING_CAN.get());
                        pOutput.accept(ModItems.DIAMOND_WATERING_CAN.get());
                        pOutput.accept(ModItems.NETHERITE_WATERING_CAN.get());

                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}