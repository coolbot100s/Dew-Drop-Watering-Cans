package cool.bot.dewdropwateringcans.item.wateringCan;

import cool.bot.dewdropwateringcans.Config;
import cool.bot.dewdropwateringcans.event.WateringCanFailEvent;
import cool.bot.dewdropwateringcans.event.WateringCanFillEvent;
import cool.bot.dewdropwateringcans.event.WateringCanPourEvent;
import cool.bot.dewdropwateringcans.event.WateringCanSuperEvent;
import cool.bot.botslib.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.MinecraftForge;

// TODO?: Maybe make this a forge fluid handler item instead of using durability as water lol
// TODO?: Add custom sound events for sounds

public class WateringCanItem extends Item {

    private int cooldown;
    private int requiredCharge;
    private int maxSuperLevel;
    private int chargePerLevel;
    private final static int USE_DURATION = 72000;

    public WateringCanItem(Properties properties, int requiredCharge, int cooldown, int maxSuperLevel) {
        super(properties);
        this.requiredCharge = requiredCharge;
        this.cooldown = cooldown;
        this.maxSuperLevel = maxSuperLevel;
        this.chargePerLevel = maxSuperLevel >= 1 ? requiredCharge / maxSuperLevel : 0;
    }

    public int getCooldown() {
        return cooldown;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return Mth.hsvToRgb(0.60F, 1.0F, 1.0F);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return USE_DURATION;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // All logic should be handled Server Side
        // Assume level and player are ServerLevel and ServerPlayer respectively
        if (level.isClientSide()) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        // We'll literally always need this
        ItemStack stack = player.getItemInHand(hand);

        // Can't be used if player is charging
        if (player.isUsingItem()) {
            return InteractionResultHolder.pass(stack);
        }

        // Can't be used if on cooldown
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        // If the player is crouching, and the can has a super, send it off to useOnRelease()
        if (player.isCrouching() && maxSuperLevel >= 1) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }

        // Get block the player currently has selected
        BlockPos pos = Util.blockHighlightedOrNull(player);

        // If not a block, do nothing
        if (pos == null) {
            return InteractionResultHolder.pass(stack);
        }

        BlockState blockState = level.getBlockState(pos);

        // Fill the can if the block is water
        if (blockState.getFluidState().is(FluidTags.WATER)) {
            MinecraftForge.EVENT_BUS.post(new WateringCanFillEvent((ServerLevel) level, (ServerPlayer) player, stack));
            return InteractionResultHolder.success(stack);
        }

        // Only use the watering can if the player is looking downwards
        if (player.getLookAngle().y() > 0) {
            return InteractionResultHolder.pass(stack);
        }

        // If the watering can is empty, trigger a WateringCanFailEvent if the player is not creative
        if (stack.getDamageValue() >= stack.getMaxDamage() && !player.isCreative()) {
            MinecraftForge.EVENT_BUS.post(new WateringCanFailEvent((ServerLevel) level,(ServerPlayer) player, stack));
            return InteractionResultHolder.fail(stack);
        }

        // If the block is covered by a solid block, do nothing
        if (level.getBlockState(pos.above()).isSolidRender(level, pos.above())) {
            return InteractionResultHolder.pass(stack);
        }

        // All checks have passed, Water that block!
        MinecraftForge.EVENT_BUS.post(new WateringCanPourEvent((ServerLevel) level, (ServerPlayer) player, stack, pos, blockState, false));
        return InteractionResultHolder.success(player.getItemInHand(hand));

    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        // All logic should be handled Server Side
        // Assume level and player are ServerLevel and ServerPlayer respectively
        if (level.isClientSide()) {
            return;
        }

        // Only do stuff if the entity is a player
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;

        // Only use the watering can if the player is looking downwards
        if (player.getLookAngle().y() > 0) {
            return;
        }

        // Get block the player currently has selected
        BlockPos pos = Util.blockHighlightedOrNull(player);

        // If not a block, do nothing
        if (pos == null) {
            return;
        }

        // Do nothing if the item is not charged enough to use any super
        int useDuration = this.getUseDuration(stack) - timeLeft;

        //int chargePerLevel = requiredCharge / maxSuperLevel;
        if (useDuration < chargePerLevel) {
            return;
        }

        // Determine what level of super is ready to be used
        int superLevelToUse = Math.floorDiv(useDuration, chargePerLevel);
        if (superLevelToUse > maxSuperLevel) {
            superLevelToUse = maxSuperLevel;
        }

        AABB area = getAreaForSuper(superLevelToUse, player, pos);

        // Use Super
        MinecraftForge.EVENT_BUS.post(new WateringCanSuperEvent((ServerLevel) level, (ServerPlayer) player, stack, area, maxSuperLevel));

    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int timeLeft) {
        if  (level.isClientSide()) {
            return;
        }

        int useDuration = this.getUseDuration(stack) - timeLeft;


        Player player = (Player) livingEntity;

        if (useDuration == chargePerLevel || (useDuration % chargePerLevel == 0 && useDuration <= chargePerLevel * maxSuperLevel && useDuration > 0)) {
            player.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1, 1);
        }

        // Only continue from here every 8 ticks
        if (useDuration % 8 != 0) {
            return;
        }

        // Determine what level of super is ready to be used
        int superLevelToUse = Math.floorDiv(useDuration, chargePerLevel);
        if (superLevelToUse > maxSuperLevel) {
            superLevelToUse = maxSuperLevel;
        }

        if (superLevelToUse <= 0) {
            return;
        }

        BlockPos pos = Util.blockHighlightedOrNull(player);

        if  (pos == null) {
            return;
        }

        AABB area = getAreaForSuper(superLevelToUse, player, pos);
        ServerLevel serverLevel = (ServerLevel) level;

        BlockPos.betweenClosedStream(area).forEach(bp -> {
            double yoff;

            if(level.getBlockState(bp).isAir()) {
                yoff = -1 + level.getBlockState(bp.below()).getShape(level,bp.below()).max(Direction.Axis.Y) + 0.1;
            } else {
                yoff = level.getBlockState(bp).getShape(level,bp).max(Direction.Axis.Y) + 0.1;
            }
            serverLevel.sendParticles(ParticleTypes.COMPOSTER,bp.getX() + 0.5, bp.getY() + yoff, bp.getZ() + 0.5, 1, 0, 0, 0, 0);
        });


    }

    // This can probably be moved to a Utils class since other items will have supers
    private AABB getAreaForSuper(int superLevelToUse,Player player,BlockPos pos) {

        // Set up our Super Shape
        int forward = 0;
        int back = 0;
        int left = 0;
        int right = 0;

        switch (superLevelToUse) {
            case 1:
                forward = Config.super1forward;
                back = Config.super1back;
                left = Config.super1left;
                right = Config.super1right;
                break;
            case 2:
                forward = Config.super2forward;
                back = Config.super2back;
                left = Config.super2left;
                right = Config.super2right;
                break;
            case 3:
                forward = Config.super3forward;
                back = Config.super3back;
                left = Config.super3left;
                right = Config.super3right;
                break;
            case 4:
                forward = Config.super4forward;
                back = Config.super4back;
                left = Config.super4left;
                right = Config.super4right;
                break;
            default:
                break;
        }

        // Get the player's facing direction
        Direction facing = player.getDirection();

        // Calculate the corners of the bounding box
        BlockPos start = pos;
        BlockPos end = pos;


        switch (facing) {
            case NORTH:
                start = pos.offset(-left, 0, -forward);
                end = pos.offset(right, 0, back);
                break;
            case SOUTH:
                start = pos.offset(-right, 0, -back);
                end = pos.offset(left, 0, forward);
                break;
            case WEST:
                start = pos.offset(-forward, 0, right);
                end = pos.offset(back, 0, -left);
                break;
            case EAST:
                start = pos.offset(forward, 0, -right);
                end = pos.offset(-back, 0, left);
                break;
            default:
                break;
        }

        return new AABB(start, end);

    }

}