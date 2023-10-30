package net.thathitmann.madeforabyss.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.thathitmann.madeforabyss.MadeForAbyss;
import net.thathitmann.madeforabyss.sanity.PlayerSanity;
import net.thathitmann.madeforabyss.sanity.SanityMod;

public class SanityHUDOverlay {
    private static final ResourceLocation SANITY_BAR = new ResourceLocation(MadeForAbyss.MOD_ID, "textures/sanity/sanity_bar_empty.png");
    private static final ResourceLocation SANITY_BAR_LEVEL = new ResourceLocation(MadeForAbyss.MOD_ID, "textures/sanity/sanity_bar_full.png");


    public static final IGuiOverlay HUD_SANITY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {



        int sanity = ClientSanityData.getPlayerSanity();



        if (sanity < PlayerSanity.getMaxSanity()) {
            int x = screenWidth / 2;
            int y = screenHeight;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, SANITY_BAR_LEVEL);
            if (sanity > 0) {
                float width = 81.0f * sanity / PlayerSanity.getMaxSanity();
                guiGraphics.blitInscribed(SANITY_BAR_LEVEL, x - 91 - (int) ((81 - width) / 2), y - 59, 81, 9, (int) width, 9);
            }
            RenderSystem.setShaderTexture(0, SANITY_BAR);
            guiGraphics.blitInscribed(SANITY_BAR, x - 91, y - 59, 81, 9, 81, 9);
        }

    });



}
