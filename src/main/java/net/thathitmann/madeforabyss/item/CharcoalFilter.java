package net.thathitmann.madeforabyss.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CharcoalFilter extends Item {


    public CharcoalFilter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.literal("Refills the gas mask. Craft together with the mask, or use the filter with the gas mask equipped to quick reload.").withStyle(ChatFormatting.DARK_PURPLE));
        } else {
            components.add(Component.literal("Press SHIFT for more info").withStyle(ChatFormatting.AQUA));
        }
        super.appendHoverText(itemStack, level, components, flag);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        for (ItemStack itemStack : pPlayer.getArmorSlots()) {
            if (itemStack.getItem() instanceof GasMask) {
                if (itemStack.getTag().getInt("Damage") > 0) {
                    itemStack.getTag().putInt("Damage", 0);
                    ItemStack filterItemStack = pPlayer.getItemInHand(pUsedHand);
                    filterItemStack.setCount(filterItemStack.getCount() - 1);
                }
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
