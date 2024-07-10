package cool.bot.dewdropwateringcans;

import com.mojang.logging.LogUtils;
import cool.bot.dewdropwateringcans.item.ModCreativeModTabs;
import cool.bot.dewdropwateringcans.item.ModItems;
import cool.bot.dewdropwateringcans.item.wateringCan.WateringCanEventsHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(DewDropWateringCans.MODID)
public class DewDropWateringCans {
    public static final String MODID = "dew_drop_watering_cans";
    private static final Logger LOGGER = LogUtils.getLogger();

    public DewDropWateringCans() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);
        ModItems.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(WateringCanEventsHandler.class);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
    }

}
