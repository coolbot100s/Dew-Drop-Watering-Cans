package cool.bot.dewdropwateringcans;

import com.mojang.logging.LogUtils;
import cool.bot.dewdropwateringcans.item.ModItems;
import cool.bot.dewdropwateringcans.item.wateringCan.WateringCanEventsHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;

@Mod(DewDropWateringCans.MODID)
public class DewDropWateringCans {
    public static final String MODID = "dew_drop_watering_cans";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ModConfigSpec.Builder CONFIG_BUILDER = new ModConfigSpec.Builder();
    public static final Config CONFIG = new Config(CONFIG_BUILDER);

    public DewDropWateringCans(ModContainer container) {
        IEventBus modEventBus = container.getEventBus();
        ModItems.register(modEventBus);
        modEventBus.addListener(ModItems::addCreative);
        container.registerConfig(ModConfig.Type.COMMON, CONFIG_BUILDER.build());

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
}
