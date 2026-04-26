package me.zyouime.zakohealthindicator.render;

import me.zyouime.zakohealthindicator.config.ModConfig;
import me.zyouime.zakohealthindicator.util.ColorUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class HealthHudRender {

    private long lastAttack = 0;
    private PlayerEntity player = null;
    private final ModConfig config;
    private final MinecraftClient client;

    public HealthHudRender(ModConfig config) {
        this.register();
        this.config = config;
        this.client = MinecraftClient.getInstance();
    }

    public void register() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer ->
            layeredDrawer.attachLayerAfter(
                IdentifiedLayer.MISC_OVERLAYS,
                Identifier.of("zakohealthindicator", "health_render"),
                this::render
            )
        );
        AttackEntityCallback.EVENT.register(((playerEntity, world, hand, entity, entityHitResult) -> {
            if (entity.getType() != EntityType.PLAYER) {
                lastAttack = 0;
                return ActionResult.PASS;
            }
            lastAttack = System.currentTimeMillis();
            if (this.player != entity) {
                this.player = (PlayerEntity) entity;
            }
            return ActionResult.PASS;
        }));
    }

    private void render(DrawContext context, RenderTickCounter tickCounter) {
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
        Text text = Text.literal(String.valueOf(health)).formatted(ColorUtil.getColor(health));
        int windowWidth = context.getScaledWindowWidth();
        int windowHeight = context.getScaledWindowHeight();
        int textWidth = client.textRenderer.getWidth(text) / 2;
        int textHeight = client.textRenderer.fontHeight / 2;
        int x = (int) (windowWidth * config.relativeX);
        int y = (int) (windowHeight * config.relativeY);
        float scale = health < 10 ? config.lowHpScale : config.normalScale;
        MatrixStack matrixStack = context.getMatrices();
        matrixStack.push();
        matrixStack.translate(x, y, 0);
        matrixStack.scale(scale, scale, 1);
        matrixStack.translate(-x, -y, 0);
        context.drawTextWithShadow(client.textRenderer, text, x - textWidth, y - textHeight, -1);
        matrixStack.pop();

    }

    private long getLeft() {
        return System.currentTimeMillis() - this.lastAttack;
    }
}
