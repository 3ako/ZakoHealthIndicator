package me.zyouime.zakohealthindicator.render;

import me.zyouime.zakohealthindicator.config.ModConfig;
import me.zyouime.zakohealthindicator.util.ColorUtil;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix3x2fStack;

public class HealthHudRender {

    private long lastAttack = 0;
    private Player player = null;
    private final ModConfig config;
    private final Minecraft client;

    public HealthHudRender(ModConfig config) {
        this.register();
        this.config = config;
        this.client = Minecraft.getInstance();
    }

    public void register() {
        HudElementRegistry.addFirst(Identifier.fromNamespaceAndPath("zakohealthindicator", "health_render"), this::render);
        AttackEntityCallback.EVENT.register(((playerEntity, world, hand, entity, entityHitResult) -> {
            if (entity.getType() != EntityTypes.PLAYER) {
                lastAttack = 0;
                return InteractionResult.PASS;
            }
            lastAttack = System.currentTimeMillis();
            if (this.player != entity) {
                this.player = (Player) entity;
            }
            return InteractionResult.PASS;
        }));
    }

    private void render(GuiGraphicsExtractor context, DeltaTracker tickCounter) {
        if (!config.enabled) {
            return;
        }
        if (this.getLeft() > config.targetDelay) {
            return;
        }
        if (this.player == null) {
            return;
        }
        int health = (int) player.getHealth();
        Component text = Component.literal(String.valueOf(health)).withStyle(ColorUtil.getColor(health));
        int windowWidth = context.guiWidth();
        int windowHeight = context.guiHeight();
        int textWidth = client.font.width(text) / 2;
        int textHeight = client.font.lineHeight / 2;
        int x = (int) (windowWidth * config.relativeX);
        int y = (int) (windowHeight * config.relativeY);
        float scale = health < 10 ? config.lowHpScale : config.normalScale;
        Matrix3x2fStack matrixStack = context.pose();
        matrixStack.pushMatrix();
        matrixStack.translate(x, y);
        matrixStack.scale(scale);
        matrixStack.translate(-x, -y);
        context.text(client.font, text, x - textWidth, y - textHeight, -1);
        matrixStack.popMatrix();

    }

    private long getLeft() {
        return System.currentTimeMillis() - this.lastAttack;
    }
}
