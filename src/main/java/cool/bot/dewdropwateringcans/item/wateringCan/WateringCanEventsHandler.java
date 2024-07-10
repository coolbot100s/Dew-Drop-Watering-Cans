package cool.bot.dewdropwateringcans.item.wateringCan;

import cool.bot.dewdropwateringcans.event.*;
import cool.bot.botslib.tag.DewDropBlockTags;
import cool.bot.botslib.util.Util;
import cool.bot.botslib.util.RNG;
import cool.bot.dewdropwateringcans.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class WateringCanEventsHandler {

    @SubscribeEvent
    public static void onWateringCanPourEvent(WateringCanPourEvent event) {
        ServerLevel level = event.serverLevel;
        ServerPlayer player = event.player;
        ItemStack  stack = event.stack;
        BlockPos pos = event.pos;
        BlockState state = event.state;
        boolean isSuper = event.isSuper;

        //TODO?: Special interaction for lava
        //TODO?: Special interaction for axolotl
        //TODO?: Config for each interaction type
        //TODO?: Probably raycast from the player's eyes to the target blockpos, see what it hits and make *that* the watered block if it has collision, or if it has some other special effect
        //TODO?: Datapack for a new "recipe type" for watering, with an input blockstate, and an output blockstate, the watering can is the catalyst

        // When pouring as a part of a super, these should be handled by the super event
        if  (!isSuper) {
            // Damage the item by 1
            stack.hurt(1, RandomSource.create(), null);

            // Reset cooldown
            resetCooldown(player, stack);
        }

        // Special nether interaction
        if (level.dimension().equals(ServerLevel.NETHER) && !Config.allowNether) {

            for (int i = 0; i < 8; i++) {
                double offsetX = 0.5;
                double offsetY = state.getShape(level,pos).max(Direction.Axis.Y);
                double offsetZ = 0.5;
                level.sendParticles(ParticleTypes.SMOKE, pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ, 0, 0, 0, 0, 1);
            }

            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5F, 3);
            return;
        }

        if (isSuper && state.isAir()) {
            pos = pos.below();
            state = level.getBlockState(pos);
        }

        // If the block is farmland (or crops on farmland)
        if (state.is(DewDropBlockTags.WATERABLE)) {
            // Set moisture of farmland to 7
            Util.setMoist(level,pos);

        } else if (state.is(BlockTags.CROPS) && level.getBlockState(pos.below()).is(DewDropBlockTags.WATERABLE)) {
            pos = pos.below();
            state = level.getBlockState(pos);
            Util.setMoist(level,pos);

        } else if (state.is(BlockTags.CAMPFIRES) && state.getValue(BlockStateProperties.LIT) && Config.extinguishFires) {
            level.setBlock(pos, state.setValue(BlockStateProperties.LIT, false), 3);
            level.playSound(null,pos,SoundEvents.FIRE_EXTINGUISH,SoundSource.BLOCKS,1,1);
        } else if (state.is(BlockTags.FIRE) && Config.extinguishFires) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 1);
            // TODO?: Sizzling particles
        } else if (state.is(BlockTags.DIRT) && Config.mudOdds > 0 && RNG.ihundo(Config.mudOdds)) {
            level.setBlock(pos, Blocks.MUD.defaultBlockState(),3);
            level.playSound(null,pos, SoundEvents.MUD_PLACE, SoundSource.BLOCKS, 1, 1);
        }

        // Play sound
        level.playSound(null, pos, SoundEvents.HONEY_BLOCK_SLIDE, SoundSource.PLAYERS, 1, 3);

        // Particles
        for (int i = 0; i < 15; i++) {
            double offsetX = RandomSource.create().nextDouble() - 0.5;
            double offsetY = state.getShape(level,pos).max(Direction.Axis.Y);
            double offsetZ = RandomSource.create().nextDouble() - 0.5;
            level.sendParticles(ParticleTypes.RAIN, pos.getX() + 0.5 + offsetX, pos.getY() + offsetY, pos.getZ() + 0.5 + offsetZ,1,0,0,0,0);
        }

        // Bonemeal mechanic
        if (Config.bonemealOdds > 0 && RNG.ihundo(Config.bonemealOdds)) {
            pos = pos.above();
            state = level.getBlockState(pos);
            Block block = state.getBlock();
            if (state.is(BlockTags.CROPS) && block instanceof CropBlock) {
                CropBlock crop = (CropBlock) block;
                crop.performBonemeal(level, level.getRandom(), pos, state);
            }
        }

    }

    @SubscribeEvent
    public static void onWateringCanFailEvent(WateringCanFailEvent event) {
        ServerLevel serverLevel = event.serverLevel;
        ServerPlayer player = event.player;
        ItemStack stack = event.stack;

        // Send a sound to the client (apparently this takes extra steps)
        player.playNotifySound(SoundEvents.LANTERN_FALL, SoundSource.PLAYERS, 1, 1);

        // Reset cooldown
        resetCooldown(player, stack);

    }

    @SubscribeEvent
    public static void onWateringCanFillEvent(WateringCanFillEvent event) {
        ServerLevel serverLevel = event.serverLevel;
        ServerPlayer player = event.player;
        ItemStack stack = event.stack;

        // Refill water
        stack.setDamageValue(0);

        // Play sound
        serverLevel.playSound(null, player.getOnPos(), SoundEvents.HONEY_BLOCK_BREAK, SoundSource.PLAYERS, 1, 1);

        // Reset cooldown
        resetCooldown(player, stack);

    }

    @SubscribeEvent
    public static void onWateringCanSuperEvent(WateringCanSuperEvent event) {
        ServerLevel level = event.serverLevel;
        ServerPlayer player = event.player;
        ItemStack stack = event.stack;
        int superLevel = event.superLevel;
        AABB area = event.area;

        // Calculate the size of the area //TODO?: Maybe store these values for each superLevel when they're defined in the config
        int areaSize = (int) BlockPos.betweenClosedStream(area).count();

        // Check if we have enough water to use the super
        if (areaSize > stack.getMaxDamage() - stack.getDamageValue() && !player.isCreative()) {
            MinecraftForge.EVENT_BUS.post(new WateringCanFailEvent(level, player, stack));
            return;
        }

        // Now you can iterate over the blocks within the area
        BlockPos.betweenClosedStream(area).forEach(blockPos -> {
                    BlockState state = level.getBlockState(blockPos);
                    MinecraftForge.EVENT_BUS.post(new WateringCanPourEvent(level, player, stack, blockPos, state, true));
                    }) ;

        // Damage the item by the areaSize
        if(!player.isCreative()) {
            stack.hurt(areaSize, RandomSource.create(), null);
        }

        // Reset cooldown
        resetCooldown(player, stack);

    }

    // Helper function for reseting item cooldown
    private static void resetCooldown(ServerPlayer player, ItemStack stack) {
        player.getCooldowns().addCooldown(stack.getItem(), ((WateringCanItem) stack.getItem()).getCooldown());
    }

}