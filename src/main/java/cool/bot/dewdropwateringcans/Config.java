package cool.bot.dewdropwateringcans;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = DewDropWateringCans.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final int MIN_SUPER_VAL = -64;
    private static final int MAX_SUPER_VAL = 64;

    // Super level 1
    private static final ForgeConfigSpec.IntValue SUPER_1_FORWARD = BUILDER
            .comment("How far forward the first level of the watering can's super will extend")
            .defineInRange("super1.forward", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_1_BACK = BUILDER
            .comment("How far back the first level of the watering can's super will extend")
            .defineInRange("super1.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_1_LEFT = BUILDER
            .comment("How far left the first level of the watering can's super will extend")
            .defineInRange("super1.left", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_1_RIGHT = BUILDER
            .comment("How far right the first level of the watering can's super will extend")
            .defineInRange("super1.right", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);

    // Super level 2
    private static final ForgeConfigSpec.IntValue SUPER_2_FORWARD = BUILDER
            .comment("How far forward the second level of the watering can's super will extend")
            .defineInRange("super2.forward", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_2_BACK = BUILDER
            .comment("How far back the second level of the watering can's super will extend")
            .defineInRange("super2.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_2_LEFT = BUILDER
            .comment("How far left the second level of the watering can's super will extend")
            .defineInRange("super2.left", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_2_RIGHT = BUILDER
            .comment("How far right the second level of the watering can's super will extend")
            .defineInRange("super2.right", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);

    // Super level 3
    private static final ForgeConfigSpec.IntValue SUPER_3_FORWARD = BUILDER
            .comment("How far forward the third level of the watering can's super will extend")
            .defineInRange("super3.forward", 4, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_3_BACK = BUILDER
            .comment("How far back the third level of the watering can's super will extend")
            .defineInRange("super3.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_3_LEFT = BUILDER
            .comment("How far left the third level of the watering can's super will extend")
            .defineInRange("super3.left", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_3_RIGHT = BUILDER
            .comment("How far right the third level of the watering can's super will extend")
            .defineInRange("super3.right", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);

    // Super level 4
    private static final ForgeConfigSpec.IntValue SUPER_4_FORWARD = BUILDER
            .comment("How far forward the fourth level of the watering can's super will extend")
            .defineInRange("super4.forward", 4, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_4_BACK = BUILDER
            .comment("How far back the fourth level of the watering can's super will extend")
            .defineInRange("super4.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_4_LEFT = BUILDER
            .comment("How far left the fourth level of the watering can's super will extend")
            .defineInRange("super4.left", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);
    private static final ForgeConfigSpec.IntValue SUPER_4_RIGHT = BUILDER
            .comment("How far right the fourth level of the watering can's super will extend")
            .defineInRange("super4.right", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);

    static final ForgeConfigSpec SERVER_CONFIG = BUILDER.build();

    public static int super1forward;
    public static int super1back;
    public static int super1left;
    public static int super1right;
    public static int super2forward;
    public static int super2back;
    public static int super2left;
    public static int super2right;
    public static int super3forward;
    public static int super3back;
    public static int super3left;
    public static int super3right;
    public static int super4forward;
    public static int super4back;
    public static int super4left;
    public static int super4right;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        super1forward = SUPER_1_FORWARD.get();
        super1back = SUPER_1_BACK.get();
        super1left = SUPER_1_LEFT.get();
        super1right = SUPER_1_RIGHT.get();
        super2forward = SUPER_2_FORWARD.get();
        super2back = SUPER_2_BACK.get();
        super2left = SUPER_2_LEFT.get();
        super2right = SUPER_2_RIGHT.get();
        super3forward = SUPER_3_FORWARD.get();
        super3back = SUPER_3_BACK.get();
        super3left = SUPER_3_LEFT.get();
        super3right = SUPER_3_RIGHT.get();
        super4forward = SUPER_4_FORWARD.get();
        super4back = SUPER_4_BACK.get();
        super4left = SUPER_4_LEFT.get();
        super4right = SUPER_4_RIGHT.get();

    }
}