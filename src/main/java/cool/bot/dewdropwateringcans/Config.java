package cool.bot.dewdropwateringcans;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    private static final int MIN_SUPER_VAL = -64;
    private static final int MAX_SUPER_VAL = 64;
    
    public final ModConfigSpec.IntValue super1forward;
    public final ModConfigSpec.IntValue super1back;
    public final ModConfigSpec.IntValue super1left;
    public final ModConfigSpec.IntValue super1right;
    public final ModConfigSpec.IntValue super2forward;
    public final ModConfigSpec.IntValue super2back;
    public final ModConfigSpec.IntValue super2left;
    public final ModConfigSpec.IntValue super2right;
    public final ModConfigSpec.IntValue super3forward;
    public final ModConfigSpec.IntValue super3back;
    public final ModConfigSpec.IntValue super3left;
    public final ModConfigSpec.IntValue super3right;
    public final ModConfigSpec.IntValue super4forward;
    public final ModConfigSpec.IntValue super4back;
    public final ModConfigSpec.IntValue super4left;
    public final ModConfigSpec.IntValue super4right;
    public final ModConfigSpec.IntValue bonemealOdds;
    public final ModConfigSpec.BooleanValue allowNether;
    public final ModConfigSpec.BooleanValue allowNetheriteCanAnyways;
    public final ModConfigSpec.BooleanValue extinguishFires;
    public final ModConfigSpec.IntValue mudOdds;

    public Config(final ModConfigSpec.Builder builder) {

        // Super level 1
        super1forward = builder.comment("How far forward the first level of the watering can's super will extend")
                .defineInRange("supers.super1.forward", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super1back = builder.comment("How far back the first level of the watering can's super will extend")
                .defineInRange("supers.super1.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super1left = builder.comment("How far left the first level of the watering can's super will extend")
                .defineInRange("supers.super1.left", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super1right = builder.comment("How far right the first level of the watering can's super will extend")
                .defineInRange("supers.super1.right", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);

        // Super level 2
        super2forward = builder.comment("How far forward the second level of the watering can's super will extend")
                .defineInRange("supers.super2.forward", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super2back = builder.comment("How far back the second level of the watering can's super will extend")
                .defineInRange("supers.super2.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super2left = builder.comment("How far left the second level of the watering can's super will extend")
                .defineInRange("supers.super2.left", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super2right = builder.comment("How far right the second level of the watering can's super will extend")
                .defineInRange("supers.super2.right", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);

        // Super level 3
        super3forward = builder.comment("How far forward the third level of the watering can's super will extend")
                .defineInRange("supers.super3.forward", 4, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super3back = builder.comment("How far back the third level of the watering can's super will extend")
                .defineInRange("supers.super3.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super3left = builder.comment("How far left the third level of the watering can's super will extend")
                .defineInRange("supers.super3.left", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super3right = builder.comment("How far right the third level of the watering can's super will extend")
                .defineInRange("supers.super3.right", 1, MIN_SUPER_VAL, MAX_SUPER_VAL);

        // Super level 4

        super4forward = builder.comment("How far forward the fourth level of the watering can's super will extend")
                .defineInRange("supers.super4.forward", 4, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super4back = builder.comment("How far back the fourth level of the watering can's super will extend")
                .defineInRange("supers.super4.back", 0, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super4left = builder.comment("How far left the fourth level of the watering can's super will extend")
                .defineInRange("supers.super4.left", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);

        super4right = builder.comment("How far right the fourth level of the watering can's super will extend")
                .defineInRange("supers.super4.right", 2, MIN_SUPER_VAL, MAX_SUPER_VAL);

        // Nether

        allowNether = builder.comment("Allow watering cans to work in the Nether.").define("nether.allow", false);

        allowNetheriteCanAnyways = builder.comment("Allows the Netherite Watering Can to be used in the nether, regardless of the previous option.").define("nether.allowNetheriteCanAnyways", true);

        // Interactions

        extinguishFires = builder.comment("Whether watering cans extinguish fires & campfires.").define("interactions.extinguishFires", true);

        mudOdds = builder.comment("The chance out of 100 that dirt is converted into mud, 0 will disable the mechanic.")
                .defineInRange("interactions.mudOdds", 25, 0, 100);

        bonemealOdds = builder.comment("The chance out of 100 that bonemeal is applied to a watered crop, 0 will disable the mechanic.")
                .defineInRange("interactions.bonemealOdds", 25, 0, 100);

    }

}